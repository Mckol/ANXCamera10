.class public Lcom/android/camera/dualvideo/UserSelectData;
.super Ljava/lang/Object;
.source "UserSelectData.java"

# interfaces
.implements Ljava/lang/Comparable;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljava/lang/Comparable<",
        "Lcom/android/camera/dualvideo/UserSelectData;",
        ">;"
    }
.end annotation


# instance fields
.field public m6PatchType:I

.field public mCameraID:I

.field private mIndex:I

.field public mPreviewType:I


# direct methods
.method public constructor <init>(IIII)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/android/camera/dualvideo/UserSelectData;->mIndex:I

    iput p2, p0, Lcom/android/camera/dualvideo/UserSelectData;->mCameraID:I

    iput p3, p0, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    iput p4, p0, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    return-void
.end method


# virtual methods
.method public compareTo(Lcom/android/camera/dualvideo/UserSelectData;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    invoke-static {p0, p1}, Ljava/lang/Integer;->compare(II)I

    move-result p0

    return p0
.end method

.method public bridge synthetic compareTo(Ljava/lang/Object;)I
    .locals 0

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/UserSelectData;->compareTo(Lcom/android/camera/dualvideo/UserSelectData;)I

    move-result p0

    return p0
.end method

.method public getIndex()I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->mIndex:I

    return p0
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "ChooseData{mSelectedIndex="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/dualvideo/UserSelectData;->mIndex:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", mCameraID="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/dualvideo/UserSelectData;->mCameraID:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", mSelected6PatchType="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", mSelectedPreviewType="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const/16 p0, 0x7d

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
