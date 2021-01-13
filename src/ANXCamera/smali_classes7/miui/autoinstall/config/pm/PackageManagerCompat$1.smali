.class Lmiui/autoinstall/config/pm/PackageManagerCompat$1;
.super Ljava/lang/Object;
.source "PackageManagerCompat.java"

# interfaces
.implements Landroid/content/ServiceConnection;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/autoinstall/config/pm/PackageManagerCompat;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lmiui/autoinstall/config/pm/PackageManagerCompat;


# direct methods
.method constructor <init>(Lmiui/autoinstall/config/pm/PackageManagerCompat;)V
    .locals 0

    iput-object p1, p0, Lmiui/autoinstall/config/pm/PackageManagerCompat$1;->this$0:Lmiui/autoinstall/config/pm/PackageManagerCompat;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected(Landroid/content/ComponentName;Landroid/os/IBinder;)V
    .locals 0

    iget-object p0, p0, Lmiui/autoinstall/config/pm/PackageManagerCompat$1;->this$0:Lmiui/autoinstall/config/pm/PackageManagerCompat;

    invoke-static {p2}, Lb/b/a/a$b;->asInterface(Landroid/os/IBinder;)Lb/b/a/a;

    move-result-object p1

    invoke-static {p0, p1}, Lmiui/autoinstall/config/pm/PackageManagerCompat;->access$002(Lmiui/autoinstall/config/pm/PackageManagerCompat;Lb/b/a/a;)Lb/b/a/a;

    return-void
.end method

.method public onServiceDisconnected(Landroid/content/ComponentName;)V
    .locals 0

    iget-object p0, p0, Lmiui/autoinstall/config/pm/PackageManagerCompat$1;->this$0:Lmiui/autoinstall/config/pm/PackageManagerCompat;

    const/4 p1, 0x0

    invoke-static {p0, p1}, Lmiui/autoinstall/config/pm/PackageManagerCompat;->access$002(Lmiui/autoinstall/config/pm/PackageManagerCompat;Lb/b/a/a;)Lb/b/a/a;

    return-void
.end method
