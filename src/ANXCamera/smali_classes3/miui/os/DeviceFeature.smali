.class public Lmiui/os/DeviceFeature;
.super Ljava/lang/Object;
.source "DeviceFeature.java"


# static fields
.field public static final BACKLIGHT_BIT:I

.field public static final PERSIST_SCREEN_EFFECT:Z

.field public static final SCREEN_EFFECT_CONFLICT:Z

.field public static final SUPPORT_3D_GESTURE:Z

.field public static final SUPPORT_AUTO_BRIGHTNESS_OPTIMIZE:Z

.field public static final SUPPORT_CAMERA_ANIMATION:Z

.field public static final SUPPORT_DISPLAYFEATURE_CALLBACK:Z

.field public static final SUPPORT_DISPLAYFEATURE_HIDL:Z

.field public static final SUPPORT_GAME_MODE:Z

.field public static final SUPPORT_LAB_GESTURE:Z

.field public static final SUPPORT_NIGHT_LIGHT:Z

.field public static final SUPPORT_NIGHT_LIGHT_ADJ:Z

.field public static final SUPPORT_PAPERMODE_ANIMATION:Z

.field public static final SYSTEM_RESOURCES:Landroid/content/res/Resources;


# direct methods
.method static constructor <clinit>()V
    .registers 4

    .line 13
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    sput-object v0, Lmiui/os/DeviceFeature;->SYSTEM_RESOURCES:Landroid/content/res/Resources;

    .line 18
    const-string v0, "support_autobrightness_optimize"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lmiui/util/FeatureParser;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    const/4 v2, 0x1

    if-eqz v0, :cond_16

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x17

    if-gt v0, v3, :cond_1e

    :cond_16
    const-string v0, "sys.autobrightness_optimize"

    .line 20
    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_20

    :cond_1e
    move v0, v2

    goto :goto_21

    :cond_20
    move v0, v1

    :goto_21
    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_AUTO_BRIGHTNESS_OPTIMIZE:Z

    .line 25
    const-string v0, "sys.displayfeature_hidl"

    .line 26
    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_DISPLAYFEATURE_HIDL:Z

    .line 28
    # sget-object v0, Lmiui/os/DeviceFeature;->SYSTEM_RESOURCES:Landroid/content/res/Resources;

    # const v3, 0x110c002d

    # .line 29
    # invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getBoolean(I)Z
    const-string/jumbo v0, "SUPPORT_DISPLAYFEATURE_CALLBACK"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lmiui/util/FeatureParser;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_DISPLAYFEATURE_CALLBACK:Z

    .line 35
    # sget-object v0, Lmiui/os/DeviceFeature;->SYSTEM_RESOURCES:Landroid/content/res/Resources;

    # const v3, 0x110c0021

    # invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getBoolean(I)Z
    const-string/jumbo v0, "SUPPORT_NIGHT_LIGHT"
    
    const/4 v1, 0x0

    invoke-static {v0, v1}, Lmiui/util/FeatureParser;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_NIGHT_LIGHT:Z

    .line 40
    const-string v0, "support_papermode_animation"

    invoke-static {v0, v1}, Lmiui/util/FeatureParser;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_PAPERMODE_ANIMATION:Z

    .line 46
    # sget-object v0, Lmiui/os/DeviceFeature;->SYSTEM_RESOURCES:Landroid/content/res/Resources;

    # const v3, 0x110c0022

    # invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getBoolean(I)Z
    const-string/jumbo v0, "SUPPORT_NIGHT_LIGHT_ADJ"
    
    const/4 v1, 0x0

    invoke-static {v0, v1}, Lmiui/util/FeatureParser;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_NIGHT_LIGHT_ADJ:Z

    .line 50
    # sget-object v0, Lmiui/os/DeviceFeature;->SYSTEM_RESOURCES:Landroid/content/res/Resources;

    # const v3, 0x110a001a

    # invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getInteger(I)I
    const-string/jumbo v0, "BACKLIGHT_BIT"

    const v1, 0x0

    invoke-static {v0, v1}, Lmiui/util/FeatureParser;->getInteger(Ljava/lang/String;I)I

    move-result v0

    sput v0, Lmiui/os/DeviceFeature;->BACKLIGHT_BIT:I

    .line 56
    const-string v0, "sys.persist_screen_effect"

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lmiui/os/DeviceFeature;->PERSIST_SCREEN_EFFECT:Z

    .line 61
    const-string v0, "ro.df.effect.conflict"

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-eq v0, v2, :cond_7a

    const-string v0, "ro.vendor.df.effect.conflict"

    .line 63
    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-ne v0, v2, :cond_78

    goto :goto_7a

    :cond_78
    move v0, v1

    goto :goto_7b

    :cond_7a
    :goto_7a
    move v0, v2

    :goto_7b
    sput-boolean v0, Lmiui/os/DeviceFeature;->SCREEN_EFFECT_CONFLICT:Z

    .line 65
    const-string v0, "support_touchfeature_gamemode"

    invoke-static {v0, v1}, Lmiui/util/FeatureParser;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_GAME_MODE:Z

    .line 81
    const-string v0, "sagit"

    sget-object v3, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_95

    sget-boolean v0, Lmiui/os/Build;->IS_STABLE_VERSION:Z

    if-nez v0, :cond_95

    move v0, v2

    goto :goto_96

    :cond_95
    move v0, v1

    :goto_96
    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_LAB_GESTURE:Z

    .line 84
    const-string v0, "pyxis"

    sget-object v3, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_af

    const-string v0, "vela"

    sget-object v3, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_ad

    goto :goto_af

    :cond_ad
    move v0, v1

    goto :goto_b0

    :cond_af
    :goto_af
    move v0, v2

    :goto_b0
    sput-boolean v0, Lmiui/os/DeviceFeature;->SUPPORT_CAMERA_ANIMATION:Z

    .line 86
    const-string v0, "cepheus"

    sget-object v3, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_c2

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-nez v0, :cond_c2

    move v1, v2

    nop

    :cond_c2
    sput-boolean v1, Lmiui/os/DeviceFeature;->SUPPORT_3D_GESTURE:Z

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .line 12
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static final hasFlipSupport()Z
    .registers 2

    .line 78
    const-string v0, "draco"

    sget-object v1, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static final hasMirihiSupport()Z
    .registers 2

    .line 69
    const-string v0, "perseus"

    sget-object v1, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_17

    const-string v0, "andromeda"

    sget-object v1, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_15

    goto :goto_17

    :cond_15
    const/4 v0, 0x0

    goto :goto_18

    :cond_17
    :goto_17
    const/4 v0, 0x1

    :goto_18
    return v0
.end method

.method public static final hasPopupCameraSupport()Z
    .registers 2

    .line 73
    const-string v0, "raphael"

    sget-object v1, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2b

    const-string v0, "davinci"

    sget-object v1, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2b

    const-string v0, "raphaelin"

    sget-object v1, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    .line 74
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2b

    const-string v0, "davinciin"

    sget-object v1, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_29

    goto :goto_2b

    :cond_29
    const/4 v0, 0x0

    goto :goto_2c

    :cond_2b
    :goto_2b
    const/4 v0, 0x1

    .line 73
    :goto_2c
    return v0
.end method

.method public static final hasSupportAudioPromity()Z
    .registers 2

    .line 90
    const-string v0, "ro.vendor.audio.us.proximity"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-nez v0, :cond_13

    const-string v0, "ro.audio.us.proximity"

    .line 91
    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_12

    goto :goto_13

    :cond_12
    goto :goto_14

    :cond_13
    :goto_13
    const/4 v1, 0x1

    .line 90
    :goto_14
    return v1
.end method
