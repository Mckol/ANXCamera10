.class public final enum Lcom/android/camera/customization/TintShapeView$ShapeType;
.super Ljava/lang/Enum;
.source "TintShapeView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/customization/TintShapeView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ShapeType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/camera/customization/TintShapeView$ShapeType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/android/camera/customization/TintShapeView$ShapeType;

.field public static final enum CIRCLE:Lcom/android/camera/customization/TintShapeView$ShapeType;

.field public static final enum RECTANGLE:Lcom/android/camera/customization/TintShapeView$ShapeType;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    new-instance v0, Lcom/android/camera/customization/TintShapeView$ShapeType;

    const/4 v1, 0x0

    const-string v2, "CIRCLE"

    invoke-direct {v0, v2, v1}, Lcom/android/camera/customization/TintShapeView$ShapeType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/android/camera/customization/TintShapeView$ShapeType;->CIRCLE:Lcom/android/camera/customization/TintShapeView$ShapeType;

    new-instance v0, Lcom/android/camera/customization/TintShapeView$ShapeType;

    const/4 v2, 0x1

    const-string v3, "RECTANGLE"

    invoke-direct {v0, v3, v2}, Lcom/android/camera/customization/TintShapeView$ShapeType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/android/camera/customization/TintShapeView$ShapeType;->RECTANGLE:Lcom/android/camera/customization/TintShapeView$ShapeType;

    const/4 v0, 0x2

    new-array v0, v0, [Lcom/android/camera/customization/TintShapeView$ShapeType;

    sget-object v3, Lcom/android/camera/customization/TintShapeView$ShapeType;->CIRCLE:Lcom/android/camera/customization/TintShapeView$ShapeType;

    aput-object v3, v0, v1

    sget-object v1, Lcom/android/camera/customization/TintShapeView$ShapeType;->RECTANGLE:Lcom/android/camera/customization/TintShapeView$ShapeType;

    aput-object v1, v0, v2

    sput-object v0, Lcom/android/camera/customization/TintShapeView$ShapeType;->$VALUES:[Lcom/android/camera/customization/TintShapeView$ShapeType;

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

.method public static valueOf(Ljava/lang/String;)Lcom/android/camera/customization/TintShapeView$ShapeType;
    .locals 1

    const-class v0, Lcom/android/camera/customization/TintShapeView$ShapeType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/TintShapeView$ShapeType;

    return-object p0
.end method

.method public static values()[Lcom/android/camera/customization/TintShapeView$ShapeType;
    .locals 1

    sget-object v0, Lcom/android/camera/customization/TintShapeView$ShapeType;->$VALUES:[Lcom/android/camera/customization/TintShapeView$ShapeType;

    invoke-virtual {v0}, [Lcom/android/camera/customization/TintShapeView$ShapeType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/android/camera/customization/TintShapeView$ShapeType;

    return-object v0
.end method
