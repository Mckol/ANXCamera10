.class public interface abstract Lcom/airbnb/lottie/W;
.super Ljava/lang/Object;
.source "LottieProperty.java"


# static fields
.field public static final COLOR:Ljava/lang/Integer;

.field public static final POSITION:Landroid/graphics/PointF;

.field public static final STROKE_COLOR:Ljava/lang/Integer;

.field public static final STROKE_WIDTH:Ljava/lang/Float;

.field public static final Sj:Ljava/lang/Integer;

.field public static final TEXT_SIZE:Ljava/lang/Float;

.field public static final Tj:Ljava/lang/Integer;

.field public static final Uj:Landroid/graphics/PointF;

.field public static final Vj:Landroid/graphics/PointF;

.field public static final Wj:Landroid/graphics/PointF;

.field public static final Xj:Landroid/graphics/PointF;

.field public static final Yj:Ljava/lang/Float;

.field public static final Zj:Lcom/airbnb/lottie/e/k;

.field public static final _j:Ljava/lang/Float;

.field public static final bk:Ljava/lang/Float;

.field public static final ck:Ljava/lang/Float;

.field public static final dk:Ljava/lang/Float;

.field public static final ek:Ljava/lang/Float;

.field public static final fk:Ljava/lang/Float;

.field public static final gk:Ljava/lang/Float;

.field public static final hk:Ljava/lang/Float;

.field public static final ik:Ljava/lang/Float;

.field public static final jk:Ljava/lang/Float;

.field public static final kk:Ljava/lang/Float;

.field public static final lk:Ljava/lang/Float;

.field public static final mk:Ljava/lang/Float;

.field public static final nk:Ljava/lang/Float;

.field public static final pk:Ljava/lang/Float;

.field public static final qk:Landroid/graphics/ColorFilter;

.field public static final rk:[Ljava/lang/Integer;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const/4 v0, 0x1

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->COLOR:Ljava/lang/Integer;

    const/4 v0, 0x2

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->STROKE_COLOR:Ljava/lang/Integer;

    const/4 v0, 0x3

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->Sj:Ljava/lang/Integer;

    const/4 v0, 0x4

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->Tj:Ljava/lang/Integer;

    new-instance v0, Landroid/graphics/PointF;

    invoke-direct {v0}, Landroid/graphics/PointF;-><init>()V

    sput-object v0, Lcom/airbnb/lottie/W;->Uj:Landroid/graphics/PointF;

    new-instance v0, Landroid/graphics/PointF;

    invoke-direct {v0}, Landroid/graphics/PointF;-><init>()V

    sput-object v0, Lcom/airbnb/lottie/W;->Vj:Landroid/graphics/PointF;

    new-instance v0, Landroid/graphics/PointF;

    invoke-direct {v0}, Landroid/graphics/PointF;-><init>()V

    sput-object v0, Lcom/airbnb/lottie/W;->Wj:Landroid/graphics/PointF;

    new-instance v0, Landroid/graphics/PointF;

    invoke-direct {v0}, Landroid/graphics/PointF;-><init>()V

    sput-object v0, Lcom/airbnb/lottie/W;->Xj:Landroid/graphics/PointF;

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->Yj:Ljava/lang/Float;

    new-instance v1, Landroid/graphics/PointF;

    invoke-direct {v1}, Landroid/graphics/PointF;-><init>()V

    sput-object v1, Lcom/airbnb/lottie/W;->POSITION:Landroid/graphics/PointF;

    new-instance v1, Lcom/airbnb/lottie/e/k;

    invoke-direct {v1}, Lcom/airbnb/lottie/e/k;-><init>()V

    sput-object v1, Lcom/airbnb/lottie/W;->Zj:Lcom/airbnb/lottie/e/k;

    const/high16 v1, 0x3f800000    # 1.0f

    invoke-static {v1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v1

    sput-object v1, Lcom/airbnb/lottie/W;->_j:Ljava/lang/Float;

    sput-object v0, Lcom/airbnb/lottie/W;->bk:Ljava/lang/Float;

    sput-object v0, Lcom/airbnb/lottie/W;->ck:Ljava/lang/Float;

    const/high16 v0, 0x40000000    # 2.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->STROKE_WIDTH:Ljava/lang/Float;

    const/high16 v0, 0x40400000    # 3.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->dk:Ljava/lang/Float;

    const/high16 v0, 0x40800000    # 4.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->ek:Ljava/lang/Float;

    const/high16 v0, 0x40a00000    # 5.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->fk:Ljava/lang/Float;

    const/high16 v0, 0x40c00000    # 6.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->gk:Ljava/lang/Float;

    const/high16 v0, 0x40e00000    # 7.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->hk:Ljava/lang/Float;

    const/high16 v0, 0x41000000    # 8.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->ik:Ljava/lang/Float;

    const/high16 v0, 0x41100000    # 9.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->jk:Ljava/lang/Float;

    const/high16 v0, 0x41200000    # 10.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->kk:Ljava/lang/Float;

    const/high16 v0, 0x41300000    # 11.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->lk:Ljava/lang/Float;

    const/high16 v0, 0x41400000    # 12.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->mk:Ljava/lang/Float;

    sput-object v0, Lcom/airbnb/lottie/W;->nk:Ljava/lang/Float;

    const/high16 v0, 0x41500000    # 13.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->pk:Ljava/lang/Float;

    const/high16 v0, 0x41600000    # 14.0f

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    sput-object v0, Lcom/airbnb/lottie/W;->TEXT_SIZE:Ljava/lang/Float;

    new-instance v0, Landroid/graphics/ColorFilter;

    invoke-direct {v0}, Landroid/graphics/ColorFilter;-><init>()V

    sput-object v0, Lcom/airbnb/lottie/W;->qk:Landroid/graphics/ColorFilter;

    const/4 v0, 0x0

    new-array v0, v0, [Ljava/lang/Integer;

    sput-object v0, Lcom/airbnb/lottie/W;->rk:[Ljava/lang/Integer;

    return-void
.end method
