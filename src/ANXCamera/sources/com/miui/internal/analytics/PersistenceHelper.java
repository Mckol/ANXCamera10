package com.miui.internal.analytics;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import miui.util.AppConstants;
import miui.util.SoftReferenceSingleton;

public class PersistenceHelper {
    private static final SoftReferenceSingleton<PersistenceHelper> INSTANCE = new SoftReferenceSingleton<PersistenceHelper>() {
        /* class com.miui.internal.analytics.PersistenceHelper.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public PersistenceHelper createInstance() {
            return new PersistenceHelper();
        }
    };
    private static final int MSG_WRITE_EVENT = 1;
    private static final String TAG = "PersistenceHelper";
    private Context mContext;
    private ObjectBuilder<Event> mEventBuilder;
    private Storable mStore;
    private ObjectBuilder<Storable> mStoreBuilder;
    private Handler mWorkHandler;
    private final AtomicBoolean mWorkerReady;

    private class RunThread extends Thread {
        private RunThread() {
        }

        public void run() {
            Looper.prepare();
            synchronized (PersistenceHelper.this.mWorkerReady) {
                PersistenceHelper.this.mWorkHandler = new Handler() {
                    /* class com.miui.internal.analytics.PersistenceHelper.RunThread.AnonymousClass1 */

                    public void handleMessage(Message message) {
                        if (message.what == 1) {
                            PersistenceHelper.this.writeEventImp((Event) message.obj);
                        }
                    }
                };
                PersistenceHelper.this.mWorkerReady.set(true);
                PersistenceHelper.this.mWorkerReady.notify();
            }
            Looper.loop();
        }
    }

    private PersistenceHelper() {
        this.mWorkerReady = new AtomicBoolean();
        this.mStoreBuilder = new ObjectBuilder<>();
        this.mEventBuilder = new ObjectBuilder<>();
        this.mEventBuilder.registerClass(TrackEvent.class, "2");
        this.mEventBuilder.registerClass(LogEvent.class, "1");
        this.mEventBuilder.registerClass(TrackPageViewEvent.class, "3");
        this.mStoreBuilder.registerClass(SQLiteStore.class, SQLiteStore.TAG);
        this.mContext = AppConstants.getCurrentApplication();
        createStore();
        synchronized (this.mWorkerReady) {
            new RunThread().start();
            while (!this.mWorkerReady.get()) {
                try {
                    this.mWorkerReady.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    private void createStore() {
        this.mStore = this.mStoreBuilder.buildObject(SQLiteStore.TAG);
        this.mStore.create(this.mContext);
    }

    public static PersistenceHelper getInstance() {
        return INSTANCE.get();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void writeEventImp(Event event) {
        Storable storable = this.mStore;
        if (storable != null) {
            event.writeEvent(storable);
        }
    }

    public void close() {
        this.mWorkHandler.getLooper().quit();
        this.mWorkHandler = null;
        Storable storable = this.mStore;
        if (storable != null) {
            storable.close();
            this.mStore = null;
        }
        this.mContext = null;
    }

    public List<Event> readEvents(String str, List<Item> list) {
        Cursor readDataset;
        Storable storable = this.mStore;
        if (storable == null || (readDataset = storable.readDataset(str)) == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        while (readDataset.moveToNext()) {
            try {
                int i = readDataset.getInt(readDataset.getColumnIndexOrThrow("type"));
                ObjectBuilder<Event> objectBuilder = this.mEventBuilder;
                Event buildObject = objectBuilder.buildObject(i + "");
                if (buildObject != null) {
                    buildObject.restore(readDataset);
                    Iterator<Item> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Item next = it.next();
                        if (next.idMatches(buildObject.getEventId())) {
                            if (next.isDispatch(Build.VERSION.INCREMENTAL)) {
                                buildObject.setPolicy(next.getPolicy());
                                arrayList.add(buildObject);
                            }
                        }
                    }
                }
            } catch (IllegalArgumentException e2) {
                Log.e(TAG, "IllegalArgumentException catched when readEvents from storage", e2);
            }
        }
        readDataset.close();
        return arrayList;
    }

    public boolean selectStore(String str) {
        Storable buildObject = this.mStoreBuilder.buildObject(str);
        if (buildObject == null) {
            return false;
        }
        Storable storable = this.mStore;
        if (storable != null) {
            storable.close();
        }
        this.mStore = buildObject;
        return true;
    }

    public void writeEvent(Event event) {
        if (this.mWorkHandler != null) {
            Message message = new Message();
            message.what = 1;
            message.obj = event;
            this.mWorkHandler.sendMessage(message);
        }
    }

    public void writeEvent(String str, String str2, long j) {
        writeEvent(new TrackEvent(str, str2, null, j));
    }
}
