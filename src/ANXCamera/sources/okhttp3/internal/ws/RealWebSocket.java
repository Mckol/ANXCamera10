package okhttp3.internal.ws;

import com.android.camera.network.net.base.HTTP;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.ws.WebSocketReader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

public final class RealWebSocket implements WebSocket, WebSocketReader.FrameCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long CANCEL_AFTER_CLOSE_MILLIS = 60000;
    private static final long MAX_QUEUE_SIZE = 16777216;
    private static final List<Protocol> ONLY_HTTP1 = Collections.singletonList(Protocol.HTTP_1_1);
    private Call call;
    private ScheduledFuture<?> cancelFuture;
    private boolean enqueuedClose;
    private ScheduledExecutorService executor;
    private boolean failed;
    private final String key;
    final WebSocketListener listener;
    private final ArrayDeque<Object> messageAndCloseQueue = new ArrayDeque<>();
    private final Request originalRequest;
    int pingCount;
    int pongCount;
    private final ArrayDeque<ByteString> pongQueue = new ArrayDeque<>();
    private long queueSize;
    private final Random random;
    private WebSocketReader reader;
    private int receivedCloseCode = -1;
    private String receivedCloseReason;
    private Streams streams;
    private WebSocketWriter writer;
    private final Runnable writerRunnable;

    /* access modifiers changed from: package-private */
    public final class CancelRunnable implements Runnable {
        CancelRunnable() {
        }

        public void run() {
            RealWebSocket.this.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public static final class Close {
        final long cancelAfterCloseMillis;
        final int code;
        final ByteString reason;

        Close(int i, ByteString byteString, long j) {
            this.code = i;
            this.reason = byteString;
            this.cancelAfterCloseMillis = j;
        }
    }

    /* access modifiers changed from: package-private */
    public static final class Message {
        final ByteString data;
        final int formatOpcode;

        Message(int i, ByteString byteString) {
            this.formatOpcode = i;
            this.data = byteString;
        }
    }

    /* access modifiers changed from: private */
    public final class PingRunnable implements Runnable {
        PingRunnable() {
        }

        public void run() {
            RealWebSocket.this.writePingFrame();
        }
    }

    public static abstract class Streams implements Closeable {
        public final boolean client;
        public final BufferedSink sink;
        public final BufferedSource source;

        public Streams(boolean z, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.client = z;
            this.source = bufferedSource;
            this.sink = bufferedSink;
        }
    }

    public RealWebSocket(Request request, WebSocketListener webSocketListener, Random random2) {
        if ("GET".equals(request.method())) {
            this.originalRequest = request;
            this.listener = webSocketListener;
            this.random = random2;
            byte[] bArr = new byte[16];
            random2.nextBytes(bArr);
            this.key = ByteString.of(bArr).base64();
            this.writerRunnable = new Runnable() {
                /* class okhttp3.internal.ws.RealWebSocket.AnonymousClass1 */

                public void run() {
                    do {
                        try {
                        } catch (IOException e2) {
                            RealWebSocket.this.failWebSocket(e2, null);
                            return;
                        }
                    } while (RealWebSocket.this.writeOneFrame());
                }
            };
            return;
        }
        throw new IllegalArgumentException("Request must be GET: " + request.method());
    }

    private void runWriter() {
        ScheduledExecutorService scheduledExecutorService = this.executor;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.execute(this.writerRunnable);
        }
    }

    private synchronized boolean send(ByteString byteString, int i) {
        if (!this.failed) {
            if (!this.enqueuedClose) {
                if (this.queueSize + ((long) byteString.size()) > MAX_QUEUE_SIZE) {
                    close(1001, null);
                    return false;
                }
                this.queueSize += (long) byteString.size();
                this.messageAndCloseQueue.add(new Message(i, byteString));
                runWriter();
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void awaitTermination(int i, TimeUnit timeUnit) throws InterruptedException {
        this.executor.awaitTermination((long) i, timeUnit);
    }

    @Override // okhttp3.WebSocket
    public void cancel() {
        this.call.cancel();
    }

    /* access modifiers changed from: package-private */
    public void checkResponse(Response response) throws ProtocolException {
        if (response.code() == 101) {
            String header = response.header(HTTP.CONN_DIRECTIVE);
            if ("Upgrade".equalsIgnoreCase(header)) {
                String header2 = response.header("Upgrade");
                if ("websocket".equalsIgnoreCase(header2)) {
                    String header3 = response.header("Sec-WebSocket-Accept");
                    String base64 = ByteString.encodeUtf8(this.key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").sha1().base64();
                    if (!base64.equals(header3)) {
                        throw new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '" + base64 + "' but was '" + header3 + "'");
                    }
                    return;
                }
                throw new ProtocolException("Expected 'Upgrade' header value 'websocket' but was '" + header2 + "'");
            }
            throw new ProtocolException("Expected 'Connection' header value 'Upgrade' but was '" + header + "'");
        }
        throw new ProtocolException("Expected HTTP 101 response but was '" + response.code() + " " + response.message() + "'");
    }

    @Override // okhttp3.WebSocket
    public boolean close(int i, String str) {
        return close(i, str, CANCEL_AFTER_CLOSE_MILLIS);
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean close(int i, String str, long j) {
        WebSocketProtocol.validateCloseCode(i);
        ByteString byteString = null;
        if (str != null) {
            byteString = ByteString.encodeUtf8(str);
            if (((long) byteString.size()) > 123) {
                throw new IllegalArgumentException("reason.size() > 123: " + str);
            }
        }
        if (!this.failed) {
            if (!this.enqueuedClose) {
                this.enqueuedClose = true;
                this.messageAndCloseQueue.add(new Close(i, byteString, j));
                runWriter();
                return true;
            }
        }
        return false;
    }

    public void connect(OkHttpClient okHttpClient) {
        OkHttpClient build = okHttpClient.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
        final int pingIntervalMillis = build.pingIntervalMillis();
        final Request build2 = this.originalRequest.newBuilder().header("Upgrade", "websocket").header(HTTP.CONN_DIRECTIVE, "Upgrade").header("Sec-WebSocket-Key", this.key).header("Sec-WebSocket-Version", "13").build();
        this.call = Internal.instance.newWebSocketCall(build, build2);
        this.call.enqueue(new Callback() {
            /* class okhttp3.internal.ws.RealWebSocket.AnonymousClass2 */

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                RealWebSocket.this.failWebSocket(iOException, null);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                try {
                    RealWebSocket.this.checkResponse(response);
                    StreamAllocation streamAllocation = Internal.instance.streamAllocation(call);
                    streamAllocation.noNewStreams();
                    Streams newWebSocketStreams = streamAllocation.connection().newWebSocketStreams(streamAllocation);
                    try {
                        RealWebSocket.this.listener.onOpen(RealWebSocket.this, response);
                        RealWebSocket.this.initReaderAndWriter("OkHttp WebSocket " + build2.url().redact(), (long) pingIntervalMillis, newWebSocketStreams);
                        streamAllocation.connection().socket().setSoTimeout(0);
                        RealWebSocket.this.loopReader();
                    } catch (Exception e2) {
                        RealWebSocket.this.failWebSocket(e2, null);
                    }
                } catch (ProtocolException e3) {
                    RealWebSocket.this.failWebSocket(e3, response);
                    Util.closeQuietly(response);
                }
            }
        });
    }

    public void failWebSocket(Exception exc, @Nullable Response response) {
        synchronized (this) {
            if (!this.failed) {
                this.failed = true;
                Streams streams2 = this.streams;
                this.streams = null;
                if (this.cancelFuture != null) {
                    this.cancelFuture.cancel(false);
                }
                if (this.executor != null) {
                    this.executor.shutdown();
                }
                try {
                    this.listener.onFailure(this, exc, response);
                } finally {
                    Util.closeQuietly(streams2);
                }
            }
        }
    }

    public void initReaderAndWriter(String str, long j, Streams streams2) throws IOException {
        synchronized (this) {
            this.streams = streams2;
            this.writer = new WebSocketWriter(streams2.client, streams2.sink, this.random);
            this.executor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(str, false));
            if (j != 0) {
                this.executor.scheduleAtFixedRate(new PingRunnable(), j, j, TimeUnit.MILLISECONDS);
            }
            if (!this.messageAndCloseQueue.isEmpty()) {
                runWriter();
            }
        }
        this.reader = new WebSocketReader(streams2.client, streams2.source, this);
    }

    public void loopReader() throws IOException {
        while (this.receivedCloseCode == -1) {
            this.reader.processNextFrame();
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadClose(int i, String str) {
        Streams streams2;
        if (i != -1) {
            synchronized (this) {
                if (this.receivedCloseCode == -1) {
                    this.receivedCloseCode = i;
                    this.receivedCloseReason = str;
                    if (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty()) {
                        streams2 = null;
                    } else {
                        streams2 = this.streams;
                        this.streams = null;
                        if (this.cancelFuture != null) {
                            this.cancelFuture.cancel(false);
                        }
                        this.executor.shutdown();
                    }
                } else {
                    throw new IllegalStateException("already closed");
                }
            }
            try {
                this.listener.onClosing(this, i, str);
                if (streams2 != null) {
                    this.listener.onClosed(this, i, str);
                }
            } finally {
                Util.closeQuietly(streams2);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(String str) throws IOException {
        this.listener.onMessage(this, str);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(ByteString byteString) throws IOException {
        this.listener.onMessage(this, byteString);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPing(ByteString byteString) {
        if (!this.failed) {
            if (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty()) {
                this.pongQueue.add(byteString);
                runWriter();
                this.pingCount++;
            }
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPong(ByteString byteString) {
        this.pongCount++;
    }

    /* access modifiers changed from: package-private */
    public synchronized int pingCount() {
        return this.pingCount;
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean pong(ByteString byteString) {
        if (!this.failed) {
            if (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty()) {
                this.pongQueue.add(byteString);
                runWriter();
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public synchronized int pongCount() {
        return this.pongCount;
    }

    /* access modifiers changed from: package-private */
    public boolean processNextFrame() throws IOException {
        try {
            this.reader.processNextFrame();
            return this.receivedCloseCode == -1;
        } catch (Exception e2) {
            this.failWebSocket(e2, null);
            return false;
        }
    }

    @Override // okhttp3.WebSocket
    public synchronized long queueSize() {
        return this.queueSize;
    }

    @Override // okhttp3.WebSocket
    public Request request() {
        return this.originalRequest;
    }

    @Override // okhttp3.WebSocket
    public boolean send(String str) {
        if (str != null) {
            return send(ByteString.encodeUtf8(str), 1);
        }
        throw new NullPointerException("text == null");
    }

    @Override // okhttp3.WebSocket
    public boolean send(ByteString byteString) {
        if (byteString != null) {
            return send(byteString, 2);
        }
        throw new NullPointerException("bytes == null");
    }

    /* access modifiers changed from: package-private */
    public void tearDown() throws InterruptedException {
        ScheduledFuture<?> scheduledFuture = this.cancelFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.executor.shutdown();
        this.executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0051, code lost:
        if (r2 == null) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r0.writePong(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0059, code lost:
        if ((r5 instanceof okhttp3.internal.ws.RealWebSocket.Message) == false) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005b, code lost:
        r1 = ((okhttp3.internal.ws.RealWebSocket.Message) r5).data;
        r0 = okio.Okio.buffer(r0.newMessageSink(((okhttp3.internal.ws.RealWebSocket.Message) r5).formatOpcode, (long) r1.size()));
        r0.write(r1);
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0077, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r11.queueSize -= (long) r1.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0082, code lost:
        monitor-exit(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0089, code lost:
        if ((r5 instanceof okhttp3.internal.ws.RealWebSocket.Close) == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008b, code lost:
        r5 = (okhttp3.internal.ws.RealWebSocket.Close) r5;
        r0.writeClose(r5.code, r5.reason);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0094, code lost:
        if (r4 == null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0096, code lost:
        r11.listener.onClosed(r11, r1, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x009b, code lost:
        okhttp3.internal.Util.closeQuietly(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x009f, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a5, code lost:
        throw new java.lang.AssertionError();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a6, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a7, code lost:
        okhttp3.internal.Util.closeQuietly(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00aa, code lost:
        throw r11;
     */
    public boolean writeOneFrame() throws IOException {
        String str;
        Object obj;
        int i;
        synchronized (this) {
            if (this.failed) {
                return false;
            }
            WebSocketWriter webSocketWriter = this.writer;
            ByteString poll = this.pongQueue.poll();
            Streams streams2 = null;
            if (poll == null) {
                obj = this.messageAndCloseQueue.poll();
                if (obj instanceof Close) {
                    i = this.receivedCloseCode;
                    str = this.receivedCloseReason;
                    if (i != -1) {
                        Streams streams3 = this.streams;
                        this.streams = null;
                        this.executor.shutdown();
                        streams2 = streams3;
                    } else {
                        this.cancelFuture = this.executor.schedule(new CancelRunnable(), ((Close) obj).cancelAfterCloseMillis, TimeUnit.MILLISECONDS);
                    }
                } else if (obj == null) {
                    return false;
                } else {
                    i = -1;
                    str = null;
                }
            } else {
                i = -1;
                obj = null;
                str = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void writePingFrame() {
        synchronized (this) {
            if (!this.failed) {
                WebSocketWriter webSocketWriter = this.writer;
                try {
                    webSocketWriter.writePing(ByteString.EMPTY);
                } catch (IOException e2) {
                    failWebSocket(e2, null);
                }
            }
        }
    }
}
