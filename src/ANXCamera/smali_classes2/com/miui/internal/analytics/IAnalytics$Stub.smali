.class public abstract Lcom/miui/internal/analytics/IAnalytics$Stub;
.super Landroid/os/Binder;
.source "IAnalytics.java"

# interfaces
.implements Lcom/miui/internal/analytics/IAnalytics;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/analytics/IAnalytics;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x409
    name = "Stub"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/miui/internal/analytics/IAnalytics$Stub$Proxy;
    }
.end annotation


# static fields
.field private static final DESCRIPTOR:Ljava/lang/String; = "com.miui.internal.analytics.IAnalytics"

.field static final TRANSACTION_track:I = 0x1


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Landroid/os/Binder;-><init>()V

    const-string v0, "com.miui.internal.analytics.IAnalytics"

    invoke-virtual {p0, p0, v0}, Landroid/os/Binder;->attachInterface(Landroid/os/IInterface;Ljava/lang/String;)V

    return-void
.end method

.method public static asInterface(Landroid/os/IBinder;)Lcom/miui/internal/analytics/IAnalytics;
    .locals 2

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    const-string v0, "com.miui.internal.analytics.IAnalytics"

    invoke-interface {p0, v0}, Landroid/os/IBinder;->queryLocalInterface(Ljava/lang/String;)Landroid/os/IInterface;

    move-result-object v0

    if-eqz v0, :cond_1

    instance-of v1, v0, Lcom/miui/internal/analytics/IAnalytics;

    if-eqz v1, :cond_1

    check-cast v0, Lcom/miui/internal/analytics/IAnalytics;

    return-object v0

    :cond_1
    new-instance v0, Lcom/miui/internal/analytics/IAnalytics$Stub$Proxy;

    invoke-direct {v0, p0}, Lcom/miui/internal/analytics/IAnalytics$Stub$Proxy;-><init>(Landroid/os/IBinder;)V

    return-object v0
.end method

.method public static getDefaultImpl()Lcom/miui/internal/analytics/IAnalytics;
    .locals 1

    sget-object v0, Lcom/miui/internal/analytics/IAnalytics$Stub$Proxy;->sDefaultImpl:Lcom/miui/internal/analytics/IAnalytics;

    return-object v0
.end method

.method public static setDefaultImpl(Lcom/miui/internal/analytics/IAnalytics;)Z
    .locals 1

    sget-object v0, Lcom/miui/internal/analytics/IAnalytics$Stub$Proxy;->sDefaultImpl:Lcom/miui/internal/analytics/IAnalytics;

    if-nez v0, :cond_0

    if-eqz p0, :cond_0

    sput-object p0, Lcom/miui/internal/analytics/IAnalytics$Stub$Proxy;->sDefaultImpl:Lcom/miui/internal/analytics/IAnalytics;

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method


# virtual methods
.method public asBinder()Landroid/os/IBinder;
    .locals 0

    return-object p0
.end method

.method public onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    const/4 v0, 0x1

    const-string v1, "com.miui.internal.analytics.IAnalytics"

    if-eq p1, v0, :cond_1

    const v2, 0x5f4e5446

    if-eq p1, v2, :cond_0

    invoke-super {p0, p1, p2, p3, p4}, Landroid/os/Binder;->onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result p0

    return p0

    :cond_0
    invoke-virtual {p3, v1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return v0

    :cond_1
    invoke-virtual {p2, v1}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    sget-object p1, Lcom/miui/internal/analytics/Event;->CREATOR:Landroid/os/Parcelable$Creator;

    invoke-virtual {p2, p1}, Landroid/os/Parcel;->createTypedArray(Landroid/os/Parcelable$Creator;)[Ljava/lang/Object;

    move-result-object p1

    check-cast p1, [Lcom/miui/internal/analytics/Event;

    invoke-interface {p0, p1}, Lcom/miui/internal/analytics/IAnalytics;->track([Lcom/miui/internal/analytics/Event;)V

    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v0
.end method
