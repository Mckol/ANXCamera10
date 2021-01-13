package miui.media;

class LocalAMRRecorder extends LocalMediaRecorder {
    private static final String AMR_NB_HEADER = "#!AMR\n";
    private static final String AMR_WB_HEADER = "#!AMR-WB\n";
    private boolean mIsAmrWB;

    LocalAMRRecorder() {
    }

    @Override // miui.media.LocalMediaRecorder, miui.media.Recorder
    public boolean canPause() {
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // miui.media.LocalMediaRecorder
    public int getHeaderLen() {
        return this.mIsAmrWB ? 9 : 6;
    }

    @Override // miui.media.LocalMediaRecorder, miui.media.Recorder
    public void release() {
        super.release();
        this.mIsAmrWB = false;
    }

    @Override // miui.media.LocalMediaRecorder, miui.media.Recorder
    public void reset() {
        super.reset();
        this.mIsAmrWB = false;
    }

    @Override // miui.media.LocalMediaRecorder, miui.media.Recorder
    public void setAudioEncoder(int i) {
        super.setAudioEncoder(i);
        if (i == 2) {
            this.mIsAmrWB = true;
        } else if (i == 1) {
            this.mIsAmrWB = false;
        }
    }
}
