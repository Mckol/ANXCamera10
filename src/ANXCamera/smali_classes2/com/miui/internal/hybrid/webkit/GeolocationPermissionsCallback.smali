.class public Lcom/miui/internal/hybrid/webkit/GeolocationPermissionsCallback;
.super Ljava/lang/Object;
.source "GeolocationPermissionsCallback.java"

# interfaces
.implements Lmiui/hybrid/GeolocationPermissions$Callback;


# instance fields
.field private mCallback:Landroid/webkit/GeolocationPermissions$Callback;


# direct methods
.method public constructor <init>(Landroid/webkit/GeolocationPermissions$Callback;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/miui/internal/hybrid/webkit/GeolocationPermissionsCallback;->mCallback:Landroid/webkit/GeolocationPermissions$Callback;

    return-void
.end method


# virtual methods
.method public invoke(Ljava/lang/String;ZZ)V
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/hybrid/webkit/GeolocationPermissionsCallback;->mCallback:Landroid/webkit/GeolocationPermissions$Callback;

    invoke-interface {p0, p1, p2, p3}, Landroid/webkit/GeolocationPermissions$Callback;->invoke(Ljava/lang/String;ZZ)V

    return-void
.end method
