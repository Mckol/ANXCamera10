.class Lcom/android/camera/module/Camera2Module$24$1;
.super Ljava/lang/Object;
.source "Camera2Module.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/module/Camera2Module$24;->onSessionStatusFlawResultData(II)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/camera/module/Camera2Module$24;

.field final synthetic val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

.field final synthetic val$flawResult:I


# direct methods
.method constructor <init>(Lcom/android/camera/module/Camera2Module$24;ILcom/android/camera/fragment/top/FragmentTopConfig;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module$24$1;->this$1:Lcom/android/camera/module/Camera2Module$24;

    iput p2, p0, Lcom/android/camera/module/Camera2Module$24$1;->val$flawResult:I

    iput-object p3, p0, Lcom/android/camera/module/Camera2Module$24$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 7

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iget v1, p0, Lcom/android/camera/module/Camera2Module$24$1;->val$flawResult:I

    if-eqz v1, :cond_6

    const/4 v2, 0x1

    const-wide/16 v3, 0xbb8

    const/4 v5, 0x0

    const-string v6, "attr_asd_flaw_tip"

    if-eq v1, v2, :cond_4

    const/4 v2, 0x2

    if-eq v1, v2, :cond_2

    const/4 v2, 0x3

    if-eq v1, v2, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/module/Camera2Module$24$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    if-eqz p0, :cond_1

    const v1, 0x7f1002f9

    invoke-virtual {p0, v5, v1, v3, v4}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertAiDetectTipHint(IIJ)V

    :cond_1
    const-string p0, "asd_flaw_blink_more"

    invoke-interface {v0, v6, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_2
    iget-object p0, p0, Lcom/android/camera/module/Camera2Module$24$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    if-eqz p0, :cond_3

    const v1, 0x7f1002fa

    invoke-virtual {p0, v5, v1, v3, v4}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertAiDetectTipHint(IIJ)V

    :cond_3
    const-string p0, "asd_flaw_blink_one"

    invoke-interface {v0, v6, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_4
    iget-object p0, p0, Lcom/android/camera/module/Camera2Module$24$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    if-eqz p0, :cond_5

    const v1, 0x7f1002fb

    invoke-virtual {p0, v5, v1, v3, v4}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertAiDetectTipHint(IIJ)V

    :cond_5
    const-string p0, "asd_flaw_cover"

    invoke-interface {v0, v6, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_6
    :goto_0
    const-string p0, "key_common_tips"

    invoke-static {p0, v0}, Lcom/android/camera/statistic/MistatsWrapper;->mistatEvent(Ljava/lang/String;Ljava/util/Map;)V

    return-void
.end method
