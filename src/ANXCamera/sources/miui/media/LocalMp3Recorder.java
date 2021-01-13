package miui.media;

import miui.media.Mp3Recorder;
import miui.media.Recorder;

class LocalMp3Recorder extends Mp3Recorder implements Recorder {
    private Recorder.OnErrorListener mOnErrorListener;

    LocalMp3Recorder() {
    }

    @Override // miui.media.Recorder
    public boolean canPause() {
        return true;
    }

    @Override // miui.media.Recorder
    public void setAudioEncoder(int i) {
    }

    @Override // miui.media.Recorder
    public void setAudioEncodingBitRate(int i) {
        super.setOutBitRate(i);
    }

    @Override // miui.media.Recorder
    public void setMaxDuration(int i) {
        super.setMaxDuration((long) i);
    }

    @Override // miui.media.Recorder
    public void setOnErrorListener(Recorder.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
        super.setOnErrorListener(new Mp3Recorder.RecordingErrorListener() {
            /* class miui.media.LocalMp3Recorder.AnonymousClass1 */

            @Override // miui.media.Mp3Recorder.RecordingErrorListener
            public void onError(Mp3Recorder mp3Recorder, int i) {
                LocalMp3Recorder.this.mOnErrorListener.onError(LocalMp3Recorder.this, RecorderUtils.convertErrorCode(i, false));
            }
        });
    }

    @Override // miui.media.Recorder
    public void setOutputFormat(int i) {
    }
}
