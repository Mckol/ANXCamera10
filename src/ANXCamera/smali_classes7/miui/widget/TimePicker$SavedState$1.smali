.class Lmiui/widget/TimePicker$SavedState$1;
.super Ljava/lang/Object;
.source "TimePicker.java"

# interfaces
.implements Landroid/os/Parcelable$Creator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/widget/TimePicker$SavedState;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Landroid/os/Parcelable$Creator<",
        "Lmiui/widget/TimePicker$SavedState;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public bridge synthetic createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1}, Lmiui/widget/TimePicker$SavedState$1;->createFromParcel(Landroid/os/Parcel;)Lmiui/widget/TimePicker$SavedState;

    move-result-object p0

    return-object p0
.end method

.method public createFromParcel(Landroid/os/Parcel;)Lmiui/widget/TimePicker$SavedState;
    .locals 1

    new-instance p0, Lmiui/widget/TimePicker$SavedState;

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lmiui/widget/TimePicker$SavedState;-><init>(Landroid/os/Parcel;Lmiui/widget/TimePicker$1;)V

    return-object p0
.end method

.method public bridge synthetic newArray(I)[Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1}, Lmiui/widget/TimePicker$SavedState$1;->newArray(I)[Lmiui/widget/TimePicker$SavedState;

    move-result-object p0

    return-object p0
.end method

.method public newArray(I)[Lmiui/widget/TimePicker$SavedState;
    .locals 0

    new-array p0, p1, [Lmiui/widget/TimePicker$SavedState;

    return-object p0
.end method
