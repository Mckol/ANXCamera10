.class public Lmiui/animation/base/AnimConfig;
.super Ljava/lang/Object;
.source "AnimConfig.java"


# static fields
.field public static final TINT_ALPHA:I = 0x0

.field public static final TINT_CANVAS:I = 0x1

.field public static final TINT_OPAQUE:I = 0x1

.field public static final TINT_USE_LAYER:I


# instance fields
.field public delay:J

.field public ease:Lmiui/animation/utils/EaseManager$EaseStyle;

.field public effectFromValue:D

.field public flags:J

.field public fromSpeed:F

.field public listeners:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet<",
            "Lmiui/animation/listener/TransitionListener;",
            ">;"
        }
    .end annotation
.end field

.field public minDuration:J

.field public relatedProperty:[Lmiui/animation/property/FloatProperty;

.field public tag:Ljava/lang/Object;

.field public tintMode:I


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const v0, 0x7f7fffff    # Float.MAX_VALUE

    iput v0, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    return-void
.end method

.method public constructor <init>(Lmiui/animation/base/AnimConfig;)V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const v0, 0x7f7fffff    # Float.MAX_VALUE

    iput v0, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    if-eqz p1, :cond_0

    iget-wide v0, p1, Lmiui/animation/base/AnimConfig;->delay:J

    iput-wide v0, p0, Lmiui/animation/base/AnimConfig;->delay:J

    iget-object v0, p1, Lmiui/animation/base/AnimConfig;->ease:Lmiui/animation/utils/EaseManager$EaseStyle;

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->ease:Lmiui/animation/utils/EaseManager$EaseStyle;

    iget-object v0, p1, Lmiui/animation/base/AnimConfig;->relatedProperty:[Lmiui/animation/property/FloatProperty;

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->relatedProperty:[Lmiui/animation/property/FloatProperty;

    iget-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    iget-object v1, p1, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    invoke-virtual {v0, v1}, Ljava/util/HashSet;->addAll(Ljava/util/Collection;)Z

    iget-object v0, p1, Lmiui/animation/base/AnimConfig;->tag:Ljava/lang/Object;

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->tag:Ljava/lang/Object;

    iget-wide v0, p1, Lmiui/animation/base/AnimConfig;->flags:J

    iput-wide v0, p0, Lmiui/animation/base/AnimConfig;->flags:J

    iget v0, p1, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    iput v0, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    iget-wide v0, p1, Lmiui/animation/base/AnimConfig;->minDuration:J

    iput-wide v0, p0, Lmiui/animation/base/AnimConfig;->minDuration:J

    iget v0, p1, Lmiui/animation/base/AnimConfig;->tintMode:I

    iput v0, p0, Lmiui/animation/base/AnimConfig;->tintMode:I

    iget-wide v0, p1, Lmiui/animation/base/AnimConfig;->effectFromValue:D

    iput-wide v0, p0, Lmiui/animation/base/AnimConfig;->effectFromValue:D

    :cond_0
    return-void
.end method

.method public constructor <init>(Lmiui/animation/property/FloatProperty;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const v0, 0x7f7fffff    # Float.MAX_VALUE

    iput v0, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    invoke-virtual {p0, p1}, Lmiui/animation/base/AnimConfig;->setProperty(Lmiui/animation/property/FloatProperty;)V

    return-void
.end method

.method public varargs constructor <init>([Ljava/lang/String;)V
    .locals 4

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const v0, 0x7f7fffff    # Float.MAX_VALUE

    iput v0, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    array-length v0, p1

    new-array v0, v0, [Lmiui/animation/property/ValueProperty;

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->relatedProperty:[Lmiui/animation/property/FloatProperty;

    const/4 v0, 0x0

    :goto_0
    array-length v1, p1

    if-ge v0, v1, :cond_0

    iget-object v1, p0, Lmiui/animation/base/AnimConfig;->relatedProperty:[Lmiui/animation/property/FloatProperty;

    new-instance v2, Lmiui/animation/property/ValueProperty;

    aget-object v3, p1, v0

    invoke-direct {v2, v3}, Lmiui/animation/property/ValueProperty;-><init>(Ljava/lang/String;)V

    aput-object v2, v1, v0

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_0
    return-void
.end method

.method public varargs constructor <init>([Lmiui/animation/property/FloatProperty;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const v0, 0x7f7fffff    # Float.MAX_VALUE

    iput v0, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    iput-object p1, p0, Lmiui/animation/base/AnimConfig;->relatedProperty:[Lmiui/animation/property/FloatProperty;

    return-void
.end method

.method public static varargs forProperty([Ljava/lang/String;)Lmiui/animation/base/AnimConfig;
    .locals 1

    new-instance v0, Lmiui/animation/base/AnimConfig;

    invoke-direct {v0, p0}, Lmiui/animation/base/AnimConfig;-><init>([Ljava/lang/String;)V

    return-object v0
.end method

.method public static varargs forProperty([Lmiui/animation/property/FloatProperty;)Lmiui/animation/base/AnimConfig;
    .locals 1

    new-instance v0, Lmiui/animation/base/AnimConfig;

    invoke-direct {v0, p0}, Lmiui/animation/base/AnimConfig;-><init>([Lmiui/animation/property/FloatProperty;)V

    return-object v0
.end method


# virtual methods
.method public varargs addListeners([Lmiui/animation/listener/TransitionListener;)Lmiui/animation/base/AnimConfig;
    .locals 1

    iget-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    invoke-static {v0, p1}, Ljava/util/Collections;->addAll(Ljava/util/Collection;[Ljava/lang/Object;)Z

    return-object p0
.end method

.method public varargs removeListeners([Lmiui/animation/listener/TransitionListener;)Lmiui/animation/base/AnimConfig;
    .locals 1

    array-length v0, p1

    if-nez v0, :cond_0

    iget-object p1, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    invoke-virtual {p1}, Ljava/util/HashSet;->clear()V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    invoke-static {p1}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->removeAll(Ljava/util/Collection;)Z

    :goto_0
    return-object p0
.end method

.method public setDelay(J)Lmiui/animation/base/AnimConfig;
    .locals 0

    iput-wide p1, p0, Lmiui/animation/base/AnimConfig;->delay:J

    return-object p0
.end method

.method public varargs setEase(I[F)Lmiui/animation/base/AnimConfig;
    .locals 0

    invoke-static {p1, p2}, Lmiui/animation/utils/EaseManager;->getStyle(I[F)Lmiui/animation/utils/EaseManager$EaseStyle;

    move-result-object p1

    iput-object p1, p0, Lmiui/animation/base/AnimConfig;->ease:Lmiui/animation/utils/EaseManager$EaseStyle;

    return-object p0
.end method

.method public setEase(Lmiui/animation/utils/EaseManager$EaseStyle;)Lmiui/animation/base/AnimConfig;
    .locals 0

    iput-object p1, p0, Lmiui/animation/base/AnimConfig;->ease:Lmiui/animation/utils/EaseManager$EaseStyle;

    return-object p0
.end method

.method public setEffectFromValue(D)Lmiui/animation/base/AnimConfig;
    .locals 0

    iput-wide p1, p0, Lmiui/animation/base/AnimConfig;->effectFromValue:D

    return-object p0
.end method

.method public setFromSpeed(F)Lmiui/animation/base/AnimConfig;
    .locals 0

    iput p1, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    return-object p0
.end method

.method public setMinDuration(J)Lmiui/animation/base/AnimConfig;
    .locals 0

    iput-wide p1, p0, Lmiui/animation/base/AnimConfig;->minDuration:J

    return-object p0
.end method

.method public final setProperty(Lmiui/animation/property/FloatProperty;)V
    .locals 2

    const/4 v0, 0x1

    new-array v0, v0, [Lmiui/animation/property/FloatProperty;

    const/4 v1, 0x0

    aput-object p1, v0, v1

    iput-object v0, p0, Lmiui/animation/base/AnimConfig;->relatedProperty:[Lmiui/animation/property/FloatProperty;

    return-void
.end method

.method public setTag(Ljava/lang/Object;)Lmiui/animation/base/AnimConfig;
    .locals 0

    iput-object p1, p0, Lmiui/animation/base/AnimConfig;->tag:Ljava/lang/Object;

    return-object p0
.end method

.method public setTintMethod(I)Lmiui/animation/base/AnimConfig;
    .locals 0

    return-object p0
.end method

.method public setTintMode(I)Lmiui/animation/base/AnimConfig;
    .locals 0

    iput p1, p0, Lmiui/animation/base/AnimConfig;->tintMode:I

    return-object p0
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "AnimConfig{, delay="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v1, p0, Lmiui/animation/base/AnimConfig;->delay:J

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v1, ", minDuration = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v1, p0, Lmiui/animation/base/AnimConfig;->minDuration:J

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v1, ", fromSpeed = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lmiui/animation/base/AnimConfig;->fromSpeed:F

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, ", ease="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lmiui/animation/base/AnimConfig;->ease:Lmiui/animation/utils/EaseManager$EaseStyle;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", relatedProperty="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lmiui/animation/base/AnimConfig;->relatedProperty:[Lmiui/animation/property/FloatProperty;

    invoke-static {v1}, Ljava/util/Arrays;->toString([Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, ", tag = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lmiui/animation/base/AnimConfig;->tag:Ljava/lang/Object;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", listeners = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p0, p0, Lmiui/animation/base/AnimConfig;->listeners:Ljava/util/HashSet;

    invoke-virtual {p0}, Ljava/util/HashSet;->toArray()[Ljava/lang/Object;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->toString([Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const/16 p0, 0x7d

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
