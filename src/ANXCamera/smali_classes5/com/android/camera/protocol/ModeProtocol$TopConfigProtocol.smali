.class public interface abstract Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;
.super Ljava/lang/Object;
.source "ModeProtocol.java"

# interfaces
.implements Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/protocol/ModeProtocol;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x609
    name = "TopConfigProtocol"
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xc1


# virtual methods
.method public getAutoHDRTargetState()Z
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method public onExtraMenuVisibilityChange(Z)V
    .locals 0

    return-void
.end method

.method public abstract reShowMoon()V
.end method

.method public abstract startAiLens()V
.end method
