.class Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity$3;
.super Ljava/lang/Object;
.source "RestoreFailedDialogActivity.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;->configDialog()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;


# direct methods
.method constructor <init>(Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;)V
    .locals 0

    iput-object p1, p0, Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity$3;->this$0:Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    iget-object p1, p0, Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity$3;->this$0:Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;

    const/4 p2, 0x1

    invoke-static {p1, p2}, Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;->access$202(Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;Z)Z

    iget-object p0, p0, Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity$3;->this$0:Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;

    invoke-static {p0}, Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;->access$300(Lmiui/autoinstall/config/activity/RestoreFailedDialogActivity;)V

    return-void
.end method
