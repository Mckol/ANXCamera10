.class public interface abstract Lcom/android/camera/protocol/ModeProtocol;
.super Ljava/lang/Object;
.source "ModeProtocol.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/protocol/ModeProtocol$ModeListManager;,
        Lcom/android/camera/protocol/ModeProtocol$MagneticSensorDetect;,
        Lcom/android/camera/protocol/ModeProtocol$KeyEvent;,
        Lcom/android/camera/protocol/ModeProtocol$ModuleFeature;,
        Lcom/android/camera/protocol/ModeProtocol$SpeechShutterDetect;,
        Lcom/android/camera/protocol/ModeProtocol$AIWatermarkDetect;,
        Lcom/android/camera/protocol/ModeProtocol$MiAsdDetect;,
        Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;,
        Lcom/android/camera/protocol/ModeProtocol$RecordState;,
        Lcom/android/camera/protocol/ModeProtocol$LensProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$OnShineChangedProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$VerticalProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$BottomMenuProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$FullScreenProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$WatermarkProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$MiBeautyProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$MakeupProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$StickerProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;,
        Lcom/android/camera/protocol/ModeProtocol$FastMotionProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;,
        Lcom/android/camera/protocol/ModeProtocol$DualController;,
        Lcom/android/camera/protocol/ModeProtocol$ManuallyValueChanged;,
        Lcom/android/camera/protocol/ModeProtocol$DualVideoRenderProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$ModuleContent;,
        Lcom/android/camera/protocol/ModeProtocol$SubtitleRecording;,
        Lcom/android/camera/protocol/ModeProtocol$BeautyRecording;,
        Lcom/android/camera/protocol/ModeProtocol$HandleBeautyRecording;,
        Lcom/android/camera/protocol/ModeProtocol$HandlerSwitcher;,
        Lcom/android/camera/protocol/ModeProtocol$FilterProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$BackStack;,
        Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;,
        Lcom/android/camera/protocol/ModeProtocol$GestureDetected;,
        Lcom/android/camera/protocol/ModeProtocol$WideSelfieProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$PanoramaProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$SnapShotIndicator;,
        Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$ShowType;,
        Lcom/android/camera/protocol/ModeProtocol$IndicatorType;,
        Lcom/android/camera/protocol/ModeProtocol$EffectCropViewController;,
        Lcom/android/camera/protocol/ModeProtocol$ZoomProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$EvChangedProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$AdjustEvState;,
        Lcom/android/camera/protocol/ModeProtocol$PlayVideoProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$TopAlertEvent;,
        Lcom/android/camera/protocol/ModeProtocol$PortraitLighting;,
        Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;,
        Lcom/android/camera/protocol/ModeProtocol$MoreModePopupController;,
        Lcom/android/camera/protocol/ModeProtocol$ModeChangeController;,
        Lcom/android/camera/protocol/ModeProtocol$CameraActionTrack;,
        Lcom/android/camera/protocol/ModeProtocol$CameraModuleSpecial;,
        Lcom/android/camera/protocol/ModeProtocol$CloneAction;,
        Lcom/android/camera/protocol/ModeProtocol$CameraAction;,
        Lcom/android/camera/protocol/ModeProtocol$TopAlert;,
        Lcom/android/camera/protocol/ModeProtocol$IDCardModeProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$MimojiGifEditor;,
        Lcom/android/camera/protocol/ModeProtocol$MimojiAlert;,
        Lcom/android/camera/protocol/ModeProtocol$MimojiEditor;,
        Lcom/android/camera/protocol/ModeProtocol$MimojiAvatarEngine;,
        Lcom/android/camera/protocol/ModeProtocol$AmbilightSelector;,
        Lcom/android/camera/protocol/ModeProtocol$AmbilightProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$MiLivePlayerControl;,
        Lcom/android/camera/protocol/ModeProtocol$MiLiveConfigChanges;,
        Lcom/android/camera/protocol/ModeProtocol$LiveRecordState;,
        Lcom/android/camera/protocol/ModeProtocol$LiveBeautyChanges;,
        Lcom/android/camera/protocol/ModeProtocol$LiveFilterChangers;,
        Lcom/android/camera/protocol/ModeProtocol$LiveAudioChanges;,
        Lcom/android/camera/protocol/ModeProtocol$MiLiveRecorderControl;,
        Lcom/android/camera/protocol/ModeProtocol$LiveEditor2;,
        Lcom/android/camera/protocol/ModeProtocol$CloneProcess;,
        Lcom/android/camera/protocol/ModeProtocol$CloneChooser;,
        Lcom/android/camera/protocol/ModeProtocol$LiveVVProcess;,
        Lcom/android/camera/protocol/ModeProtocol$LiveVVChooser;,
        Lcom/android/camera/protocol/ModeProtocol$LiveVVExternal;,
        Lcom/android/camera/protocol/ModeProtocol$LiveModuleSub;,
        Lcom/android/camera/protocol/ModeProtocol$LiveRecordControl;,
        Lcom/android/camera/protocol/ModeProtocol$LiveGenericControl;,
        Lcom/android/camera/protocol/ModeProtocol$LiveConfigVV;,
        Lcom/android/camera/protocol/ModeProtocol$LiveConfigChanges;,
        Lcom/android/camera/protocol/ModeProtocol$LiveSpeedChanges;,
        Lcom/android/camera/protocol/ModeProtocol$LiveVideoEditor;,
        Lcom/android/camera/protocol/ModeProtocol$KaleidoscopeProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$AutoZoomModuleProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$AutoZoomViewProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;,
        Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;,
        Lcom/android/camera/protocol/ModeProtocol$EventType;,
        Lcom/android/camera/protocol/ModeProtocol$LyingDirectHint;,
        Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;,
        Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;,
        Lcom/android/camera/protocol/ModeProtocol$ProtocolType;
    }
.end annotation


# static fields
.field public static final ADJUST_EV_START:I = 0x1

.field public static final ADJUST_EV_STOP:I = 0x2

.field public static final APPLY_EV_IMMEDIATELY:I = 0x3

.field public static final EVENT_AI_WATERMARK:I = 0x15

.field public static final EVENT_AMBILIGHT:I = 0x18

.field public static final EVENT_BEAUTY:I = 0x2

.field public static final EVENT_BLANK_BEAUTY:I = 0xa

.field public static final EVENT_CLONE_USE_GUIDE:I = 0x17

.field public static final EVENT_FAST_MOTION:I = 0x22

.field public static final EVENT_GIF_EDIT:I = 0x14

.field public static final EVENT_INTENT_SHOT_FINISH:I = 0x6

.field public static final EVENT_KALEIDOSCOPE:I = 0x12

.field public static final EVENT_LIVE_REVIEW:I = 0xb

.field public static final EVENT_LIVE_SPEED:I = 0xd

.field public static final EVENT_LIVE_STICKER:I = 0xc

.field public static final EVENT_MAKEUP:I = 0x3

.field public static final EVENT_MIMOJI:I = 0xe

.field public static final EVENT_MIMOJI_EMOTICON:I = 0x13

.field public static final EVENT_MODE_EDIT:I = 0x20

.field public static final EVENT_MODE_MORE_NORMAL:I = 0x1e

.field public static final EVENT_MODE_MORE_POPUP:I = 0x1f

.field public static final EVENT_PORTAIT_LIGHTING:I = 0x21

.field public static final EVENT_RESTORE_BOTTOM_ACTION:I = 0x7

.field public static final EVENT_RESTORE_FRAGMENT:I = 0x10

.field public static final EVENT_SUBTITLE:I = 0x11

.field public static final EVENT_VV:I = 0xf

.field public static final INDICATOR_FACE_VIEW:I = 0x1

.field public static final INDICATOR_FOCUS_VIEW:I = 0x2

.field public static final INDICATOR_OBJECT_VIEW:I = 0x3

.field public static final PROTOCOL_ACTION:I = 0xa1

.field public static final PROTOCOL_ACTION_PROCESSING:I = 0xa2

.field public static final PROTOCOL_ACTION_TRACK:I = 0xba

.field public static final PROTOCOL_AI_WATERMARK:I = 0xfd

.field public static final PROTOCOL_AI_WATERMARK_DETECT:I = 0xfe

.field public static final PROTOCOL_AMBILIGHT:I = 0x1a4

.field public static final PROTOCOL_AMBILIGHT_SELECTOR:I = 0x1a5

.field public static final PROTOCOL_AUTO_ZOOM_MODULE:I = 0xd7

.field public static final PROTOCOL_AUTO_ZOOM_VIEW:I = 0xd6

.field public static final PROTOCOL_BACK_STACK:I = 0xab

.field public static final PROTOCOL_BEAUTY_RECORDING:I = 0xad

.field public static final PROTOCOL_BEAUTY_SHOW_STATUS:I = 0xd5

.field public static final PROTOCOL_BOKEH_F_NUMBER:I = 0xd2

.field public static final PROTOCOL_BOTTOM_MENU:I = 0xc5

.field public static final PROTOCOL_BOTTOM_POPUP_TIPS:I = 0xaf

.field public static final PROTOCOL_CAMERA_CLICK_OBSERVABLE:I = 0xe3

.field public static final PROTOCOL_CAMERA_MODULE_SPEC:I = 0xc3

.field public static final PROTOCOL_CLONE_ACTION:I = 0x1a3

.field public static final PROTOCOL_CLONE_CHOOSER:I = 0x1a0

.field public static final PROTOCOL_CLONE_PROCESS:I = 0x1a2

.field public static final PROTOCOL_CONFIG_CHANGE:I = 0xa4

.field public static final PROTOCOL_DELEGATE:I = 0xa0

.field public static final PROTOCOL_DUAL_CONTROLLER:I = 0xb6

.field public static final PROTOCOL_DUAL_VIDEO_RENDER:I = 0x1ae

.field public static final PROTOCOL_EFFECT_CONTROL:I = 0xa5

.field public static final PROTOCOL_EV_CHANGED:I = 0xa9

.field public static final PROTOCOL_FACE_BEAUTY:I = 0xb9

.field public static final PROTOCOL_FAST_MOTION:I = 0x2a2

.field public static final PROTOCOL_FILTER_SWITCHER:I = 0xb7

.field public static final PROTOCOL_FRONT_BEAUTY:I = 0xc2

.field public static final PROTOCOL_ID_CARD:I = 0xe9

.field public static final PROTOCOL_KALEIDOSCOPE:I = 0xec

.field public static final PROTOCOL_KEY_EVENT:I = 0xef

.field public static final PROTOCOL_LIVE_BEAUTY:I = 0xf4

.field public static final PROTOCOL_LIVE_CONFIG:I = 0xc9

.field public static final PROTOCOL_LIVE_CONFIG_VV:I = 0xe4

.field public static final PROTOCOL_LIVE_FILTER:I = 0xf3

.field public static final PROTOCOL_LIVE_MUSIC:I = 0xd3

.field public static final PROTOCOL_LIVE_SPEED:I = 0xe8

.field public static final PROTOCOL_LIVE_STATE:I = 0xf5

.field public static final PROTOCOL_LIVE_VIDEO_EDITOR:I = 0xd1

.field public static final PROTOCOL_LIVE_VV_CHOOSER:I = 0xe5

.field public static final PROTOCOL_LIVE_VV_PROCESS:I = 0xe6

.field public static final PROTOCOL_MAGNETIC_SENSOR_DETECT:I = 0xa10

.field public static final PROTOCOL_MAIN_CONTENT:I = 0xa6

.field public static final PROTOCOL_MAKEUP_CONTROL:I = 0xb4

.field public static final PROTOCOL_MANUALLY_ADJUST:I = 0xb5

.field public static final PROTOCOL_MANUALLY_CHANGE:I = 0xae

.field public static final PROTOCOL_MIMOJI:I = 0xd9

.field public static final PROTOCOL_MIMOJI2:I = 0xf6

.field public static final PROTOCOL_MIMOJI_ALERT:I = 0xe2

.field public static final PROTOCOL_MIMOJI_BOTTOM_LIST:I = 0xf8

.field public static final PROTOCOL_MIMOJI_DATA_BASE:I = 0xe1

.field public static final PROTOCOL_MIMOJI_EDITOR:I = 0xe0

.field public static final PROTOCOL_MIMOJI_EDITOR2:I = 0xf7

.field public static final PROTOCOL_MIMOJI_EMOTICON:I = 0xfa

.field public static final PROTOCOL_MIMOJI_FULL_SCREEN:I = 0xf9

.field public static final PROTOCOL_MIMOJI_GIF_EDITOR:I = 0xfb

.field public static final PROTOCOL_MIMOJI_GIF_RECORD:I = 0xed

.field public static final PROTOCOL_MIMOJI_VIDEO_EDITOR:I = 0xfc

.field public static final PROTOCOL_MI_ASD_DETECT:I = 0xeb

.field public static final PROTOCOL_MI_LIVE_CONFIG:I = 0xf1

.field public static final PROTOCOL_MI_LIVE_PLAYER:I = 0xf2

.field public static final PROTOCOL_MODE_CHANGE_CONTROLLER:I = 0xb3

.field public static final PROTOCOL_MODE_LIST_MANAGER:I = 0xa00

.field public static final PROTOCOL_MODULE_CONTENT:I = 0x1af

.field public static final PROTOCOL_MORE_MODE_POPUP:I = 0xa01

.field public static final PROTOCOL_ON_FACE_BEAUTY_CHANGED:I = 0xc7

.field public static final PROTOCOL_ON_SHINE_CHANGED:I = 0xea

.field public static final PROTOCOL_OPTICAL_ZOOM_RATIO:I = 0xcb

.field public static final PROTOCOL_PANORAMA:I = 0xb0

.field public static final PROTOCOL_PLAY_VIDEO:I = 0xa7

.field public static final PROTOCOL_PORTRAIT_LIGHTING:I = 0x2a0

.field public static final PROTOCOL_PREVIEW_CHANGED:I = 0xa8

.field public static final PROTOCOL_RECORDING_STATE:I = 0xd4

.field public static final PROTOCOL_SCREEN_LIGHT:I = 0xc4

.field public static final PROTOCOL_SNAP_INDICATOR:I = 0xb8

.field public static final PROTOCOL_SPEECH_SHUTTER_DETECT:I = 0xff

.field public static final PROTOCOL_STANDALONE_MACRO:I = 0xca

.field public static final PROTOCOL_STICKER_CONTROL:I = 0xb2

.field public static final PROTOCOL_SUBTITLE:I = 0xe7

.field public static final PROTOCOL_TOP_ALERT:I = 0xac

.field public static final PROTOCOL_TOP_CONFIG:I = 0xc1

.field public static final PROTOCOL_ULTRA_WIDE:I = 0xc8

.field public static final PROTOCOL_VERTICAL:I = 0xc6

.field public static final PROTOCOL_WIDE_SELFIE:I = 0xd8

.field public static final PROTOCOL_ZOOM_CHANGED:I = 0xaa

.field public static final SHOW_FAIL:I = 0x3

.field public static final SHOW_START:I = 0x1

.field public static final SHOW_SUCCESS:I = 0x2

.field public static final TOP_ALERT_EVENT:I = 0x2a1
