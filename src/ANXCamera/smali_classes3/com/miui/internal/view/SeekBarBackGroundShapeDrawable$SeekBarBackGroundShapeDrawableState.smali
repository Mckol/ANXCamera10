.class public Lcom/miui/internal/view/SeekBarBackGroundShapeDrawable$SeekBarBackGroundShapeDrawableState;
.super Lcom/miui/internal/view/SeekBarGradientDrawable$SeekBarGradientState;
.source "SeekBarBackGroundShapeDrawable.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/view/SeekBarBackGroundShapeDrawable;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xc
    name = "SeekBarBackGroundShapeDrawableState"
.end annotation


# direct methods
.method protected constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/miui/internal/view/SeekBarGradientDrawable$SeekBarGradientState;-><init>()V

    return-void
.end method


# virtual methods
.method protected newSeekBarGradientDrawable(Landroid/content/res/Resources;Landroid/content/res/Resources$Theme;Lcom/miui/internal/view/SeekBarGradientDrawable$SeekBarGradientState;)Landroid/graphics/drawable/Drawable;
    .locals 0

    new-instance p0, Lcom/miui/internal/view/SeekBarBackGroundShapeDrawable;

    invoke-direct {p0, p1, p2, p3}, Lcom/miui/internal/view/SeekBarBackGroundShapeDrawable;-><init>(Landroid/content/res/Resources;Landroid/content/res/Resources$Theme;Lcom/miui/internal/view/SeekBarGradientDrawable$SeekBarGradientState;)V

    return-object p0
.end method
