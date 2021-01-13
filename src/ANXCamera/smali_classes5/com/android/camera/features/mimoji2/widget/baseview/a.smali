.class public final synthetic Lcom/android/camera/features/mimoji2/widget/baseview/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;

.field private final synthetic ug:Ljava/lang/Object;

.field private final synthetic vg:I

.field private final synthetic wg:Landroid/view/View;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;Ljava/lang/Object;ILandroid/view/View;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->tg:Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->ug:Ljava/lang/Object;

    iput p3, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->vg:I

    iput-object p4, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->wg:Landroid/view/View;

    return-void
.end method


# virtual methods
.method public final onClick(Landroid/view/View;)V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->tg:Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->ug:Ljava/lang/Object;

    iget v2, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->vg:I

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/a;->wg:Landroid/view/View;

    invoke-static {v0, v1, v2, p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;->a(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;Ljava/lang/Object;ILandroid/view/View;Landroid/view/View;)V

    return-void
.end method
