.class public final enum Lmiuix/animation/ITouchStyle$TouchType;
.super Ljava/lang/Enum;
.source "ITouchStyle.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiuix/animation/ITouchStyle;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "TouchType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lmiuix/animation/ITouchStyle$TouchType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lmiuix/animation/ITouchStyle$TouchType;

.field public static final enum DOWN:Lmiuix/animation/ITouchStyle$TouchType;

.field public static final enum UP:Lmiuix/animation/ITouchStyle$TouchType;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    new-instance v0, Lmiuix/animation/ITouchStyle$TouchType;

    const/4 v1, 0x0

    const-string v2, "UP"

    invoke-direct {v0, v2, v1}, Lmiuix/animation/ITouchStyle$TouchType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiuix/animation/ITouchStyle$TouchType;->UP:Lmiuix/animation/ITouchStyle$TouchType;

    new-instance v0, Lmiuix/animation/ITouchStyle$TouchType;

    const/4 v2, 0x1

    const-string v3, "DOWN"

    invoke-direct {v0, v3, v2}, Lmiuix/animation/ITouchStyle$TouchType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiuix/animation/ITouchStyle$TouchType;->DOWN:Lmiuix/animation/ITouchStyle$TouchType;

    const/4 v0, 0x2

    new-array v0, v0, [Lmiuix/animation/ITouchStyle$TouchType;

    sget-object v3, Lmiuix/animation/ITouchStyle$TouchType;->UP:Lmiuix/animation/ITouchStyle$TouchType;

    aput-object v3, v0, v1

    sget-object v1, Lmiuix/animation/ITouchStyle$TouchType;->DOWN:Lmiuix/animation/ITouchStyle$TouchType;

    aput-object v1, v0, v2

    sput-object v0, Lmiuix/animation/ITouchStyle$TouchType;->$VALUES:[Lmiuix/animation/ITouchStyle$TouchType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lmiuix/animation/ITouchStyle$TouchType;
    .locals 1

    const-class v0, Lmiuix/animation/ITouchStyle$TouchType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lmiuix/animation/ITouchStyle$TouchType;

    return-object p0
.end method

.method public static values()[Lmiuix/animation/ITouchStyle$TouchType;
    .locals 1

    sget-object v0, Lmiuix/animation/ITouchStyle$TouchType;->$VALUES:[Lmiuix/animation/ITouchStyle$TouchType;

    invoke-virtual {v0}, [Lmiuix/animation/ITouchStyle$TouchType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lmiuix/animation/ITouchStyle$TouchType;

    return-object v0
.end method
