.class public Lcom/miui/internal/webkit/DeviceAccountLogin;
.super Ljava/lang/Object;
.source "DeviceAccountLogin.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/miui/internal/webkit/DeviceAccountLogin$LoginCallback;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "DeviceAccountLogin"


# instance fields
.field protected mAccountManager:Landroid/accounts/AccountManager;

.field protected mActivity:Landroid/app/Activity;

.field private mCallback:Landroid/accounts/AccountManagerCallback;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/accounts/AccountManagerCallback<",
            "Landroid/os/Bundle;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Landroid/app/Activity;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/miui/internal/webkit/DeviceAccountLogin;->mActivity:Landroid/app/Activity;

    iget-object p1, p0, Lcom/miui/internal/webkit/DeviceAccountLogin;->mActivity:Landroid/app/Activity;

    invoke-virtual {p1}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    invoke-static {p1}, Landroid/accounts/AccountManager;->get(Landroid/content/Context;)Landroid/accounts/AccountManager;

    move-result-object p1

    iput-object p1, p0, Lcom/miui/internal/webkit/DeviceAccountLogin;->mAccountManager:Landroid/accounts/AccountManager;

    new-instance p1, Lcom/miui/internal/webkit/DeviceAccountLogin$LoginCallback;

    invoke-direct {p1, p0}, Lcom/miui/internal/webkit/DeviceAccountLogin$LoginCallback;-><init>(Lcom/miui/internal/webkit/DeviceAccountLogin;)V

    iput-object p1, p0, Lcom/miui/internal/webkit/DeviceAccountLogin;->mCallback:Landroid/accounts/AccountManagerCallback;

    return-void
.end method


# virtual methods
.method public login(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 10

    iget-object v0, p0, Lcom/miui/internal/webkit/DeviceAccountLogin;->mAccountManager:Landroid/accounts/AccountManager;

    invoke-virtual {v0, p1}, Landroid/accounts/AccountManager;->getAccountsByType(Ljava/lang/String;)[Landroid/accounts/Account;

    move-result-object p1

    array-length v0, p1

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/miui/internal/webkit/DeviceAccountLogin;->onLoginCancel()V

    return-void

    :cond_0
    const/4 v0, 0x0

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    const/4 v2, 0x0

    if-eqz v1, :cond_2

    aget-object v0, p1, v2

    :cond_1
    move-object v4, v0

    goto :goto_1

    :cond_2
    array-length v1, p1

    :goto_0
    if-ge v2, v1, :cond_1

    aget-object v3, p1, v2

    iget-object v4, v3, Landroid/accounts/Account;->name:Ljava/lang/String;

    invoke-virtual {v4, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3

    move-object v4, v3

    goto :goto_1

    :cond_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :goto_1
    if-eqz v4, :cond_4

    invoke-virtual {p0}, Lcom/miui/internal/webkit/DeviceAccountLogin;->onLoginStart()V

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "weblogin:"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    iget-object v3, p0, Lcom/miui/internal/webkit/DeviceAccountLogin;->mAccountManager:Landroid/accounts/AccountManager;

    const/4 v6, 0x0

    const/4 v7, 0x0

    iget-object v8, p0, Lcom/miui/internal/webkit/DeviceAccountLogin;->mCallback:Landroid/accounts/AccountManagerCallback;

    const/4 v9, 0x0

    invoke-virtual/range {v3 .. v9}, Landroid/accounts/AccountManager;->getAuthToken(Landroid/accounts/Account;Ljava/lang/String;Landroid/os/Bundle;Landroid/app/Activity;Landroid/accounts/AccountManagerCallback;Landroid/os/Handler;)Landroid/accounts/AccountManagerFuture;

    goto :goto_2

    :cond_4
    invoke-virtual {p0}, Lcom/miui/internal/webkit/DeviceAccountLogin;->onLoginCancel()V

    :goto_2
    return-void
.end method

.method public onLoginCancel()V
    .locals 0

    return-void
.end method

.method public onLoginFail()V
    .locals 0

    return-void
.end method

.method public onLoginPageFinished()V
    .locals 0

    return-void
.end method

.method public onLoginStart()V
    .locals 0

    return-void
.end method

.method public onLoginSuccess(Ljava/lang/String;)V
    .locals 0

    return-void
.end method
