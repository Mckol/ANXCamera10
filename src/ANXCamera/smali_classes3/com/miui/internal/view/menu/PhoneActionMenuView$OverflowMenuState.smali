.class final enum Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;
.super Ljava/lang/Enum;
.source "PhoneActionMenuView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/view/menu/PhoneActionMenuView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x401a
    name = "OverflowMenuState"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

.field public static final enum Collapsed:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

.field public static final enum Collapsing:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

.field public static final enum Expanded:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

.field public static final enum Expanding:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    new-instance v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    const/4 v1, 0x0

    const-string v2, "Collapsed"

    invoke-direct {v0, v2, v1}, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Collapsed:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    new-instance v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    const/4 v2, 0x1

    const-string v3, "Expanding"

    invoke-direct {v0, v3, v2}, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Expanding:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    new-instance v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    const/4 v3, 0x2

    const-string v4, "Expanded"

    invoke-direct {v0, v4, v3}, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Expanded:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    new-instance v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    const/4 v4, 0x3

    const-string v5, "Collapsing"

    invoke-direct {v0, v5, v4}, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Collapsing:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    const/4 v0, 0x4

    new-array v0, v0, [Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    sget-object v5, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Collapsed:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    aput-object v5, v0, v1

    sget-object v1, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Expanding:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    aput-object v1, v0, v2

    sget-object v1, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Expanded:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    aput-object v1, v0, v3

    sget-object v1, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->Collapsing:Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    aput-object v1, v0, v4

    sput-object v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->$VALUES:[Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

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

.method public static valueOf(Ljava/lang/String;)Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;
    .locals 1

    const-class v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    return-object p0
.end method

.method public static values()[Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;
    .locals 1

    sget-object v0, Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->$VALUES:[Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    invoke-virtual {v0}, [Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/miui/internal/view/menu/PhoneActionMenuView$OverflowMenuState;

    return-object v0
.end method
