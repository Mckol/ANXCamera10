.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/edit/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

.field private final synthetic ug:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;

.field private final synthetic vg:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/a;->tg:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/a;->ug:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;

    iput p3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/a;->vg:I

    return-void
.end method


# virtual methods
.method public final OnRecyclerItemClickListener(Ljava/lang/Object;ILandroid/view/View;)V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/a;->tg:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/a;->ug:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;

    iget v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/a;->vg:I

    move-object v3, p1

    check-cast v3, Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;

    move v4, p2

    move-object v5, p3

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->a(Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;ILcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;ILandroid/view/View;)V

    return-void
.end method
