.class public Lmiui/util/AlmanacConsPros;
.super Ljava/lang/Object;
.source "AlmanacConsPros.java"


# static fields
.field private static final DATA:[Ljava/lang/String;

.field private static final DATA_ASSET_NAME:Ljava/lang/String; = "huangli.idf"

.field private static final END_TIME:Ljava/lang/Long;

.field private static final START_TIME:Ljava/lang/Long;

.field private static final TAG:Ljava/lang/String; = "AlmanacConsPros"

.field private static sSingleton:Lmiui/util/AlmanacConsPros;


# instance fields
.field private mReader:Lmiui/util/DirectIndexedFile$Reader;


# direct methods
.method static constructor <clinit>()V
    .locals 114

    new-instance v0, Ljava/util/GregorianCalendar;

    const/16 v1, 0x76d

    const/4 v2, 0x0

    const/4 v3, 0x1

    invoke-direct {v0, v1, v2, v3}, Ljava/util/GregorianCalendar;-><init>(III)V

    invoke-virtual {v0}, Ljava/util/GregorianCalendar;->getTimeInMillis()J

    move-result-wide v0

    invoke-static {v0, v1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    sput-object v0, Lmiui/util/AlmanacConsPros;->START_TIME:Ljava/lang/Long;

    new-instance v0, Ljava/util/GregorianCalendar;

    const/16 v1, 0x834

    const/16 v2, 0xb

    const/16 v3, 0x1f

    invoke-direct {v0, v1, v2, v3}, Ljava/util/GregorianCalendar;-><init>(III)V

    invoke-virtual {v0}, Ljava/util/GregorianCalendar;->getTimeInMillis()J

    move-result-wide v0

    invoke-static {v0, v1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    sput-object v0, Lmiui/util/AlmanacConsPros;->END_TIME:Ljava/lang/Long;

    const-string v1, "\u7acb\u7891"

    const-string v2, "\u796d\u7940"

    const-string v3, "\u8d77\u57fa"

    const-string v4, "\u79fb\u5f99"

    const-string v5, "\u5f00\u4ed3"

    const-string v6, "\u7834\u5c4b"

    const-string v7, "\u4f5c\u5395"

    const-string v8, "\u5408\u5e10"

    const-string v9, "\u5165\u5b66"

    const-string v10, "\u4ea4\u6613"

    const-string v11, "\u9020\u4ed3"

    const-string v12, "\u6302\u533e"

    const-string v13, "\u7834\u571f"

    const-string v14, "\u5408\u810a"

    const-string v15, "\u542f\u6512"

    const-string v16, "\u658b\u91ae"

    const-string v17, "\u6559\u725b\u9a6c"

    const-string v18, "\u6355\u6349"

    const-string v19, "\u5b89\u846c"

    const-string v20, "\u4f59\u4e8b\u52ff\u53d6"

    const-string v21, "\u5b89\u9999"

    const-string v22, "\u5b89\u95e8"

    const-string v23, "\u4e0a\u6881"

    const-string v24, "\u5408\u5bff\u6728"

    const-string v25, "\u884c\u4e27"

    const-string v26, "\u8ba2\u76df"

    const-string v27, "\u7ecf\u7edc"

    const-string v28, "\u7ed3\u7f51"

    const-string v29, "\u9020\u6865"

    const-string v30, "\u5b89\u5e8a"

    const-string v31, "\u53d6\u6e14"

    const-string v32, "\u6c90\u6d74"

    const-string v33, "\u79fb\u67e9"

    const-string v34, "\u9020\u755c\u6906\u6816"

    const-string v35, "\u916c\u795e"

    const-string v36, "\u8fdb\u4eba\u53e3"

    const-string v37, "\u5f00\u751f\u575f"

    const-string v38, "\u683d\u79cd"

    const-string v39, "\u4f5c\u6881"

    const-string v40, "\u666e\u6e21"

    const-string v41, "\u6398\u4e95"

    const-string v42, "\u8c22\u571f"

    const-string v43, "\u7acb\u5238"

    const-string v44, "\u5272\u871c"

    const-string v45, "\u4e58\u8239"

    const-string v46, "\u5165\u5b85"

    const-string v47, "\u5206\u5c45"

    const-string v48, "\u67b6\u9a6c"

    const-string v49, "\u5f52\u5b81"

    const-string v50, "\u5b89\u7893\u78d1"

    const-string v51, "\u96d5\u523b"

    const-string v52, "\u5851\u7ed8"

    const-string v53, "\u7948\u798f"

    const-string v54, "\u5f00\u5149"

    const-string v55, "\u65ad\u8681"

    const-string v56, "\u51fa\u706b"

    const-string v57, "\u5165\u6b93"

    const-string v58, "\u9020\u5c4b"

    const-string v59, "\u4fee\u9970\u57a3\u5899"

    const-string v60, "\u9020\u8f66\u5668"

    const-string v61, "\u51fa\u884c"

    const-string v62, "\u88c1\u8863"

    const-string v63, "\u6574\u624b\u8db3\u7532"

    const-string v64, "\u5e73\u6cbb\u9053\u6d82"

    const-string v65, "\u9020\u8239"

    const-string v66, "\u5f52\u5cab"

    const-string v67, "\u7eb3\u5a7f"

    const-string v68, "\u95ee\u540d"

    const-string v69, "\u96c7\u5eb8"

    const-string v70, "\u51a0\u7b04"

    const-string v71, "\u5f00\u67f1\u773c"

    const-string v72, "\u4fee\u95e8"

    const-string v73, "\u7406\u53d1"

    const-string v74, "\u4e60\u827a"

    const-string v75, "\u7eb3\u755c"

    const-string v76, "\u5f00\u6e20"

    const-string v77, "\u7f6e\u4ea7"

    const-string v78, "\u7eb3\u8d22"

    const-string v79, "\u5b89\u673a\u68b0"

    const-string v80, "\u51fa\u8d27\u8d22"

    const-string v81, "\u62c6\u5378"

    const-string v82, "\u7eb3\u91c7"

    const-string v83, "\u4fee\u575f"

    const-string v84, "\u626b\u820d"

    const-string v85, "\u6c42\u55e3"

    const-string v86, "\u653e\u6c34"

    const-string v87, "\u8865\u57a3"

    const-string v88, "\u63a2\u75c5"

    const-string v89, "\u6c42\u533b"

    const-string v90, "\u9488\u7078"

    const-string v91, "\u7ad6\u67f1"

    const-string v92, "\u6210\u670d"

    const-string v93, "\u5f00\u6c60"

    const-string v94, "\u4f10\u6728"

    const-string v95, "\u4f5c\u7076"

    const-string v96, "\u8bcd\u8bbc"

    const-string v97, "\u8d74\u4efb"

    const-string v98, "\u574f\u57a3"

    const-string v99, "\u585e\u7a74"

    const-string v100, "\u7b51\u5824"

    const-string v101, "\u4f1a\u4eb2\u53cb"

    const-string v102, "\u7267\u517b"

    const-string v103, "\u8bf8\u4e8b\u4e0d\u5b9c"

    const-string v104, "\u9020\u5e99"

    const-string v105, "\u89e3\u9664"

    const-string v106, "\u9664\u670d"

    const-string v107, "\u754b\u730e"

    const-string v108, "\u6cbb\u75c5"

    const-string v109, "\u5b9a\u78c9"

    const-string v110, "\u5f00\u5e02"

    const-string v111, "\u52a8\u571f"

    const-string v112, "\u5ac1\u5a36"

    const-string v113, "\u4fee\u9020"

    filled-new-array/range {v1 .. v113}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lmiui/util/AlmanacConsPros;->DATA:[Ljava/lang/String;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    invoke-direct {p0}, Lmiui/util/AlmanacConsPros;->initResource()V

    return-void
.end method

.method public static declared-synchronized getInstance()Lmiui/util/AlmanacConsPros;
    .locals 2

    const-class v0, Lmiui/util/AlmanacConsPros;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lmiui/util/AlmanacConsPros;->sSingleton:Lmiui/util/AlmanacConsPros;

    if-nez v1, :cond_0

    new-instance v1, Lmiui/util/AlmanacConsPros;

    invoke-direct {v1}, Lmiui/util/AlmanacConsPros;-><init>()V

    sput-object v1, Lmiui/util/AlmanacConsPros;->sSingleton:Lmiui/util/AlmanacConsPros;

    :cond_0
    sget-object v1, Lmiui/util/AlmanacConsPros;->sSingleton:Lmiui/util/AlmanacConsPros;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method private initResource()V
    .locals 3

    invoke-static {}, Lmiui/util/AppConstants;->getCurrentApplication()Landroid/app/Application;

    move-result-object v0

    const-string v1, "huangli.idf"

    invoke-static {v0, v1}, Lcom/miui/internal/util/DirectIndexedFileExtractor;->getDirectIndexedFilePath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_0

    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v2

    if-eqz v2, :cond_0

    :try_start_0
    invoke-static {v0}, Lmiui/util/DirectIndexedFile;->open(Ljava/lang/String;)Lmiui/util/DirectIndexedFile$Reader;

    move-result-object v0

    iput-object v0, p0, Lmiui/util/AlmanacConsPros;->mReader:Lmiui/util/DirectIndexedFile$Reader;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    :cond_0
    :goto_0
    iget-object v0, p0, Lmiui/util/AlmanacConsPros;->mReader:Lmiui/util/DirectIndexedFile$Reader;

    if-nez v0, :cond_1

    :try_start_1
    invoke-static {}, Lmiui/util/AppConstants;->getCurrentApplication()Landroid/app/Application;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Application;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;I)Ljava/io/InputStream;

    move-result-object v0

    invoke-static {v0}, Lmiui/util/DirectIndexedFile;->open(Ljava/io/InputStream;)Lmiui/util/DirectIndexedFile$Reader;

    move-result-object v0

    iput-object v0, p0, Lmiui/util/AlmanacConsPros;->mReader:Lmiui/util/DirectIndexedFile$Reader;
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_1

    goto :goto_1

    :catch_1
    const-string p0, "AlmanacConsPros"

    const-string v0, "Can\'t read huangli.idf, NO huangli supported!"

    invoke-static {p0, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :cond_1
    :goto_1
    return-void
.end method


# virtual methods
.method protected finalize()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    iget-object v0, p0, Lmiui/util/AlmanacConsPros;->mReader:Lmiui/util/DirectIndexedFile$Reader;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lmiui/util/DirectIndexedFile$Reader;->close()V

    :cond_0
    invoke-super {p0}, Ljava/lang/Object;->finalize()V

    return-void
.end method

.method public getConsPros(J)[Ljava/lang/String;
    .locals 8

    iget-object v0, p0, Lmiui/util/AlmanacConsPros;->mReader:Lmiui/util/DirectIndexedFile$Reader;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return-object v1

    :cond_0
    sget-object v0, Lmiui/util/AlmanacConsPros;->START_TIME:Ljava/lang/Long;

    invoke-virtual {v0}, Ljava/lang/Long;->longValue()J

    move-result-wide v2

    cmp-long v0, p1, v2

    if-ltz v0, :cond_4

    sget-object v0, Lmiui/util/AlmanacConsPros;->END_TIME:Ljava/lang/Long;

    invoke-virtual {v0}, Ljava/lang/Long;->longValue()J

    move-result-wide v2

    cmp-long v0, p1, v2

    if-lez v0, :cond_1

    goto :goto_2

    :cond_1
    const/4 v0, 0x2

    new-array v1, v0, [Ljava/lang/String;

    sget-object v2, Lmiui/util/AlmanacConsPros;->START_TIME:Ljava/lang/Long;

    invoke-virtual {v2}, Ljava/lang/Long;->longValue()J

    move-result-wide v2

    sub-long/2addr p1, v2

    const-wide/32 v2, 0x5265c00

    div-long/2addr p1, v2

    long-to-int p1, p1

    iget-object p0, p0, Lmiui/util/AlmanacConsPros;->mReader:Lmiui/util/DirectIndexedFile$Reader;

    const/4 p2, 0x0

    invoke-virtual {p0, p2, p1, p2}, Lmiui/util/DirectIndexedFile$Reader;->get(III)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/String;

    const/4 p1, -0x1

    const-string v2, ","

    invoke-virtual {p0, v2, p1}, Ljava/lang/String;->split(Ljava/lang/String;I)[Ljava/lang/String;

    move-result-object p0

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    array-length v2, p0

    if-ne v2, v0, :cond_4

    move v0, p2

    :goto_0
    array-length v2, p0

    if-ge v0, v2, :cond_4

    invoke-virtual {p1}, Ljava/util/ArrayList;->clear()V

    aget-object v2, p0, v0

    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_3

    aget-object v2, p0, v0

    const-string v3, "\u3001"

    invoke-virtual {v2, v3}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v2

    array-length v3, v2

    move v4, p2

    :goto_1
    if-ge v4, v3, :cond_3

    aget-object v5, v2, v4

    invoke-static {v5}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    sget-object v6, Lmiui/util/AlmanacConsPros;->DATA:[Ljava/lang/String;

    array-length v7, v6

    if-ge v5, v7, :cond_2

    aget-object v5, v6, v5

    invoke-virtual {p1, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_2
    add-int/lit8 v4, v4, 0x1

    goto :goto_1

    :cond_3
    const-string v2, " "

    invoke-static {v2, p1}, Landroid/text/TextUtils;->join(Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v0

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_4
    :goto_2
    return-object v1
.end method
