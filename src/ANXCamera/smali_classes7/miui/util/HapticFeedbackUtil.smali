.class public Lmiui/util/HapticFeedbackUtil;
.super Ljava/lang/Object;
.source "HapticFeedbackUtil.java"


# static fields
.field public static final EFFECT_KEY_FLICK:Ljava/lang/String; = "flick"

.field public static final EFFECT_KEY_LONG_PRESS:Ljava/lang/String; = "long_press"

.field public static final EFFECT_KEY_MESH_HEAVY:Ljava/lang/String; = "mesh_heavy"

.field public static final EFFECT_KEY_MESH_LIGHT:Ljava/lang/String; = "mesh_light"

.field public static final EFFECT_KEY_MESH_NORMAL:Ljava/lang/String; = "mesh_normal"

.field public static final EFFECT_KEY_POPUP_LIGHT:Ljava/lang/String; = "popup_light"

.field public static final EFFECT_KEY_POPUP_NORMAL:Ljava/lang/String; = "popup_normal"

.field public static final EFFECT_KEY_SWITCH:Ljava/lang/String; = "switch"

.field public static final EFFECT_KEY_TAP_LIGHT:Ljava/lang/String; = "tap_light"

.field public static final EFFECT_KEY_TAP_NORMAL:Ljava/lang/String; = "tap_normal"

.field public static final EFFECT_KEY_VIRTUAL_KEY_DOWN:Ljava/lang/String; = "virtual_key_down"

.field public static final EFFECT_KEY_VIRTUAL_KEY_LONGPRESS:Ljava/lang/String; = "virtual_key_longpress"

.field public static final EFFECT_KEY_VIRTUAL_KEY_TAP:Ljava/lang/String; = "virtual_key_tap"

.field public static final EFFECT_KEY_VIRTUAL_KEY_UP:Ljava/lang/String; = "virtual_key_up"

.field private static final ID_TO_KEY:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final PROPERTY_KEY:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final PROPERTY_MOTOR_KEY:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final VIRTUAL_RELEASED:I = 0x2


# direct methods
.method static constructor <clinit>()V
    .locals 16

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    sput-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v1, "virtual_key_down"

    const/4 v2, 0x1

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v2, "virtual_key_longpress"

    const/4 v3, 0x0

    invoke-virtual {v0, v3, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v3, "virtual_key_tap"

    const/4 v4, 0x3

    invoke-virtual {v0, v4, v3}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v4, "virtual_key_up"

    const/4 v5, 0x2

    invoke-virtual {v0, v5, v4}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v5, "tap_normal"

    const/high16 v6, 0x10000000

    invoke-virtual {v0, v6, v5}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v6, "tap_light"

    const v7, 0x10000001

    invoke-virtual {v0, v7, v6}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v7, "flick"

    const v8, 0x10000002

    invoke-virtual {v0, v8, v7}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v8, "switch"

    const v9, 0x10000003

    invoke-virtual {v0, v9, v8}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v9, "mesh_heavy"

    const v10, 0x10000004

    invoke-virtual {v0, v10, v9}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v10, "mesh_normal"

    const v11, 0x10000005

    invoke-virtual {v0, v11, v10}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v11, "mesh_light"

    const v12, 0x10000006

    invoke-virtual {v0, v12, v11}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v12, "long_press"

    const v13, 0x10000007

    invoke-virtual {v0, v13, v12}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v13, "popup_normal"

    const v14, 0x10000008

    invoke-virtual {v0, v14, v13}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    const-string v14, "popup_light"

    const v15, 0x10000009

    invoke-virtual {v0, v15, v14}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v15, "sys.haptic.down"

    invoke-virtual {v0, v1, v15}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.long"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.tap"

    invoke-virtual {v0, v3, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.up"

    invoke-virtual {v0, v4, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.tap.normal"

    invoke-virtual {v0, v5, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.tap.light"

    invoke-virtual {v0, v6, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.flick"

    invoke-virtual {v0, v7, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.switch"

    invoke-virtual {v0, v8, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.mesh.heavy"

    invoke-virtual {v0, v9, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.mesh.normal"

    invoke-virtual {v0, v10, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.mesh.light"

    invoke-virtual {v0, v11, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.long.press"

    invoke-virtual {v0, v12, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.popup.normal"

    invoke-virtual {v0, v13, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    const-string v1, "sys.haptic.popup.light"

    invoke-virtual {v0, v14, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v6}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v8}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v9}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v10}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v11}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v12}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v13}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, v14}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static isSupportLinearMotorVibrate()Z
    .locals 2

    const-string v0, "sys.haptic.motor"

    invoke-static {v0}, Lmiui/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "linear"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static isSupportLinearMotorVibrate(I)Z
    .locals 1

    invoke-static {}, Lmiui/util/HapticFeedbackUtil;->isSupportLinearMotorVibrate()Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->ID_TO_KEY:Landroid/util/SparseArray;

    invoke-virtual {v0, p0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/String;

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_MOTOR_KEY:Ljava/util/List;

    invoke-interface {v0, p0}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Lmiui/util/HapticFeedbackUtil;->PROPERTY_KEY:Ljava/util/HashMap;

    invoke-virtual {v0, p0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/String;

    invoke-static {p0}, Lmiui/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method
