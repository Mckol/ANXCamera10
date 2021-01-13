package miui.media;

class LocalAACRecorder extends LocalMediaRecorder {
    LocalAACRecorder() {
    }

    @Override // miui.media.LocalMediaRecorder, miui.media.Recorder
    public boolean canPause() {
        return true;
    }

    @Override // miui.media.LocalMediaRecorder, miui.media.Recorder
    public void setAudioSource(int i) {
        super.setAudioSource(i);
        setOutputFormat(6);
        setAudioEncoder(3);
    }
}
