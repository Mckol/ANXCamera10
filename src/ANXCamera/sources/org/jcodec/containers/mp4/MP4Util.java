package org.jcodec.containers.mp4;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.IOUtils;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.FileTypeBox;
import org.jcodec.containers.mp4.boxes.Header;
import org.jcodec.containers.mp4.boxes.MetaBox;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.containers.mp4.boxes.MovieFragmentBox;
import org.jcodec.containers.mp4.boxes.TrakBox;

public class MP4Util {

    public static class Atom {
        private Header header;
        private long offset;

        public Atom(Header header2, long j) {
            this.header = header2;
            this.offset = j;
        }

        public void copy(SeekableByteChannel seekableByteChannel, WritableByteChannel writableByteChannel) throws IOException {
            seekableByteChannel.setPosition(this.offset);
            NIOUtils.copy(seekableByteChannel, writableByteChannel, this.header.getSize());
        }

        public Header getHeader() {
            return this.header;
        }

        public long getOffset() {
            return this.offset;
        }

        public Box parseBox(SeekableByteChannel seekableByteChannel) throws IOException {
            seekableByteChannel.setPosition(this.offset + this.header.headerSize());
            return BoxUtil.parseBox(NIOUtils.fetchFromChannel(seekableByteChannel, (int) this.header.getBodySize()), this.header, BoxFactory.getDefault());
        }
    }

    public static class Movie {
        private FileTypeBox ftyp;
        private MovieBox moov;

        public Movie(FileTypeBox fileTypeBox, MovieBox movieBox) {
            this.ftyp = fileTypeBox;
            this.moov = movieBox;
        }

        public FileTypeBox getFtyp() {
            return this.ftyp;
        }

        public MovieBox getMoov() {
            return this.moov;
        }
    }

    public static Atom atom(SeekableByteChannel seekableByteChannel) throws IOException {
        long position = seekableByteChannel.position();
        Header read = Header.read(NIOUtils.fetchFromChannel(seekableByteChannel, 16));
        if (read == null) {
            return null;
        }
        return new Atom(read, position);
    }

    public static Movie createRefFullMovie(SeekableByteChannel seekableByteChannel, String str) throws IOException {
        TrakBox[] tracks;
        Movie parseFullMovieChannel = parseFullMovieChannel(seekableByteChannel);
        for (TrakBox trakBox : parseFullMovieChannel.moov.getTracks()) {
            trakBox.setDataRef(str);
        }
        return parseFullMovieChannel;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0029  */
    public static Movie createRefFullMovieFromFile(File file) throws IOException {
        Throwable th;
        FileChannelWrapper fileChannelWrapper;
        try {
            fileChannelWrapper = NIOUtils.readableChannel(file);
            try {
                Movie createRefFullMovie = createRefFullMovie(fileChannelWrapper, "file://" + file.getCanonicalPath());
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                return createRefFullMovie;
            } catch (Throwable th2) {
                th = th2;
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannelWrapper = null;
            if (fileChannelWrapper != null) {
            }
            throw th;
        }
    }

    public static MovieBox createRefMovie(SeekableByteChannel seekableByteChannel, String str) throws IOException {
        TrakBox[] tracks;
        MovieBox parseMovieChannel = parseMovieChannel(seekableByteChannel);
        for (TrakBox trakBox : parseMovieChannel.getTracks()) {
            trakBox.setDataRef(str);
        }
        return parseMovieChannel;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0029  */
    public static MovieBox createRefMovieFromFile(File file) throws IOException {
        Throwable th;
        FileChannelWrapper fileChannelWrapper;
        try {
            fileChannelWrapper = NIOUtils.readableChannel(file);
            try {
                MovieBox createRefMovie = createRefMovie(fileChannelWrapper, "file://" + file.getCanonicalPath());
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                return createRefMovie;
            } catch (Throwable th2) {
                th = th2;
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannelWrapper = null;
            if (fileChannelWrapper != null) {
            }
            throw th;
        }
    }

    public static void doWriteFullMovieToChannel(SeekableByteChannel seekableByteChannel, Movie movie, int i) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(estimateMoovBoxSize(movie.getMoov()) + i + 128);
        movie.getFtyp().write(allocate);
        movie.getMoov().write(allocate);
        allocate.flip();
        seekableByteChannel.write(allocate);
    }

    public static void doWriteMovieToChannel(SeekableByteChannel seekableByteChannel, MovieBox movieBox, int i) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate((estimateMoovBoxSize(movieBox) + i) * 4);
        movieBox.write(allocate);
        allocate.flip();
        seekableByteChannel.write(allocate);
    }

    public static int estimateMoovBoxSize(MovieBox movieBox) {
        return movieBox.estimateSize() + 4096;
    }

    public static Atom findFirstAtom(String str, SeekableByteChannel seekableByteChannel) throws IOException {
        for (Atom atom : getRootAtoms(seekableByteChannel)) {
            if (str.equals(atom.getHeader().getFourcc())) {
                return atom;
            }
        }
        return null;
    }

    public static Atom findFirstAtom(String str, SeekableByteChannel seekableByteChannel, long j, long j2) throws IOException {
        seekableByteChannel.setPosition(j);
        long j3 = j;
        while (j3 < seekableByteChannel.size() && j3 < j + j2) {
            seekableByteChannel.setPosition(j3);
            Header read = Header.read(NIOUtils.fetchFromChannel(seekableByteChannel, 16));
            if (read == null) {
                return null;
            }
            if (str.equals(read.getFourcc())) {
                return new Atom(read, j3);
            }
            j3 += read.getSize();
        }
        return null;
    }

    public static List<Atom> getRootAtoms(SeekableByteChannel seekableByteChannel) throws IOException {
        long j = 0;
        seekableByteChannel.setPosition(0);
        ArrayList arrayList = new ArrayList();
        while (j < seekableByteChannel.size()) {
            seekableByteChannel.setPosition(j);
            Header read = Header.read(NIOUtils.fetchFromChannel(seekableByteChannel, 16));
            if (read == null) {
                break;
            }
            arrayList.add(new Atom(read, j));
            j += read.getSize();
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static Movie parseFullMovie(File file) throws IOException {
        FileChannelWrapper fileChannelWrapper;
        Throwable th;
        try {
            fileChannelWrapper = NIOUtils.readableChannel(file);
            try {
                Movie parseFullMovieChannel = parseFullMovieChannel(fileChannelWrapper);
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                return parseFullMovieChannel;
            } catch (Throwable th2) {
                th = th2;
                if (fileChannelWrapper != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannelWrapper = null;
            if (fileChannelWrapper != null) {
                fileChannelWrapper.close();
            }
            throw th;
        }
    }

    public static Movie parseFullMovieChannel(SeekableByteChannel seekableByteChannel) throws IOException {
        FileTypeBox fileTypeBox = null;
        for (Atom atom : getRootAtoms(seekableByteChannel)) {
            if ("ftyp".equals(atom.getHeader().getFourcc())) {
                fileTypeBox = (FileTypeBox) atom.parseBox(seekableByteChannel);
            } else if ("moov".equals(atom.getHeader().getFourcc())) {
                return new Movie(fileTypeBox, (MovieBox) atom.parseBox(seekableByteChannel));
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static MetaBox parseMeta(File file) throws IOException {
        FileChannelWrapper fileChannelWrapper;
        Throwable th;
        try {
            fileChannelWrapper = NIOUtils.readableChannel(file);
            try {
                MetaBox parseMetaChannel = parseMetaChannel(fileChannelWrapper);
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                return parseMetaChannel;
            } catch (Throwable th2) {
                th = th2;
                if (fileChannelWrapper != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannelWrapper = null;
            if (fileChannelWrapper != null) {
                fileChannelWrapper.close();
            }
            throw th;
        }
    }

    public static MetaBox parseMetaChannel(SeekableByteChannel seekableByteChannel) throws IOException {
        Atom findFirstAtom = findFirstAtom("moov", seekableByteChannel, 0, seekableByteChannel.size());
        if (findFirstAtom == null) {
            return null;
        }
        long headerSize = findFirstAtom.getHeader().headerSize();
        Atom findFirstAtom2 = findFirstAtom(MetaBox.fourcc(), seekableByteChannel, findFirstAtom.offset + headerSize, findFirstAtom.getHeader().getSize() - headerSize);
        if (findFirstAtom2 == null) {
            return null;
        }
        return (MetaBox) findFirstAtom2.parseBox(seekableByteChannel);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static MovieBox parseMovie(File file) throws IOException {
        FileChannelWrapper fileChannelWrapper;
        Throwable th;
        try {
            fileChannelWrapper = NIOUtils.readableChannel(file);
            try {
                MovieBox parseMovieChannel = parseMovieChannel(fileChannelWrapper);
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                return parseMovieChannel;
            } catch (Throwable th2) {
                th = th2;
                if (fileChannelWrapper != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannelWrapper = null;
            if (fileChannelWrapper != null) {
                fileChannelWrapper.close();
            }
            throw th;
        }
    }

    public static MovieBox parseMovieChannel(SeekableByteChannel seekableByteChannel) throws IOException {
        for (Atom atom : getRootAtoms(seekableByteChannel)) {
            if ("moov".equals(atom.getHeader().getFourcc())) {
                return (MovieBox) atom.parseBox(seekableByteChannel);
            }
        }
        return null;
    }

    public static List<MovieFragmentBox> parseMovieFragments(SeekableByteChannel seekableByteChannel) throws IOException {
        LinkedList linkedList = new LinkedList();
        MovieBox movieBox = null;
        for (Atom atom : getRootAtoms(seekableByteChannel)) {
            if ("moov".equals(atom.getHeader().getFourcc())) {
                movieBox = (MovieBox) atom.parseBox(seekableByteChannel);
            } else if ("moof".equalsIgnoreCase(atom.getHeader().getFourcc())) {
                linkedList.add((MovieFragmentBox) atom.parseBox(seekableByteChannel));
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ((MovieFragmentBox) it.next()).setMovie(movieBox);
        }
        return linkedList;
    }

    public static ByteBuffer writeBox(Box box, int i) {
        ByteBuffer allocate = ByteBuffer.allocate(i);
        box.write(allocate);
        allocate.flip();
        return allocate;
    }

    public static void writeFullMovie(SeekableByteChannel seekableByteChannel, Movie movie) throws IOException {
        doWriteFullMovieToChannel(seekableByteChannel, movie, 0);
    }

    public static void writeFullMovieToFile(File file, Movie movie) throws IOException {
        Throwable th;
        FileChannelWrapper fileChannelWrapper;
        try {
            fileChannelWrapper = NIOUtils.writableChannel(file);
            try {
                writeFullMovie(fileChannelWrapper, movie);
                IOUtils.closeQuietly(fileChannelWrapper);
            } catch (Throwable th2) {
                th = th2;
                IOUtils.closeQuietly(fileChannelWrapper);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannelWrapper = null;
            IOUtils.closeQuietly(fileChannelWrapper);
            throw th;
        }
    }

    public static void writeMovie(SeekableByteChannel seekableByteChannel, MovieBox movieBox) throws IOException {
        doWriteMovieToChannel(seekableByteChannel, movieBox, 0);
    }

    public static void writeMovieToFile(File file, MovieBox movieBox) throws IOException {
        Throwable th;
        FileChannelWrapper fileChannelWrapper;
        try {
            fileChannelWrapper = NIOUtils.writableChannel(file);
            try {
                writeMovie(fileChannelWrapper, movieBox);
                IOUtils.closeQuietly(fileChannelWrapper);
            } catch (Throwable th2) {
                th = th2;
                IOUtils.closeQuietly(fileChannelWrapper);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannelWrapper = null;
            IOUtils.closeQuietly(fileChannelWrapper);
            throw th;
        }
    }
}
