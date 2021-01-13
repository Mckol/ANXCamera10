.class public final Lcom/android/camera/storage/ParallelSaveRequest;
.super Lcom/android/camera/storage/AbstractSaveRequest;
.source "ParallelSaveRequest.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "ParallelSaveRequest"


# instance fields
.field private isHeic:Z

.field private mAlgorithmName:Ljava/lang/String;

.field private mContext:Landroid/content/Context;

.field private mData:[B

.field private mInfo:Lcom/xiaomi/camera/core/PictureInfo;

.field private mJpegRotation:I

.field private mLocation:Landroid/location/Location;

.field private mNeedThumbnail:Z

.field private mSavePath:Ljava/lang/String;

.field private mSaverCallback:Lcom/android/camera/storage/SaverCallback;

.field private mSize:I

.field private mTimestamp:J


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Lcom/xiaomi/camera/core/ParallelTaskData;Lcom/android/camera/storage/SaverCallback;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/storage/AbstractSaveRequest;-><init>()V

    iput-object p1, p0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-virtual {p0, p2}, Lcom/android/camera/storage/AbstractSaveRequest;->setSaverCallback(Lcom/android/camera/storage/SaverCallback;)V

    invoke-virtual {p0}, Lcom/android/camera/storage/AbstractSaveRequest;->calculateMemoryUsed()I

    move-result p1

    iput p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSize:I

    invoke-virtual {p0}, Lcom/android/camera/storage/AbstractSaveRequest;->isHeicSavingRequest()Z

    move-result p1

    iput-boolean p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->isHeic:Z

    return-void
.end method

.method private parseDocAndSave(IILjava/lang/String;Landroid/net/Uri;)V
    .locals 21

    move-object/from16 v1, p0

    sget-object v0, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "parse document E: sensorOrientation = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move/from16 v3, p2

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    array-length v2, v0

    const/4 v4, 0x0

    invoke-static {v0, v4, v2}, Landroid/graphics/BitmapFactory;->decodeByteArray([BII)Landroid/graphics/Bitmap;

    move-result-object v6

    invoke-static {}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess;->getInstance()Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess;

    move-result-object v0

    sget-object v2, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$DocumentType;->PPT:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$DocumentType;

    invoke-static/range {p2 .. p2}, Lcom/android/zxing/DocumentDecoder;->getRotateFlag(I)Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    move-result-object v3

    invoke-virtual {v0, v6, v2, v3}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess;->doScanDocument(Landroid/graphics/Bitmap;Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$DocumentType;Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;)[F

    move-result-object v2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDocument()Lcom/android/camera/data/data/runing/ComponentRunningDocument;

    move-result-object v0

    const/16 v3, 0xa3

    invoke-virtual {v0, v3}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object v3

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {v3, v0}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$EnhanceType;->valueOf(Ljava/lang/String;)Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$EnhanceType;

    move-result-object v8

    invoke-static {}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess;->getInstance()Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess;

    move-result-object v5

    sget-object v9, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$DocumentType;->PPT:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$DocumentType;

    const/4 v10, 0x0

    move-object v7, v2

    invoke-virtual/range {v5 .. v10}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess;->doCropAndEnhance(Landroid/graphics/Bitmap;[FLcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$EnhanceType;Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$DocumentType;Z)Landroid/graphics/Bitmap;

    move-result-object v5

    new-instance v6, Lcom/android/gallery3d/exif/ExifInterface;

    invoke-direct {v6}, Lcom/android/gallery3d/exif/ExifInterface;-><init>()V

    :try_start_0
    iget-object v0, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    invoke-virtual {v6, v0}, Lcom/android/gallery3d/exif/ExifInterface;->readExif([B)V

    invoke-virtual {v6}, Lcom/android/gallery3d/exif/ExifInterface;->removeCompressedThumbnail()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    sget-object v7, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "readExif: error "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8, v0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    const/4 v0, 0x1

    invoke-virtual {v6, v0}, Lcom/android/gallery3d/exif/ExifInterface;->addXiaomiDocPhotoVersion(B)Z

    new-instance v0, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v0}, Ljava/io/ByteArrayOutputStream;-><init>()V

    invoke-virtual {v6, v0}, Lcom/android/gallery3d/exif/ExifInterface;->getExifWriterStream(Ljava/io/OutputStream;)Ljava/io/OutputStream;

    move-result-object v6

    sget-object v7, Landroid/graphics/Bitmap$CompressFormat;->JPEG:Landroid/graphics/Bitmap$CompressFormat;

    const/16 v8, 0x64

    invoke-virtual {v5, v7, v8, v6}, Landroid/graphics/Bitmap;->compress(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z

    invoke-virtual {v0}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v0

    iget-object v6, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    array-length v6, v6

    int-to-long v6, v6

    invoke-static {v0, v6, v7, v2, v3}, Lcom/android/camera/storage/Storage;->addXmpData([BJ[FLjava/lang/String;)[B

    move-result-object v0

    array-length v2, v0

    iget-object v3, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    array-length v3, v3

    add-int/2addr v2, v3

    new-array v7, v2, [B

    array-length v2, v0

    invoke-static {v0, v4, v7, v4, v2}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    iget-object v2, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    array-length v0, v0

    array-length v3, v2

    invoke-static {v2, v4, v7, v0, v3}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    sget-object v0, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    const-string v2, "parse document X: "

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v6, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    const/4 v8, 0x0

    const/4 v9, 0x0

    iget-object v12, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mLocation:Landroid/location/Location;

    invoke-virtual {v5}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v14

    invoke-virtual {v5}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v15

    const/16 v16, 0x0

    const/16 v17, 0x0

    const/16 v18, 0x0

    iget-object v0, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mAlgorithmName:Ljava/lang/String;

    iget-object v1, v1, Lcom/android/camera/storage/ParallelSaveRequest;->mInfo:Lcom/xiaomi/camera/core/PictureInfo;

    move-object/from16 v10, p4

    move-object/from16 v11, p3

    move/from16 v13, p1

    move-object/from16 v19, v0

    move-object/from16 v20, v1

    invoke-static/range {v6 .. v20}, Lcom/android/camera/storage/Storage;->updateImageWithExtraExif(Landroid/content/Context;[BZLcom/android/gallery3d/exif/ExifInterface;Landroid/net/Uri;Ljava/lang/String;Landroid/location/Location;IIILjava/lang/String;ZZLjava/lang/String;Lcom/xiaomi/camera/core/PictureInfo;)Z

    return-void
.end method


# virtual methods
.method public getSize()I
    .locals 0

    iget p0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSize:I

    return p0
.end method

.method public isFinal()Z
    .locals 0

    const/4 p0, 0x1

    return p0
.end method

.method public onFinish()V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/xiaomi/camera/core/ParallelTaskData;->getCaptureTime()J

    move-result-wide v2

    const-wide/16 v4, 0x0

    cmp-long v0, v2, v4

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-virtual {v0}, Lcom/xiaomi/camera/core/ParallelTaskData;->getCaptureTime()J

    move-result-wide v2

    invoke-static {v1, v2, v3}, Lcom/android/camera/statistic/ScenarioTrackUtil;->trackShotToViewEnd(ZJ)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    invoke-static {v0, v1}, Lcom/xiaomi/camera/base/PerformanceTracker;->trackImageSaver(Ljava/lang/Object;I)V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    iget-object v1, p0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    if-eqz v1, :cond_1

    invoke-virtual {v1}, Lcom/xiaomi/camera/core/ParallelTaskData;->releaseImageData()V

    iput-object v0, p0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    :cond_1
    iget-object v0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    iget p0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSize:I

    invoke-interface {v0, p0}, Lcom/android/camera/storage/SaverCallback;->onSaveFinish(I)V

    return-void
.end method

.method protected reFillSaveRequest([BJJLandroid/location/Location;ILjava/lang/String;IIZLjava/lang/String;Lcom/xiaomi/camera/core/PictureInfo;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    iput-wide p2, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mTimestamp:J

    iput-wide p4, p0, Lcom/android/camera/storage/AbstractSaveRequest;->date:J

    if-nez p6, :cond_0

    const/4 p1, 0x0

    goto :goto_0

    :cond_0
    new-instance p1, Landroid/location/Location;

    invoke-direct {p1, p6}, Landroid/location/Location;-><init>(Landroid/location/Location;)V

    :goto_0
    iput-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mLocation:Landroid/location/Location;

    iput p7, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mJpegRotation:I

    iput-object p8, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    iput p9, p0, Lcom/android/camera/storage/AbstractSaveRequest;->width:I

    iput p10, p0, Lcom/android/camera/storage/AbstractSaveRequest;->height:I

    iput-boolean p11, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mNeedThumbnail:Z

    iput-object p12, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mAlgorithmName:Ljava/lang/String;

    iput-object p13, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mInfo:Lcom/xiaomi/camera/core/PictureInfo;

    return-void
.end method

.method public run()V
    .locals 0

    invoke-virtual {p0}, Lcom/android/camera/storage/ParallelSaveRequest;->save()V

    invoke-virtual {p0}, Lcom/android/camera/storage/ParallelSaveRequest;->onFinish()V

    return-void
.end method

.method public save()V
    .locals 27

    move-object/from16 v0, p0

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/storage/AbstractSaveRequest;->parserParallelTaskData()V

    sget-object v1, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "save: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " | "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mTimestamp:J

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->intern()Ljava/lang/String;

    move-result-object v1

    monitor-enter v1

    :try_start_0
    invoke-static {}, Lcom/android/camera/db/DbRepository;->dbItemSaveTask()Lcom/android/camera/db/item/DbItemSaveTask;

    move-result-object v2

    iget-object v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v2, v3}, Lcom/android/camera/db/item/DbItemSaveTask;->getItemByPath(Ljava/lang/String;)Lcom/android/camera/db/element/SaveTask;

    move-result-object v2

    if-nez v2, :cond_0

    iget-object v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v3}, Lcom/android/camera/Util;->getFileTitleFromPath(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/android/camera/storage/Storage;->isSaveToHidenFolder(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_0

    invoke-static {}, Lcom/android/camera/db/DbRepository;->dbItemSaveTask()Lcom/android/camera/db/item/DbItemSaveTask;

    move-result-object v3

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    invoke-virtual {v3, v4, v5}, Lcom/android/camera/db/item/DbItemBase;->generateItem(J)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/db/element/SaveTask;

    iget-object v4, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v3, v4}, Lcom/android/camera/db/element/SaveTask;->setPath(Ljava/lang/String;)V

    const-wide/16 v4, -0x1

    invoke-static {v4, v5}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v4

    invoke-virtual {v3, v4}, Lcom/android/camera/db/element/SaveTask;->setStartTime(Ljava/lang/Long;)V

    invoke-static {}, Lcom/android/camera/db/DbRepository;->dbItemSaveTask()Lcom/android/camera/db/item/DbItemSaveTask;

    move-result-object v4

    const-wide/16 v5, 0x0

    invoke-virtual {v4, v3, v5, v6}, Lcom/android/camera/db/item/DbItemBase;->endItemAndInsert(Ljava/lang/Object;J)J

    sget-object v3, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "insert full size picture:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v5, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    iget v3, v0, Lcom/android/camera/storage/AbstractSaveRequest;->width:I

    iget v4, v0, Lcom/android/camera/storage/AbstractSaveRequest;->height:I

    iget-object v5, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    invoke-static {v5}, Lcom/android/gallery3d/exif/ExifHelper;->getOrientation([B)I

    move-result v5

    iget v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mJpegRotation:I

    add-int/2addr v6, v5

    rem-int/lit16 v6, v6, 0xb4

    if-nez v6, :cond_1

    goto :goto_0

    :cond_1
    move/from16 v26, v4

    move v4, v3

    move/from16 v3, v26

    :goto_0
    const/4 v15, 0x0

    if-eqz v2, :cond_4

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->isValid()Z

    move-result v6

    if-nez v6, :cond_2

    goto/16 :goto_2

    :cond_2
    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v6}, Lcom/android/camera/Util;->getFileTitleFromPath(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->getMediaStoreId()Ljava/lang/Long;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/Long;->longValue()J

    move-result-wide v6

    invoke-static {v6, v7}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->getResultUri(J)Landroid/net/Uri;

    move-result-object v6

    sget-object v7, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "algo mark: uri: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Landroid/net/Uri;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " | "

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->getPath()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v7, v6}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v6, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "algo mark: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v8, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mJpegRotation:I

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " | "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " | "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " | "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    iget-object v7, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v6, v15, v7}, Lcom/android/camera/storage/Storage;->getMediaUri(Landroid/content/Context;ZLjava/lang/String;)Landroid/net/Uri;

    move-result-object v6

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->getMediaStoreId()Ljava/lang/Long;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/Long;->longValue()J

    move-result-wide v7

    invoke-static {v6, v7, v8}, Landroid/content/ContentUris;->withAppendedId(Landroid/net/Uri;J)Landroid/net/Uri;

    move-result-object v10

    iget-object v6, v0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    if-eqz v6, :cond_3

    iget-object v6, v0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-virtual {v6}, Lcom/xiaomi/camera/core/ParallelTaskData;->getDataParameter()Lcom/xiaomi/camera/core/ParallelTaskDataParameter;

    move-result-object v6

    if-eqz v6, :cond_3

    iget-object v6, v0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-virtual {v6}, Lcom/xiaomi/camera/core/ParallelTaskData;->getDataParameter()Lcom/xiaomi/camera/core/ParallelTaskDataParameter;

    move-result-object v6

    invoke-virtual {v6}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter;->isDocumentShot()Z

    move-result v6

    if-eqz v6, :cond_3

    iget-object v3, v0, Lcom/android/camera/storage/AbstractSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-virtual {v3}, Lcom/xiaomi/camera/core/ParallelTaskData;->getDataParameter()Lcom/xiaomi/camera/core/ParallelTaskDataParameter;

    move-result-object v3

    invoke-virtual {v3}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter;->getSensorOrientation()I

    move-result v3

    invoke-direct {v0, v5, v3, v11, v10}, Lcom/android/camera/storage/ParallelSaveRequest;->parseDocAndSave(IILjava/lang/String;Landroid/net/Uri;)V

    move v3, v15

    goto :goto_1

    :cond_3
    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    iget-object v7, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    iget-boolean v8, v0, Lcom/android/camera/storage/ParallelSaveRequest;->isHeic:Z

    const/4 v9, 0x0

    iget-object v12, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mLocation:Landroid/location/Location;

    const/16 v16, 0x0

    const/16 v17, 0x0

    const/16 v18, 0x0

    iget-object v14, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mAlgorithmName:Ljava/lang/String;

    iget-object v13, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mInfo:Lcom/xiaomi/camera/core/PictureInfo;

    move-object/from16 v20, v13

    move v13, v5

    move-object v5, v14

    move v14, v3

    move v3, v15

    move v15, v4

    move-object/from16 v19, v5

    invoke-static/range {v6 .. v20}, Lcom/android/camera/storage/Storage;->updateImageWithExtraExif(Landroid/content/Context;[BZLcom/android/gallery3d/exif/ExifInterface;Landroid/net/Uri;Ljava/lang/String;Landroid/location/Location;IIILjava/lang/String;ZZLjava/lang/String;Lcom/xiaomi/camera/core/PictureInfo;)Z

    :goto_1
    iget-object v0, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    invoke-static {v0, v2, v3}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->markTaskFinish(Landroid/content/Context;Lcom/android/camera/db/element/SaveTask;Z)V

    goto/16 :goto_7

    :cond_4
    :goto_2
    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    if-eqz v6, :cond_5

    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v6}, Lcom/android/camera/Util;->getFileTitleFromPath(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    goto :goto_3

    :cond_5
    iget-wide v6, v0, Lcom/android/camera/storage/AbstractSaveRequest;->date:J

    invoke-static {v6, v7}, Lcom/android/camera/Util;->createJpegName(J)Ljava/lang/String;

    move-result-object v6

    :goto_3
    move-object v14, v6

    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    iget-wide v8, v0, Lcom/android/camera/storage/AbstractSaveRequest;->date:J

    iget-object v10, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mLocation:Landroid/location/Location;

    iget-object v12, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    iget-boolean v13, v0, Lcom/android/camera/storage/ParallelSaveRequest;->isHeic:Z

    const/16 v16, 0x0

    const/16 v17, 0x0

    const/16 v18, 0x0

    const/16 v19, 0x0

    const/4 v11, 0x1

    if-eqz v2, :cond_6

    move/from16 v20, v11

    goto :goto_4

    :cond_6
    move/from16 v20, v15

    :goto_4
    iget-object v7, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mAlgorithmName:Ljava/lang/String;

    move-object/from16 v23, v2

    iget-object v2, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mInfo:Lcom/xiaomi/camera/core/PictureInfo;

    move-object/from16 v21, v7

    move-object v7, v14

    move v11, v5

    move-object/from16 v24, v14

    move v14, v3

    move/from16 v25, v5

    move v5, v15

    move v15, v4

    move-object/from16 v22, v2

    invoke-static/range {v6 .. v22}, Lcom/android/camera/storage/Storage;->addImage(Landroid/content/Context;Ljava/lang/String;JLandroid/location/Location;I[BZIIZZZZZLjava/lang/String;Lcom/xiaomi/camera/core/PictureInfo;)Landroid/net/Uri;

    move-result-object v2

    if-eqz v2, :cond_b

    iget-boolean v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mNeedThumbnail:Z

    const-wide/high16 v7, 0x4080000000000000L    # 512.0

    if-eqz v6, :cond_8

    int-to-double v9, v3

    int-to-double v11, v4

    invoke-static {v9, v10, v11, v12}, Ljava/lang/Math;->max(DD)D

    move-result-wide v9

    div-double/2addr v9, v7

    invoke-static {v9, v10}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v9

    double-to-int v6, v9

    invoke-static {v6}, Ljava/lang/Integer;->highestOneBit(I)I

    move-result v6

    iget-object v9, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    move/from16 v10, v25

    invoke-static {v9, v10, v6, v2, v5}, Lcom/android/camera/Thumbnail;->createThumbnail([BIILandroid/net/Uri;Z)Lcom/android/camera/Thumbnail;

    move-result-object v6

    if-eqz v6, :cond_7

    iget-object v9, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    const/4 v11, 0x1

    invoke-interface {v9, v6, v11}, Lcom/android/camera/storage/SaverCallback;->postUpdateThumbnail(Lcom/android/camera/Thumbnail;Z)V

    move v6, v11

    goto :goto_6

    :cond_7
    const/4 v11, 0x1

    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    invoke-interface {v6}, Lcom/android/camera/storage/SaverCallback;->postHideThumbnailProgressing()V

    goto :goto_5

    :cond_8
    move/from16 v10, v25

    const/4 v11, 0x1

    :goto_5
    move v6, v5

    :goto_6
    iget-object v9, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    const/4 v12, 0x2

    move-object/from16 v13, v24

    invoke-interface {v9, v2, v13, v12}, Lcom/android/camera/storage/SaverCallback;->notifyNewMediaData(Landroid/net/Uri;Ljava/lang/String;I)V

    if-eqz v23, :cond_9

    sget-object v3, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "algo mark: "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Landroid/net/Uri;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {v2}, Landroid/content/ContentUris;->parseId(Landroid/net/Uri;)J

    move-result-wide v2

    invoke-static {v2, v3}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    move-object/from16 v3, v23

    invoke-virtual {v3, v2}, Lcom/android/camera/db/element/SaveTask;->setMediaStoreId(Ljava/lang/Long;)V

    iget-object v0, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    invoke-static {v0, v3, v5}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->markTaskFinish(Landroid/content/Context;Lcom/android/camera/db/element/SaveTask;Z)V

    goto :goto_7

    :cond_9
    if-nez v6, :cond_a

    int-to-double v12, v3

    int-to-double v3, v4

    invoke-static {v12, v13, v3, v4}, Ljava/lang/Math;->max(DD)D

    move-result-wide v3

    div-double/2addr v3, v7

    invoke-static {v3, v4}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v3

    double-to-int v3, v3

    invoke-static {v3}, Ljava/lang/Integer;->highestOneBit(I)I

    move-result v3

    iget-object v4, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    invoke-static {v4, v10, v3, v2, v5}, Lcom/android/camera/Thumbnail;->createThumbnail([BIILandroid/net/Uri;Z)Lcom/android/camera/Thumbnail;

    move-result-object v3

    if-eqz v3, :cond_a

    iget-object v4, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    invoke-interface {v4, v3, v11}, Lcom/android/camera/storage/SaverCallback;->postUpdateThumbnail(Lcom/android/camera/Thumbnail;Z)V

    :cond_a
    iget-object v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    invoke-static {v2}, Landroid/content/ContentUris;->parseId(Landroid/net/Uri;)J

    move-result-wide v4

    iget-object v0, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v3, v4, v5, v0}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->insertImageToParallelService(Landroid/content/Context;JLjava/lang/String;)V

    goto :goto_7

    :cond_b
    move-object/from16 v13, v24

    invoke-static {v13}, Lcom/android/camera/storage/Storage;->isSaveToHidenFolder(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_c

    iget-object v0, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    const/4 v2, 0x0

    const/4 v3, 0x3

    invoke-interface {v0, v2, v13, v3}, Lcom/android/camera/storage/SaverCallback;->notifyNewMediaData(Landroid/net/Uri;Ljava/lang/String;I)V

    :cond_c
    :goto_7
    monitor-exit v1

    return-void

    :catchall_0
    move-exception v0

    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v0
.end method

.method public setContextAndCallback(Landroid/content/Context;Lcom/android/camera/storage/SaverCallback;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    iput-object p2, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    return-void
.end method
