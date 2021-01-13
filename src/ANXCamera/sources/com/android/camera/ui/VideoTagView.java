package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import miui.view.animation.CubicEaseOutInterpolator;

public class VideoTagView implements View.OnClickListener {
    private static final int DEFAULT_TAG_MARGIN_LEFT = Util.dpToPixel(14.9f);
    private static final int DEFAULT_TAG_MARGIN_RIGHT = Util.dpToPixel(13.1f);
    private static final int SPLIT_LINE_MARGIN = Util.dpToPixel(8.7f);
    private static final String TAG = "VideoTagView";
    private static final int VERTICAL_TAG = 100;
    public static final int VIDEO_TAG_STATE_PAUSE = 2;
    public static final int VIDEO_TAG_STATE_PREPARE = 0;
    public static final int VIDEO_TAG_STATE_RESUME = 3;
    public static final int VIDEO_TAG_STATE_START = 1;
    public static final int VIDEO_TAG_STATE_STOP = 4;
    private boolean isRecordingPause;
    private boolean isShowTagValue;
    private boolean isTagRecording;
    private Context mContext;
    private long mNeedRemoveTime;
    private long mPauseRecordingTime;
    private long mStartRecordingTime;
    private TextView mTagCountValue;
    private FrameLayout mTagFullLayout;
    private View mVideoTag;
    private AnimatorSet mVideoTagExpandAnim;
    private AnimatorSet mVideoTagHideAnim;
    private int mVideoTagNormalSize;
    private int recordingPauseTagCount = 0;
    private StringBuilder srtBuilder = new StringBuilder();
    private int videoTagCount = 0;

    /* access modifiers changed from: private */
    public class VideoTagCapsuleEvaluator implements TypeEvaluator<PointF> {
        private PointF pointA;
        private PointF pointB;

        private VideoTagCapsuleEvaluator() {
            this.pointA = new PointF(0.2f, 1.8f);
            this.pointB = new PointF(0.4f, 1.0f);
        }

        public PointF evaluate(float f, PointF pointF, PointF pointF2) {
            float f2 = 1.0f - f;
            PointF pointF3 = this.pointA;
            PointF pointF4 = this.pointB;
            return new PointF((pointF.x * f2 * f2 * f2) + (pointF3.x * 3.0f * f * f2 * f2) + (pointF4.x * 3.0f * f * f * f2) + (pointF2.x * f * f * f), (pointF.y * f2 * f2 * f2) + (pointF3.y * 3.0f * f * f2 * f2) + (pointF4.y * 3.0f * f * f * f2) + (pointF2.y * f * f * f));
        }
    }

    /* access modifiers changed from: private */
    public class VideoTagCountEvaluator implements TypeEvaluator<PointF> {
        private PointF pointA;
        private PointF pointB;

        private VideoTagCountEvaluator() {
            this.pointA = new PointF(0.2f, 1.6f);
            this.pointB = new PointF(0.2f, 1.0f);
        }

        public PointF evaluate(float f, PointF pointF, PointF pointF2) {
            float f2 = 1.0f - f;
            PointF pointF3 = this.pointA;
            PointF pointF4 = this.pointB;
            return new PointF((pointF.x * f2 * f2 * f2) + (pointF3.x * 3.0f * f * f2 * f2) + (pointF4.x * 3.0f * f * f * f2) + (pointF2.x * f * f * f), (pointF.y * f2 * f2 * f2) + (pointF3.y * 3.0f * f * f2 * f2) + (pointF4.y * 3.0f * f * f * f2) + (pointF2.y * f * f * f));
        }
    }

    private String getTime(long j) {
        long j2 = (j - this.mStartRecordingTime) - this.mNeedRemoveTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return simpleDateFormat.format(Long.valueOf(j2));
    }

    private void initVideoTagAnimator() {
        if (this.mVideoTagExpandAnim == null) {
            ValueAnimator ofObject = ValueAnimator.ofObject(new VideoTagCapsuleEvaluator(), new PointF(0.0f, 0.0f), new PointF(1.0f, 1.0f));
            ofObject.addUpdateListener(new p(this));
            ValueAnimator ofObject2 = ValueAnimator.ofObject(new VideoTagCountEvaluator(), new PointF(0.0f, 0.0f), new PointF(1.0f, 1.0f));
            ofObject2.addUpdateListener(new m(this));
            this.mVideoTagExpandAnim = new AnimatorSet();
            this.mVideoTagExpandAnim.playTogether(ofObject, ofObject2);
            this.mVideoTagExpandAnim.setDuration(600L);
        }
        if (this.mVideoTagHideAnim == null) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat.setInterpolator(new CubicEaseOutInterpolator());
            ofFloat.addUpdateListener(new o(this));
            ofFloat.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.ui.VideoTagView.AnonymousClass1 */

                public void onAnimationCancel(Animator animator) {
                    VideoTagView.this.isShowTagValue = false;
                    VideoTagView.this.resetTagView();
                }

                public void onAnimationEnd(Animator animator) {
                    VideoTagView.this.isShowTagValue = false;
                    VideoTagView.this.resetTagView();
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat2.setInterpolator(new CubicEaseOutInterpolator());
            ofFloat2.addUpdateListener(new n(this));
            this.mVideoTagHideAnim = new AnimatorSet();
            this.mVideoTagHideAnim.setDuration(200L);
            this.mVideoTagHideAnim.setStartDelay(1000);
            this.mVideoTagHideAnim.playTogether(ofFloat, ofFloat2);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetTagView() {
        if (this.isTagRecording) {
            this.mTagFullLayout.setVisibility(0);
        } else {
            this.mTagFullLayout.setVisibility(8);
        }
        this.mTagFullLayout.setEnabled(true);
    }

    private void setTagLayoutWidth(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mVideoTag.getLayoutParams();
        layoutParams.width = i;
        this.mVideoTag.setLayoutParams(layoutParams);
        if (!this.isShowTagValue) {
            resetTagView();
        } else if (this.mTagCountValue.getVisibility() != 0) {
            this.mTagCountValue.setVisibility(0);
        }
    }

    private void setViewMargin(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.height = Util.sTopBarHeight;
        marginLayoutParams.topMargin = Util.sTopMargin;
        view.setLayoutParams(marginLayoutParams);
    }

    private void updateTagValueView(TextView textView) {
        int i = this.videoTagCount;
        if (i < 10) {
            textView.setText("0" + this.videoTagCount);
        } else {
            textView.setText(String.valueOf(i));
        }
        float measureText = textView.getPaint().measureText((String) textView.getText());
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTagCountValue.getLayoutParams();
        int i2 = (int) measureText;
        marginLayoutParams.width = i2;
        marginLayoutParams.setMarginEnd(-i2);
        this.mTagCountValue.setLayoutParams(marginLayoutParams);
        initVideoTagAnimator();
        AnimatorSet animatorSet = this.mVideoTagExpandAnim;
        if (animatorSet != null) {
            animatorSet.start();
        }
        AnimatorSet animatorSet2 = this.mVideoTagHideAnim;
        if (animatorSet2 != null) {
            animatorSet2.start();
        }
    }

    private void updateTagView() {
        resetTagView();
    }

    public /* synthetic */ void e(ValueAnimator valueAnimator) {
        float f = ((PointF) valueAnimator.getAnimatedValue()).y;
        int i = this.mVideoTagNormalSize;
        setTagLayoutWidth((int) ((f * ((float) i)) + ((float) i)));
    }

    public /* synthetic */ void f(ValueAnimator valueAnimator) {
        this.mTagCountValue.setTranslationX(-(((PointF) valueAnimator.getAnimatedValue()).y * ((float) this.mVideoTagNormalSize)));
    }

    public /* synthetic */ void g(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        int i = this.mVideoTagNormalSize;
        setTagLayoutWidth((int) ((floatValue * ((float) i)) + ((float) i)));
    }

    public String getVideoTagContent() {
        return this.srtBuilder.toString();
    }

    public /* synthetic */ void h(ValueAnimator valueAnimator) {
        this.mTagCountValue.setTranslationX(-(((Float) valueAnimator.getAnimatedValue()).floatValue() * ((float) this.mVideoTagNormalSize)));
    }

    public void init(View view, Context context) {
        this.mContext = context;
        this.mVideoTagNormalSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.video_tag_normal_size);
        this.mTagFullLayout = (FrameLayout) view.findViewById(R.id.video_tag_layout);
        this.mVideoTag = view.findViewById(R.id.video_tag);
        this.mTagCountValue = (TextView) this.mTagFullLayout.findViewById(R.id.video_tag_value);
        this.mTagFullLayout.setOnClickListener(this);
        setViewMargin(this.mTagFullLayout);
    }

    public void onClick(View view) {
        String str;
        this.isShowTagValue = true;
        this.mTagFullLayout.setEnabled(false);
        if (this.isRecordingPause) {
            int i = this.recordingPauseTagCount;
            if (i == 0) {
                this.recordingPauseTagCount = i + 1;
                str = getTime(this.mPauseRecordingTime);
            } else {
                updateTagValueView(this.mTagCountValue);
                return;
            }
        } else {
            str = getTime(System.currentTimeMillis());
        }
        this.videoTagCount++;
        this.srtBuilder.append(this.videoTagCount + "\n");
        this.srtBuilder.append(String.format("%s\n", str));
        this.srtBuilder.append("\n");
        Log.d(TAG, "video tag number: " + this.videoTagCount);
        updateTagValueView(this.mTagCountValue);
    }

    public void pause() {
        Log.d(TAG, "handleTagRecordingPause: ");
        this.isRecordingPause = true;
        this.recordingPauseTagCount = 0;
        this.mPauseRecordingTime = System.currentTimeMillis();
    }

    public void prepare() {
        Log.d(TAG, "handleTagRecordingPrepare: ");
        this.videoTagCount = 0;
        this.mNeedRemoveTime = 0;
        this.isShowTagValue = true;
        this.isRecordingPause = false;
        this.isTagRecording = true;
        updateTagView();
        if (this.srtBuilder.length() != 0) {
            StringBuilder sb = this.srtBuilder;
            sb.delete(0, sb.length());
        }
    }

    public void resume() {
        Log.d(TAG, "handleTagRecordingResume: ");
        this.isRecordingPause = false;
        this.mNeedRemoveTime += System.currentTimeMillis() - this.mPauseRecordingTime;
    }

    public void start() {
        Log.d(TAG, "handleTagRecordingStart: ");
        this.mStartRecordingTime = System.currentTimeMillis();
    }

    public void stop() {
        Log.d(TAG, "handleTagRecordingStop: ");
        this.isRecordingPause = false;
        this.videoTagCount = 0;
        this.isTagRecording = false;
        this.mTagFullLayout.setVisibility(8);
    }
}
