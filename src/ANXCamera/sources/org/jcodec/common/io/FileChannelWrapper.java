package org.jcodec.common.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelWrapper implements SeekableByteChannel {
    private FileChannel ch;

    public FileChannelWrapper(FileChannel fileChannel) throws FileNotFoundException {
        this.ch = fileChannel;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public void close() throws IOException {
        this.ch.close();
    }

    public boolean isOpen() {
        return this.ch.isOpen();
    }

    @Override // org.jcodec.common.io.SeekableByteChannel
    public long position() throws IOException {
        return this.ch.position();
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        return this.ch.read(byteBuffer);
    }

    @Override // org.jcodec.common.io.SeekableByteChannel
    public SeekableByteChannel setPosition(long j) throws IOException {
        this.ch.position(j);
        return this;
    }

    @Override // org.jcodec.common.io.SeekableByteChannel
    public long size() throws IOException {
        return this.ch.size();
    }

    @Override // org.jcodec.common.io.SeekableByteChannel
    public SeekableByteChannel truncate(long j) throws IOException {
        this.ch.truncate(j);
        return this;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) throws IOException {
        return this.ch.write(byteBuffer);
    }
}
