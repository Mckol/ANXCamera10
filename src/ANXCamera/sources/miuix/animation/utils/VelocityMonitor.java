package miuix.animation.utils;

import android.os.SystemClock;
import java.util.Arrays;
import java.util.LinkedList;

public class VelocityMonitor {
    private static final long MAX_DELTA = 100;
    private static final int MAX_RECORD_COUNT = 10;
    private static final long MIN_DELTA = 30;
    private static final long TIME_THRESHOLD = 50;
    private LinkedList<MoveRecord> mHistory = new LinkedList<>();
    private float[] mVelocity;

    /* access modifiers changed from: private */
    public static class MoveRecord {
        long timeStamp;
        double[] values;

        private MoveRecord() {
        }
    }

    private void addAndUpdate(MoveRecord moveRecord) {
        this.mHistory.add(moveRecord);
        if (this.mHistory.size() > 10) {
            this.mHistory.remove(0);
        }
        updateVelocity();
    }

    private float calVelocity(int i, MoveRecord moveRecord, MoveRecord moveRecord2) {
        long j;
        MoveRecord moveRecord3;
        float f;
        double d2 = moveRecord.values[i];
        long j2 = moveRecord.timeStamp;
        double velocity = (double) getVelocity(d2, moveRecord2.values[i], j2 - moveRecord2.timeStamp);
        int size = this.mHistory.size() - 2;
        long j3 = 0;
        MoveRecord moveRecord4 = null;
        while (true) {
            if (size < 0) {
                j = j3;
                moveRecord3 = moveRecord4;
                f = Float.MAX_VALUE;
                break;
            }
            moveRecord3 = this.mHistory.get(size);
            long j4 = j2 - moveRecord3.timeStamp;
            if (j4 <= MIN_DELTA || j4 >= MAX_DELTA) {
                if (j4 > MAX_DELTA) {
                    this.mHistory.remove(size);
                }
                size--;
                moveRecord4 = moveRecord3;
                j3 = j4;
            } else {
                f = getVelocity(d2, moveRecord3.values[i], j4);
                double d3 = (double) f;
                if (velocity * d3 > 0.0d) {
                    f = (float) (f > 0.0f ? Math.max(velocity, d3) : Math.min(velocity, d3));
                }
                j = j4;
            }
        }
        return (f != Float.MAX_VALUE || moveRecord3 == null) ? f : getVelocity(d2, moveRecord3.values[i], j);
    }

    private void clearVelocity() {
        float[] fArr = this.mVelocity;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
        }
    }

    private MoveRecord getMoveRecord() {
        MoveRecord moveRecord = new MoveRecord();
        moveRecord.timeStamp = SystemClock.uptimeMillis();
        return moveRecord;
    }

    private float getVelocity(double d2, double d3, long j) {
        return (float) (j == 0 ? 0.0d : (d2 - d3) / ((double) (((float) j) / 1000.0f)));
    }

    private void updateVelocity() {
        int size = this.mHistory.size();
        if (size >= 2) {
            MoveRecord last = this.mHistory.getLast();
            MoveRecord moveRecord = this.mHistory.get(size - 2);
            float[] fArr = this.mVelocity;
            if (fArr == null || fArr.length < last.values.length) {
                this.mVelocity = new float[last.values.length];
            }
            for (int i = 0; i < last.values.length; i++) {
                this.mVelocity[i] = calVelocity(i, last, moveRecord);
            }
            return;
        }
        clearVelocity();
    }

    public void clear() {
        this.mHistory.clear();
        clearVelocity();
    }

    public float getVelocity(int i) {
        float[] fArr;
        long uptimeMillis = SystemClock.uptimeMillis();
        if ((this.mHistory.size() <= 0 || Math.abs(uptimeMillis - this.mHistory.getLast().timeStamp) <= TIME_THRESHOLD) && (fArr = this.mVelocity) != null && fArr.length > i) {
            return fArr[i];
        }
        return 0.0f;
    }

    public void update(double... dArr) {
        if (dArr != null && dArr.length != 0) {
            MoveRecord moveRecord = getMoveRecord();
            moveRecord.values = dArr;
            addAndUpdate(moveRecord);
        }
    }

    public void update(float... fArr) {
        if (!(fArr == null || fArr.length == 0)) {
            MoveRecord moveRecord = getMoveRecord();
            moveRecord.values = new double[fArr.length];
            for (int i = 0; i < fArr.length; i++) {
                moveRecord.values[i] = (double) fArr[i];
            }
            addAndUpdate(moveRecord);
        }
    }
}
