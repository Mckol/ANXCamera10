.class public Lcom/android/camera/HybridZoomingSystem;
.super Ljava/lang/Object;
.source "HybridZoomingSystem.java"


# static fields
.field private static final DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION:Ljava/lang/String;

.field public static final FLOAT_MICRO_SCENE_ZOOM_MAX:F = 1.0f

.field public static final FLOAT_MOON_MODE_ZOOM_MAX:F = 20.0f

.field public static final FLOAT_MOON_MODE_ZOOM_MIN:F = 1.0f

.field public static final FLOAT_STEP_FOR_ZOOM_RATIO_CHANGE:F = 0.1f

.field public static final FLOAT_SUPER_MOON_ZOOM_MAX:F = 60.0f

.field public static final FLOAT_SUPER_MOON_ZOOM_MIN:F = 5.0f

.field public static final FLOAT_ULTRA_WIDE_ZOOM_MAX:F = 6.0f

.field public static final FLOAT_ZOOM_RATIO_10X:F = 10.0f

.field public static final FLOAT_ZOOM_RATIO_2X:F = 2.0f

.field public static final FLOAT_ZOOM_RATIO_30X:F = 30.0f

.field public static final FLOAT_ZOOM_RATIO_3X:F = 3.0f

.field public static final FLOAT_ZOOM_RATIO_5X:F = 5.0f

.field public static final FLOAT_ZOOM_RATIO_MACRO:F = 0.0f

.field public static final FLOAT_ZOOM_RATIO_NONE:F = 1.0f

.field public static FLOAT_ZOOM_RATIO_ULTR:F = 0.0f

.field public static final FLOAT_ZOOM_RATIO_ULTRA_TELE_THRESHHOLD:F = 3.7f

.field public static final FLOAT_ZOOM_RATIO_WIDE:F = 1.0f

.field public static final IS_2_OR_MORE_SAT:Z

.field public static final IS_2_SAT:Z

.field public static final IS_3_OR_MORE_SAT:Z

.field public static final IS_3_SAT:Z

.field public static final IS_4_OR_MORE_SAT:Z

.field public static final IS_4_SAT:Z

.field private static final MACRO_OPTICAL_ZOOM_RATIO_COMBINATION:Ljava/lang/String;

.field private static final OPTICAL_ZOOM_RATIO_COMBINATION:I

.field public static final STRING_SUPER_MOON_ZOOM_RATIO_NONE:Ljava/lang/String; = "5.0"

.field public static final STRING_ZOOM_RATIO_NONE:Ljava/lang/String; = "1.0"

.field private static final TAG:Ljava/lang/String; = "HybridZoomingSystem"

.field public static final TOLERANCE_FOR_ZOOM_RATIO_CHANGED:F = 0.01f

.field public static final ZOOM_INDEXS_DEFAULT:Ljava/lang/String; = "0.6:1:2:5:10"

.field public static final ZOOM_INDEXS_SUPER_MOON:Ljava/lang/String; = "5:20:30:40:50:60"

.field public static final ZOOM_RULER_DEFAULT:Ljava/lang/String; = "4:10:10:5"

.field public static final ZOOM_RULER_SUPER_MOON:Ljava/lang/String; = "3:10:10:10:10"

.field public static sDefaultMacroOpticalZoomRatio:F

.field private static sDefaultOpticalZoomRatioIndex:I

.field private static sMacroSupportedOpticalZoomRatios:[F

.field private static sMacroZoomRatioIndex:I

.field private static sNightSupportedOpticalZoomRatios:[F

.field private static sNightSupportedZoomRatioIndex:I

.field private static sPixelSupportedOpticalZoomRatios:[F

.field private static sPixelSupportedZoomRatioIndex:I

.field private static sSuperMoonSupportedOpticalZoomRatios:[F

.field private static sSuperMoonSupportedZoomRatioIndex:I

.field private static sSupportedOpticalZoomRatios:[F

.field private static final sZoomRatioHistory:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method private static synthetic $closeResource(Ljava/lang/Throwable;Ljava/lang/AutoCloseable;)V
    .locals 0

    if-eqz p0, :cond_0

    :try_start_0
    invoke-interface {p1}, Ljava/lang/AutoCloseable;->close()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception p1

    invoke-virtual {p0, p1}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_0

    :cond_0
    invoke-interface {p1}, Ljava/lang/AutoCloseable;->close()V

    :goto_0
    return-void
.end method

.method static constructor <clinit>()V
    .locals 13

    const-string v0, "\\s*[:,]\\s*"

    new-instance v1, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v1}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v1, Lcom/android/camera/HybridZoomingSystem;->sZoomRatioHistory:Ljava/util/Map;

    invoke-static {}, Lb/c/a/c;->isSupportedOpticalZoom()Z

    move-result v1

    const-string v2, "1.0"

    if-eqz v1, :cond_0

    const-string v1, "1.0:2.0"

    goto :goto_0

    :cond_0
    move-object v1, v2

    :goto_0
    sput-object v1, Lcom/android/camera/HybridZoomingSystem;->DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION:Ljava/lang/String;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v1

    invoke-static {}, Lb/c/a/c;->isSupportedOpticalZoom()Z

    move-result v3

    const/4 v4, 0x2

    const/4 v5, 0x1

    if-eqz v3, :cond_1

    move v3, v4

    goto :goto_1

    :cond_1
    move v3, v5

    :goto_1
    invoke-virtual {v1, v3}, Lb/c/a/b;->y(I)I

    move-result v1

    sput v1, Lcom/android/camera/HybridZoomingSystem;->OPTICAL_ZOOM_RATIO_COMBINATION:I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v1

    sget-object v3, Lcom/android/camera/HybridZoomingSystem;->DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION:Ljava/lang/String;

    invoke-virtual {v1, v3}, Lb/c/a/b;->X(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/android/camera/HybridZoomingSystem;->MACRO_OPTICAL_ZOOM_RATIO_COMBINATION:Ljava/lang/String;

    const/4 v1, -0x1

    sput v1, Lcom/android/camera/HybridZoomingSystem;->sMacroZoomRatioIndex:I

    new-array v3, v4, [F

    fill-array-data v3, :array_0

    sput-object v3, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedOpticalZoomRatios:[F

    const/4 v3, 0x0

    sput v3, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedZoomRatioIndex:I

    const/4 v6, 0x3

    new-array v7, v6, [F

    fill-array-data v7, :array_1

    sput-object v7, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    sput v5, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedZoomRatioIndex:I

    new-array v7, v4, [F

    fill-array-data v7, :array_2

    sput-object v7, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedOpticalZoomRatios:[F

    sput v3, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedZoomRatioIndex:I

    sput v1, Lcom/android/camera/HybridZoomingSystem;->sDefaultOpticalZoomRatioIndex:I

    const/high16 v7, 0x3f800000    # 1.0f

    sput v7, Lcom/android/camera/HybridZoomingSystem;->sDefaultMacroOpticalZoomRatio:F

    const v8, 0x3f19999a    # 0.6f

    sput v8, Lcom/android/camera/HybridZoomingSystem;->FLOAT_ZOOM_RATIO_ULTR:F

    new-instance v8, Ljava/util/ArrayList;

    invoke-direct {v8}, Ljava/util/ArrayList;-><init>()V

    new-instance v9, Ljava/util/Scanner;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v10

    sget-object v11, Lcom/android/camera/HybridZoomingSystem;->DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION:Ljava/lang/String;

    invoke-virtual {v10, v11}, Lb/c/a/b;->Y(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    invoke-direct {v9, v10}, Ljava/util/Scanner;-><init>(Ljava/lang/String;)V

    :try_start_0
    invoke-virtual {v9, v0}, Ljava/util/Scanner;->useDelimiter(Ljava/lang/String;)Ljava/util/Scanner;

    move v10, v1

    :cond_2
    :goto_2
    invoke-virtual {v9}, Ljava/util/Scanner;->hasNext()Z

    move-result v11

    if-eqz v11, :cond_3

    invoke-virtual {v9}, Ljava/util/Scanner;->next()Ljava/lang/String;

    move-result-object v11

    if-eqz v11, :cond_2

    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v12

    if-lez v12, :cond_2

    invoke-interface {v8, v11}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    add-int/lit8 v10, v10, 0x1

    invoke-virtual {v11, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-eqz v11, :cond_2

    sget v11, Lcom/android/camera/HybridZoomingSystem;->sDefaultOpticalZoomRatioIndex:I

    if-ne v11, v1, :cond_2

    sput v10, Lcom/android/camera/HybridZoomingSystem;->sDefaultOpticalZoomRatioIndex:I
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_2

    goto :goto_2

    :cond_3
    const/4 v2, 0x0

    invoke-static {v2, v9}, Lcom/android/camera/HybridZoomingSystem;->$closeResource(Ljava/lang/Throwable;Ljava/lang/AutoCloseable;)V

    sget v9, Lcom/android/camera/HybridZoomingSystem;->sDefaultOpticalZoomRatioIndex:I

    const-string v10, "The supported optical zoom ratios are probably not configured correctly"

    if-ltz v9, :cond_14

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v9

    if-lt v9, v5, :cond_14

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v9

    invoke-static {v9, v6}, Ljava/lang/Math;->min(II)I

    move-result v9

    new-array v9, v9, [F

    sput-object v9, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    move v9, v3

    :goto_3
    sget-object v11, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    array-length v12, v11

    if-ge v9, v12, :cond_5

    invoke-interface {v8, v9}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/String;

    invoke-static {v12}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result v12

    aput v12, v11, v9

    sget-object v11, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    aget v12, v11, v9

    cmpg-float v12, v12, v7

    if-gez v12, :cond_4

    aget v11, v11, v9

    sput v11, Lcom/android/camera/HybridZoomingSystem;->FLOAT_ZOOM_RATIO_ULTR:F

    :cond_4
    add-int/lit8 v9, v9, 0x1

    goto :goto_3

    :cond_5
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v9, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    invoke-static {v9}, Ljava/util/Arrays;->toString([F)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v9, "["

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v9, Lcom/android/camera/HybridZoomingSystem;->sDefaultOpticalZoomRatioIndex:I

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v9, "]"

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    const-string v9, "HybridZoomingSystem"

    invoke-static {v9, v7}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-boolean v7, Lb/c/a/c;->dh:Z

    if-eqz v7, :cond_6

    new-array v7, v4, [F

    fill-array-data v7, :array_3

    sput-object v7, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedOpticalZoomRatios:[F

    :cond_6
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v7

    invoke-virtual {v7}, Lb/c/a/b;->isSupportMacroMode()Z

    move-result v7

    if-eqz v7, :cond_b

    invoke-interface {v8}, Ljava/util/List;->clear()V

    new-instance v7, Ljava/util/Scanner;

    sget-object v11, Lcom/android/camera/HybridZoomingSystem;->MACRO_OPTICAL_ZOOM_RATIO_COMBINATION:Ljava/lang/String;

    invoke-direct {v7, v11}, Ljava/util/Scanner;-><init>(Ljava/lang/String;)V

    :try_start_1
    invoke-virtual {v7, v0}, Ljava/util/Scanner;->useDelimiter(Ljava/lang/String;)Ljava/util/Scanner;

    :cond_7
    :goto_4
    invoke-virtual {v7}, Ljava/util/Scanner;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_8

    invoke-virtual {v7}, Ljava/util/Scanner;->next()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_7

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v11

    if-lez v11, :cond_7

    invoke-interface {v8, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    add-int/lit8 v1, v1, 0x1

    if-nez v1, :cond_7

    invoke-static {v0}, Ljava/lang/Float;->valueOf(Ljava/lang/String;)Ljava/lang/Float;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v0

    sput v0, Lcom/android/camera/HybridZoomingSystem;->sDefaultMacroOpticalZoomRatio:F
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_4

    :cond_8
    invoke-static {v2, v7}, Lcom/android/camera/HybridZoomingSystem;->$closeResource(Ljava/lang/Throwable;Ljava/lang/AutoCloseable;)V

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v0

    if-lt v0, v5, :cond_a

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v0

    new-array v0, v0, [F

    sput-object v0, Lcom/android/camera/HybridZoomingSystem;->sMacroSupportedOpticalZoomRatios:[F

    invoke-interface {v8}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    move v1, v3

    :goto_5
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_9

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    sget-object v7, Lcom/android/camera/HybridZoomingSystem;->sMacroSupportedOpticalZoomRatios:[F

    add-int/lit8 v8, v1, 0x1

    invoke-static {v2}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result v2

    aput v2, v7, v1

    move v1, v8

    goto :goto_5

    :cond_9
    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sMacroSupportedOpticalZoomRatios:[F

    invoke-static {v0}, Ljava/util/Arrays;->toString([F)Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_6

    :cond_a
    new-instance v0, Ljava/lang/IllegalStateException;

    invoke-direct {v0, v10}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0

    :catchall_0
    move-exception v0

    :try_start_2
    throw v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    :catchall_1
    move-exception v1

    invoke-static {v0, v7}, Lcom/android/camera/HybridZoomingSystem;->$closeResource(Ljava/lang/Throwable;Ljava/lang/AutoCloseable;)V

    throw v1

    :cond_b
    :goto_6
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->Fk()Z

    move-result v0

    if-eqz v0, :cond_d

    sget-boolean v0, Lb/c/a/c;->hh:Z

    if-eqz v0, :cond_c

    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    sput-object v0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    :cond_c
    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    sget v1, Lcom/android/camera/HybridZoomingSystem;->FLOAT_ZOOM_RATIO_ULTR:F

    aput v1, v0, v3

    sput v5, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedZoomRatioIndex:I

    :cond_d
    sget v0, Lcom/android/camera/HybridZoomingSystem;->OPTICAL_ZOOM_RATIO_COMBINATION:I

    if-ne v0, v4, :cond_e

    move v0, v5

    goto :goto_7

    :cond_e
    move v0, v3

    :goto_7
    sput-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_2_SAT:Z

    sget v0, Lcom/android/camera/HybridZoomingSystem;->OPTICAL_ZOOM_RATIO_COMBINATION:I

    if-lt v0, v4, :cond_f

    move v0, v5

    goto :goto_8

    :cond_f
    move v0, v3

    :goto_8
    sput-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_2_OR_MORE_SAT:Z

    sget v0, Lcom/android/camera/HybridZoomingSystem;->OPTICAL_ZOOM_RATIO_COMBINATION:I

    if-ne v0, v6, :cond_10

    move v0, v5

    goto :goto_9

    :cond_10
    move v0, v3

    :goto_9
    sput-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_SAT:Z

    sget v0, Lcom/android/camera/HybridZoomingSystem;->OPTICAL_ZOOM_RATIO_COMBINATION:I

    if-lt v0, v6, :cond_11

    move v0, v5

    goto :goto_a

    :cond_11
    move v0, v3

    :goto_a
    sput-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    sget v0, Lcom/android/camera/HybridZoomingSystem;->OPTICAL_ZOOM_RATIO_COMBINATION:I

    const/4 v1, 0x4

    if-ne v0, v1, :cond_12

    move v0, v5

    goto :goto_b

    :cond_12
    move v0, v3

    :goto_b
    sput-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_4_SAT:Z

    sget v0, Lcom/android/camera/HybridZoomingSystem;->OPTICAL_ZOOM_RATIO_COMBINATION:I

    if-lt v0, v1, :cond_13

    move v3, v5

    :cond_13
    sput-boolean v3, Lcom/android/camera/HybridZoomingSystem;->IS_4_OR_MORE_SAT:Z

    return-void

    :cond_14
    new-instance v0, Ljava/lang/IllegalStateException;

    invoke-direct {v0, v10}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0

    :catchall_2
    move-exception v0

    :try_start_3
    throw v0
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    :catchall_3
    move-exception v1

    invoke-static {v0, v9}, Lcom/android/camera/HybridZoomingSystem;->$closeResource(Ljava/lang/Throwable;Ljava/lang/AutoCloseable;)V

    throw v1

    :array_0
    .array-data 4
        0x3f800000    # 1.0f
        0x40000000    # 2.0f
    .end array-data

    :array_1
    .array-data 4
        0x3f19999a    # 0.6f
        0x3f800000    # 1.0f
        0x40000000    # 2.0f
    .end array-data

    :array_2
    .array-data 4
        0x40a00000    # 5.0f
        0x41a00000    # 20.0f
    .end array-data

    :array_3
    .array-data 4
        0x3f800000    # 1.0f
        0x40a00000    # 5.0f
    .end array-data
.end method

.method private constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static add(FF)F
    .locals 1

    const/high16 v0, 0x41200000    # 10.0f

    mul-float/2addr p0, v0

    float-to-int p0, p0

    mul-float/2addr p1, v0

    float-to-int p1, p1

    add-int/2addr p0, p1

    int-to-float p0, p0

    div-float/2addr p0, v0

    return p0
.end method

.method public static clamp(FFF)F
    .locals 1

    cmpl-float v0, p0, p2

    if-lez v0, :cond_0

    return p2

    :cond_0
    cmpg-float p2, p0, p1

    if-gez p2, :cond_1

    return p1

    :cond_1
    return p0
.end method

.method public static clearZoomRatioHistory()V
    .locals 2

    const-string v0, "HybridZoomingSystem"

    const-string v1, "clearZoomRatioHistory()"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sZoomRatioHistory:Ljava/util/Map;

    invoke-interface {v0}, Ljava/util/Map;->clear()V

    return-void
.end method

.method public static getDefaultOpticalZoomRatioIndex(I)I
    .locals 1

    const/16 v0, 0xad

    if-eq p0, v0, :cond_2

    const/16 v0, 0xaf

    if-eq p0, v0, :cond_1

    const/16 v0, 0xbc

    if-eq p0, v0, :cond_0

    sget p0, Lcom/android/camera/HybridZoomingSystem;->sDefaultOpticalZoomRatioIndex:I

    return p0

    :cond_0
    sget p0, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedZoomRatioIndex:I

    return p0

    :cond_1
    sget p0, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedZoomRatioIndex:I

    return p0

    :cond_2
    sget p0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedZoomRatioIndex:I

    return p0
.end method

.method public static getMacroZoomRatioIndex(I)I
    .locals 1

    const/16 v0, 0xa1

    if-eq p0, v0, :cond_0

    const/16 v0, 0xb7

    if-eq p0, v0, :cond_0

    const/16 v0, 0xbc

    if-eq p0, v0, :cond_0

    packed-switch p0, :pswitch_data_0

    sget p0, Lcom/android/camera/HybridZoomingSystem;->sMacroZoomRatioIndex:I

    return p0

    :cond_0
    :pswitch_0
    const/4 p0, -0x1

    return p0

    :pswitch_data_0
    .packed-switch 0xad
        :pswitch_0
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public static getMaximumMacroOpticalZoomRatio()F
    .locals 2

    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sMacroSupportedOpticalZoomRatios:[F

    array-length v1, v0

    add-int/lit8 v1, v1, -0x1

    aget v0, v0, v1

    return v0
.end method

.method public static getMaximumOpticalZoomRatio(I)F
    .locals 1

    const/16 v0, 0xad

    if-eq p0, v0, :cond_2

    const/16 v0, 0xaf

    if-eq p0, v0, :cond_1

    const/16 v0, 0xbc

    if-eq p0, v0, :cond_0

    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_0
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_1
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_2
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    :goto_0
    array-length v0, p0

    add-int/lit8 v0, v0, -0x1

    aget p0, p0, v0

    return p0
.end method

.method public static getMinimumMacroOpticalZoomRatio()F
    .locals 2

    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sMacroSupportedOpticalZoomRatios:[F

    const/4 v1, 0x0

    aget v0, v0, v1

    return v0
.end method

.method public static getMinimumOpticalZoomRatio(I)F
    .locals 2

    const/16 v0, 0xad

    const/4 v1, 0x0

    if-eq p0, v0, :cond_3

    const/16 v0, 0xaf

    if-eq p0, v0, :cond_2

    const/16 v0, 0xbc

    if-eq p0, v0, :cond_1

    sget p0, Lcom/android/camera/HybridZoomingSystem;->sMacroZoomRatioIndex:I

    const/4 v0, -0x1

    if-eq p0, v0, :cond_0

    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    const/4 v0, 0x1

    aget p0, p0, v0

    return p0

    :cond_0
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    aget p0, p0, v1

    return p0

    :cond_1
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedOpticalZoomRatios:[F

    aget p0, p0, v1

    return p0

    :cond_2
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedOpticalZoomRatios:[F

    aget p0, p0, v1

    return p0

    :cond_3
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    aget p0, p0, v1

    return p0
.end method

.method public static getOpticalZoomRatioAt(II)F
    .locals 5

    const/16 v0, 0xad

    if-eq p0, v0, :cond_2

    const/16 v0, 0xaf

    if-eq p0, v0, :cond_1

    const/16 v0, 0xbc

    if-eq p0, v0, :cond_0

    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_0
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_1
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_2
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    :goto_0
    array-length v0, p0

    const-string v1, ")   curIndex error : "

    const-string v2, "The given index must be in range [0, "

    const-string v3, "HybridZoomingSystem"

    if-gez p1, :cond_3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v3, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x0

    goto :goto_1

    :cond_3
    if-lt p1, v0, :cond_4

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v3, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    add-int/lit8 p1, v0, -0x1

    :cond_4
    :goto_1
    aget p0, p0, p1

    return p0
.end method

.method public static getOpticalZoomRatioIndex(IF)I
    .locals 2

    const/16 v0, 0xad

    if-eq p0, v0, :cond_2

    const/16 v0, 0xaf

    if-eq p0, v0, :cond_1

    const/16 v0, 0xbc

    if-eq p0, v0, :cond_0

    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_0
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_1
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedOpticalZoomRatios:[F

    goto :goto_0

    :cond_2
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    :goto_0
    array-length v0, p0

    add-int/lit8 v0, v0, -0x1

    :goto_1
    if-ltz v0, :cond_4

    aget v1, p0, v0

    cmpl-float v1, p1, v1

    if-ltz v1, :cond_3

    return v0

    :cond_3
    add-int/lit8 v0, v0, -0x1

    goto :goto_1

    :cond_4
    new-instance p0, Ljava/lang/IllegalArgumentException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Illegal zoom ratio: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static getSupportedOpticalZoomRatios(I)[F
    .locals 1

    const/16 v0, 0xad

    if-eq p0, v0, :cond_2

    const/16 v0, 0xaf

    if-eq p0, v0, :cond_1

    const/16 v0, 0xbc

    if-eq p0, v0, :cond_0

    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSupportedOpticalZoomRatios:[F

    return-object p0

    :cond_0
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sSuperMoonSupportedOpticalZoomRatios:[F

    return-object p0

    :cond_1
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sPixelSupportedOpticalZoomRatios:[F

    return-object p0

    :cond_2
    sget-object p0, Lcom/android/camera/HybridZoomingSystem;->sNightSupportedOpticalZoomRatios:[F

    return-object p0
.end method

.method public static getTeleMinZoomRatio()F
    .locals 2

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxCameraId()I

    move-result v0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    const/high16 v1, 0x40000000    # 2.0f

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {v0, v1}, Lcom/android/camera2/CameraCapabilities;->getMinZoomRatio(F)F

    move-result v1

    :goto_0
    return v1
.end method

.method public static getTeleZoomRatio(I)F
    .locals 2

    invoke-static {p0}, Lcom/android/camera/HybridZoomingSystem;->getSupportedOpticalZoomRatios(I)[F

    move-result-object v0

    invoke-static {p0}, Lcom/android/camera/HybridZoomingSystem;->getDefaultOpticalZoomRatioIndex(I)I

    move-result p0

    add-int/lit8 p0, p0, 0x1

    array-length v1, v0

    if-ge p0, v1, :cond_0

    aget p0, v0, p0

    goto :goto_0

    :cond_0
    const/high16 p0, 0x40000000    # 2.0f

    :goto_0
    return p0
.end method

.method public static getUltraTeleMinZoomRatio()F
    .locals 2

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraTeleCameraId()I

    move-result v0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    const/high16 v1, 0x40a00000    # 5.0f

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {v0, v1}, Lcom/android/camera2/CameraCapabilities;->getMinZoomRatio(F)F

    move-result v1

    :goto_0
    return v1
.end method

.method public static getZoomRatioHistory(ILjava/lang/String;)Ljava/lang/String;
    .locals 2

    const/16 v0, 0xa5

    if-eq p0, v0, :cond_0

    const/16 v0, 0xba

    if-ne p0, v0, :cond_1

    :cond_0
    const/16 p0, 0xa3

    :cond_1
    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sZoomRatioHistory:Ljava/util/Map;

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    const/16 v1, 0xbc

    if-ne p0, v1, :cond_3

    if-eqz v0, :cond_2

    goto :goto_0

    :cond_2
    const-string v0, "5.0"

    :goto_0
    return-object v0

    :cond_3
    if-eqz v0, :cond_4

    move-object p1, v0

    :cond_4
    return-object p1
.end method

.method public static getZoomRatioNone(ZI)F
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->jj()Z

    move-result v0

    if-eqz v0, :cond_1

    if-eqz p0, :cond_1

    if-eqz p1, :cond_0

    const/16 p0, 0xb4

    if-ne p1, p0, :cond_1

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p0

    invoke-virtual {p0}, Lb/c/a/b;->wh()F

    move-result p0

    return p0

    :cond_1
    const/high16 p0, 0x3f800000    # 1.0f

    return p0
.end method

.method public static isOpticalZoomRatio(IF)Z
    .locals 0

    invoke-static {p0}, Lcom/android/camera/HybridZoomingSystem;->getSupportedOpticalZoomRatios(I)[F

    move-result-object p0

    invoke-static {p0, p1}, Ljava/util/Arrays;->binarySearch([FF)I

    move-result p0

    if-ltz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public static isZoomRatioNone(FZ)Z
    .locals 4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->jj()Z

    move-result v0

    const/4 v1, 0x1

    const/4 v2, 0x0

    const/high16 v3, 0x3f800000    # 1.0f

    if-eqz v0, :cond_2

    if-eqz p1, :cond_2

    cmpl-float p1, p0, v3

    if-eqz p1, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p1

    invoke-virtual {p1}, Lb/c/a/b;->wh()F

    move-result p1

    cmpl-float p0, p0, p1

    if-nez p0, :cond_0

    goto :goto_0

    :cond_0
    move v1, v2

    :cond_1
    :goto_0
    return v1

    :cond_2
    cmpl-float p0, p0, v3

    if-nez p0, :cond_3

    goto :goto_1

    :cond_3
    move v1, v2

    :goto_1
    return v1
.end method

.method static preload()V
    .locals 2

    const-string v0, "HybridZoomingSystem"

    const-string v1, "preload"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public static setZoomRatioHistory(ILjava/lang/String;)V
    .locals 1

    const/16 v0, 0xa5

    if-eq p0, v0, :cond_0

    const/16 v0, 0xba

    if-ne p0, v0, :cond_1

    :cond_0
    const/16 p0, 0xa3

    :cond_1
    sget-object v0, Lcom/android/camera/HybridZoomingSystem;->sZoomRatioHistory:Ljava/util/Map;

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    invoke-interface {v0, p0, p1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public static sub(FF)F
    .locals 1

    const/high16 v0, 0x41200000    # 10.0f

    mul-float/2addr p0, v0

    float-to-int p0, p0

    mul-float/2addr p1, v0

    float-to-int p1, p1

    sub-int/2addr p0, p1

    int-to-float p0, p0

    div-float/2addr p0, v0

    return p0
.end method

.method public static toCropRegion(FLandroid/graphics/Rect;)Landroid/graphics/Rect;
    .locals 6

    const/4 v0, 0x0

    cmpg-float v0, p0, v0

    if-lez v0, :cond_1

    if-eqz p1, :cond_0

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v0

    div-int/lit8 v0, v0, 0x2

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result v1

    div-int/lit8 v1, v1, 0x2

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v2

    int-to-float v2, v2

    const/high16 v3, 0x40000000    # 2.0f

    mul-float/2addr v3, p0

    div-float/2addr v2, v3

    float-to-int v2, v2

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result p1

    int-to-float p1, p1

    div-float/2addr p1, v3

    float-to-int p1, p1

    new-instance v3, Landroid/graphics/Rect;

    invoke-direct {v3}, Landroid/graphics/Rect;-><init>()V

    sub-int v4, v0, v2

    sub-int v5, v1, p1

    add-int/2addr v0, v2

    add-int/2addr v1, p1

    invoke-virtual {v3, v4, v5, v0, v1}, Landroid/graphics/Rect;->set(IIII)V

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "toCropRegion(): zoom ratio = "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string p0, ", crop region = "

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "HybridZoomingSystem"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object v3

    :cond_0
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string p1, "activeArraySize must be non null"

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0

    :cond_1
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string p1, "Zoom ratio must be greater than 0.0f"

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static toDecimal(F)F
    .locals 1

    const/high16 v0, 0x41200000    # 10.0f

    mul-float/2addr p0, v0

    float-to-int p0, p0

    int-to-float p0, p0

    div-float/2addr p0, v0

    return p0
.end method

.method public static toFloat(Ljava/lang/String;F)F
    .locals 2

    :try_start_0
    invoke-static {p0}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result p0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    :catch_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Invalid zoom: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "HybridZoomingSystem"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return p1
.end method

.method public static toMTKCropRegion(FLandroid/graphics/Rect;)[I
    .locals 6

    const/4 v0, 0x0

    cmpg-float v0, p0, v0

    if-lez v0, :cond_1

    if-eqz p1, :cond_0

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v0

    const/4 v1, 0x2

    div-int/2addr v0, v1

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result v2

    div-int/2addr v2, v1

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v3

    int-to-float v3, v3

    const/high16 v4, 0x40000000    # 2.0f

    mul-float/2addr v4, p0

    div-float/2addr v3, v4

    float-to-int v3, v3

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result p1

    int-to-float p1, p1

    div-float/2addr p1, v4

    float-to-int p1, p1

    const/4 v4, 0x4

    new-array v4, v4, [I

    sub-int/2addr v0, v3

    const/4 v5, 0x0

    aput v0, v4, v5

    sub-int/2addr v2, p1

    const/4 v0, 0x1

    aput v2, v4, v0

    mul-int/2addr v3, v1

    aput v3, v4, v1

    mul-int/2addr p1, v1

    const/4 v2, 0x3

    aput p1, v4, v2

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "int[]{"

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget v3, v4, v5

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, ", "

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget v0, v4, v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget v0, v4, v1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget v0, v4, v2

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v0, "}"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "toMTKCropRegion(): zoom ratio = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string p0, ", crop region = "

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "HybridZoomingSystem"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object v4

    :cond_0
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string p1, "activeArraySize must be non null"

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0

    :cond_1
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string p1, "Zoom ratio must be greater than 0.0f"

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static toString(F)Ljava/lang/String;
    .locals 4

    sget v0, Lcom/android/camera/HybridZoomingSystem;->FLOAT_ZOOM_RATIO_ULTR:F

    cmpl-float v0, v0, p0

    const/4 v1, 0x0

    const/4 v2, 0x1

    const-string v3, "%.1fx"

    if-eqz v0, :cond_2

    const/high16 v0, 0x3f800000    # 1.0f

    cmpl-float v0, v0, p0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/HybridZoomingSystem;->getTeleMinZoomRatio()F

    move-result v0

    cmpl-float v0, v0, p0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/HybridZoomingSystem;->getUltraTeleMinZoomRatio()F

    move-result v0

    cmpl-float v0, v0, p0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    cmpl-float v0, v0, p0

    if-nez v0, :cond_1

    const-string p0, "macro"

    return-object p0

    :cond_1
    invoke-static {p0}, Lcom/android/camera/HybridZoomingSystem;->toDecimal(F)F

    move-result p0

    sget-object v0, Ljava/util/Locale;->US:Ljava/util/Locale;

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {p0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p0

    aput-object p0, v2, v1

    invoke-static {v0, v3, v2}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0

    :cond_2
    :goto_0
    sget-object v0, Ljava/util/Locale;->US:Ljava/util/Locale;

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {p0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p0

    aput-object p0, v2, v1

    invoke-static {v0, v3, v2}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public static toZoomRatio(Landroid/graphics/Rect;Landroid/graphics/Rect;)F
    .locals 3

    if-eqz p0, :cond_0

    if-eqz p1, :cond_0

    invoke-virtual {p0}, Landroid/graphics/Rect;->width()I

    move-result v0

    int-to-float v0, v0

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v1

    int-to-float v1, v1

    div-float/2addr v0, v1

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "toZoomRatio(): activeArraySize = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string p0, ", crop region = "

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "HybridZoomingSystem"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->c(Ljava/lang/String;Ljava/lang/String;)I

    return v0

    :cond_0
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string p1, "activeArraySize & cropRegion must be non null"

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method
