.class public Lcom/android/camera/dualvideo/render/Compose;
.super Ljava/lang/Object;
.source "Compose.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/dualvideo/render/Compose$RenderComposeType;
    }
.end annotation


# static fields
.field public static final DOWN:I = 0xc

.field public static final FULL:I = 0xd

.field public static final MINI:I = 0xa

.field public static PRIORITYS:[[I = null

.field public static final SIX_PATCH_0:I = 0x14

.field public static final SIX_PATCH_1:I = 0x15

.field public static final SIX_PATCH_2:I = 0x16

.field public static final SIX_PATCH_3:I = 0x17

.field public static final SIX_PATCH_4:I = 0x18

.field public static final SIX_PATCH_5:I = 0x19

.field public static final UP:I = 0xb


# direct methods
.method static constructor <clinit>()V
    .locals 4

    const/16 v0, 0xa

    new-array v1, v0, [[I

    new-array v2, v0, [I

    fill-array-data v2, :array_0

    const/4 v3, 0x0

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_1

    const/4 v3, 0x1

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_2

    const/4 v3, 0x2

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_3

    const/4 v3, 0x3

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_4

    const/4 v3, 0x4

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_5

    const/4 v3, 0x5

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_6

    const/4 v3, 0x6

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_7

    const/4 v3, 0x7

    aput-object v2, v1, v3

    new-array v2, v0, [I

    fill-array-data v2, :array_8

    const/16 v3, 0x8

    aput-object v2, v1, v3

    new-array v0, v0, [I

    fill-array-data v0, :array_9

    const/16 v2, 0x9

    aput-object v0, v1, v2

    sput-object v1, Lcom/android/camera/dualvideo/render/Compose;->PRIORITYS:[[I

    return-void

    nop

    :array_0
    .array-data 4
        0x64
        0x64
        0x64
        0x64
        0x64
        0x64
        0x63
        0x5f
        0x5f
        0x5b
    .end array-data

    :array_1
    .array-data 4
        0x64
        0x64
        0x64
        0x64
        0x64
        0x64
        0x63
        0x5f
        0x5f
        0x5b
    .end array-data

    :array_2
    .array-data 4
        0x64
        0x64
        0x64
        0x64
        0x64
        0x64
        0x63
        0x5f
        0x5f
        0x5b
    .end array-data

    :array_3
    .array-data 4
        0x64
        0x64
        0x64
        0x64
        0x64
        0x64
        0x63
        0x5f
        0x5f
        0x5b
    .end array-data

    :array_4
    .array-data 4
        0x64
        0x64
        0x64
        0x64
        0x64
        0x64
        0x63
        0x5f
        0x5f
        0x5b
    .end array-data

    :array_5
    .array-data 4
        0x64
        0x64
        0x64
        0x64
        0x64
        0x64
        0x63
        0x5f
        0x5f
        0x5b
    .end array-data

    :array_6
    .array-data 4
        0x59
        0x59
        0x59
        0x59
        0x59
        0x59
        0x4f
        0x4b
        0x4b
        0x48
    .end array-data

    :array_7
    .array-data 4
        0x55
        0x55
        0x55
        0x55
        0x55
        0x55
        0x45
        0x41
        0x41
        0x3d
    .end array-data

    :array_8
    .array-data 4
        0x55
        0x55
        0x55
        0x55
        0x55
        0x55
        0x45
        0x41
        0x41
        0x3d
    .end array-data

    :array_9
    .array-data 4
        0x51
        0x51
        0x51
        0x51
        0x51
        0x51
        0x3b
        0x37
        0x37
        0x33
    .end array-data
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getIndex(I)I
    .locals 0

    packed-switch p0, :pswitch_data_0

    packed-switch p0, :pswitch_data_1

    const/4 p0, -0x1

    return p0

    :pswitch_0
    const/4 p0, 0x5

    return p0

    :pswitch_1
    const/4 p0, 0x4

    return p0

    :pswitch_2
    const/4 p0, 0x3

    return p0

    :pswitch_3
    const/4 p0, 0x2

    return p0

    :pswitch_4
    const/4 p0, 0x1

    return p0

    :pswitch_5
    const/4 p0, 0x0

    return p0

    :pswitch_6
    const/4 p0, 0x6

    return p0

    :pswitch_7
    const/16 p0, 0x8

    return p0

    :pswitch_8
    const/4 p0, 0x7

    return p0

    :pswitch_9
    const/16 p0, 0x9

    return p0

    :pswitch_data_0
    .packed-switch 0xa
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x14
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
