.class Lcom/android/camera/ui/ScreenHint$1;
.super Ljava/lang/Object;
.source "ScreenHint.java"

# interfaces
.implements Landroid/content/DialogInterface$OnDismissListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/ui/ScreenHint;->showFirstUseHint(Ljava/lang/Runnable;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/ui/ScreenHint;

.field final synthetic val$doNext:Ljava/lang/Runnable;


# direct methods
.method constructor <init>(Lcom/android/camera/ui/ScreenHint;Ljava/lang/Runnable;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/ui/ScreenHint$1;->this$0:Lcom/android/camera/ui/ScreenHint;

    iput-object p2, p0, Lcom/android/camera/ui/ScreenHint$1;->val$doNext:Ljava/lang/Runnable;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onDismiss(Landroid/content/DialogInterface;)V
    .locals 1

    iget-object p1, p0, Lcom/android/camera/ui/ScreenHint$1;->this$0:Lcom/android/camera/ui/ScreenHint;

    invoke-static {p1}, Lcom/android/camera/ui/ScreenHint;->access$000(Lcom/android/camera/ui/ScreenHint;)Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/ui/ScreenHint$1;->this$0:Lcom/android/camera/ui/ScreenHint;

    invoke-static {p1}, Lcom/android/camera/ui/ScreenHint;->access$100(Lcom/android/camera/ui/ScreenHint;)Landroid/app/Activity;

    move-result-object p1

    invoke-static {p1}, Lcom/android/camera/permission/PermissionManager;->requestCameraLocationPermissions(Landroid/app/Activity;)Z

    move-result p1

    if-eqz p1, :cond_1

    const/4 p1, 0x1

    invoke-static {p1}, Lcom/android/camera/CameraSettings;->updateRecordLocationPreference(Z)V

    invoke-static {}, Lcom/android/camera/LocationManager;->instance()Lcom/android/camera/LocationManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/android/camera/LocationManager;->recordLocation(Z)V

    iget-object p1, p0, Lcom/android/camera/ui/ScreenHint$1;->val$doNext:Ljava/lang/Runnable;

    invoke-interface {p1}, Ljava/lang/Runnable;->run()V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/ui/ScreenHint$1;->val$doNext:Ljava/lang/Runnable;

    invoke-interface {p1}, Ljava/lang/Runnable;->run()V

    :cond_1
    :goto_0
    iget-object p0, p0, Lcom/android/camera/ui/ScreenHint$1;->this$0:Lcom/android/camera/ui/ScreenHint;

    const/4 p1, 0x0

    invoke-static {p0, p1}, Lcom/android/camera/ui/ScreenHint;->access$202(Lcom/android/camera/ui/ScreenHint;Lmiui/app/AlertDialog;)Lmiui/app/AlertDialog;

    return-void
.end method
