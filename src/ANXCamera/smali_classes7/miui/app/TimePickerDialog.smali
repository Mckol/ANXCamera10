.class public Lmiui/app/TimePickerDialog;
.super Landroid/app/AlertDialog;
.source "TimePickerDialog.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lmiui/app/TimePickerDialog$OnTimeSetListener;
    }
.end annotation


# static fields
.field private static final HOUR:Ljava/lang/String; = "miui:hour"

.field private static final IS_24_HOUR:Ljava/lang/String; = "miui:is24hour"

.field private static final MINUTE:Ljava/lang/String; = "miui:minute"


# instance fields
.field private final mCallback:Lmiui/app/TimePickerDialog$OnTimeSetListener;

.field mInitialHourOfDay:I

.field mInitialMinute:I

.field mIs24HourView:Z

.field private final mTimePicker:Lmiui/widget/TimePicker;


# direct methods
.method public constructor <init>(Landroid/content/Context;ILmiui/app/TimePickerDialog$OnTimeSetListener;IIZ)V
    .locals 0

    invoke-direct {p0, p1, p2}, Landroid/app/AlertDialog;-><init>(Landroid/content/Context;I)V

    iput-object p3, p0, Lmiui/app/TimePickerDialog;->mCallback:Lmiui/app/TimePickerDialog$OnTimeSetListener;

    iput p4, p0, Lmiui/app/TimePickerDialog;->mInitialHourOfDay:I

    iput p5, p0, Lmiui/app/TimePickerDialog;->mInitialMinute:I

    iput-boolean p6, p0, Lmiui/app/TimePickerDialog;->mIs24HourView:Z

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Landroid/app/AlertDialog;->setIcon(I)V

    sget p1, Lcom/miui/internal/R$string;->time_picker_dialog_title:I

    invoke-virtual {p0, p1}, Landroid/app/AlertDialog;->setTitle(I)V

    invoke-virtual {p0}, Landroid/app/AlertDialog;->getContext()Landroid/content/Context;

    move-result-object p1

    const p2, 0x104000a

    invoke-virtual {p1, p2}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object p2

    new-instance p3, Lmiui/app/TimePickerDialog$1;

    invoke-direct {p3, p0}, Lmiui/app/TimePickerDialog$1;-><init>(Lmiui/app/TimePickerDialog;)V

    const/4 p4, -0x1

    invoke-virtual {p0, p4, p2, p3}, Landroid/app/AlertDialog;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)V

    invoke-virtual {p0}, Landroid/app/AlertDialog;->getContext()Landroid/content/Context;

    move-result-object p2

    const/high16 p3, 0x1040000

    invoke-virtual {p2, p3}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object p2

    const/4 p3, 0x0

    const/4 p4, -0x2

    invoke-virtual {p0, p4, p2, p3}, Landroid/app/AlertDialog;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)V

    const-string p2, "layout_inflater"

    invoke-virtual {p1, p2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/view/LayoutInflater;

    sget p2, Lcom/miui/internal/R$layout;->time_picker_dialog:I

    invoke-virtual {p1, p2, p3}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p1

    invoke-virtual {p0, p1}, Landroid/app/AlertDialog;->setView(Landroid/view/View;)V

    sget p2, Lcom/miui/internal/R$id;->timePicker:I

    invoke-virtual {p1, p2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lmiui/widget/TimePicker;

    iput-object p1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    iget-object p1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    iget-boolean p2, p0, Lmiui/app/TimePickerDialog;->mIs24HourView:Z

    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p2

    invoke-virtual {p1, p2}, Lmiui/widget/TimePicker;->setIs24HourView(Ljava/lang/Boolean;)V

    iget-object p1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    iget p2, p0, Lmiui/app/TimePickerDialog;->mInitialHourOfDay:I

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    invoke-virtual {p1, p2}, Lmiui/widget/TimePicker;->setCurrentHour(Ljava/lang/Integer;)V

    iget-object p1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    iget p2, p0, Lmiui/app/TimePickerDialog;->mInitialMinute:I

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    invoke-virtual {p1, p2}, Lmiui/widget/TimePicker;->setCurrentMinute(Ljava/lang/Integer;)V

    iget-object p0, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-virtual {p0, p3}, Lmiui/widget/TimePicker;->setOnTimeChangedListener(Lmiui/widget/TimePicker$OnTimeChangedListener;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Lmiui/app/TimePickerDialog$OnTimeSetListener;IIZ)V
    .locals 7

    const/4 v2, 0x0

    move-object v0, p0

    move-object v1, p1

    move-object v3, p2

    move v4, p3

    move v5, p4

    move v6, p5

    invoke-direct/range {v0 .. v6}, Lmiui/app/TimePickerDialog;-><init>(Landroid/content/Context;ILmiui/app/TimePickerDialog$OnTimeSetListener;IIZ)V

    return-void
.end method

.method static synthetic access$000(Lmiui/app/TimePickerDialog;)V
    .locals 0

    invoke-direct {p0}, Lmiui/app/TimePickerDialog;->tryNotifyTimeSet()V

    return-void
.end method

.method private tryNotifyTimeSet()V
    .locals 3

    iget-object v0, p0, Lmiui/app/TimePickerDialog;->mCallback:Lmiui/app/TimePickerDialog$OnTimeSetListener;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-virtual {v0}, Landroid/widget/FrameLayout;->clearFocus()V

    iget-object v0, p0, Lmiui/app/TimePickerDialog;->mCallback:Lmiui/app/TimePickerDialog$OnTimeSetListener;

    iget-object v1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-virtual {v1}, Lmiui/widget/TimePicker;->getCurrentHour()Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v2

    iget-object p0, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-virtual {p0}, Lmiui/widget/TimePicker;->getCurrentMinute()Ljava/lang/Integer;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    invoke-interface {v0, v1, v2, p0}, Lmiui/app/TimePickerDialog$OnTimeSetListener;->onTimeSet(Lmiui/widget/TimePicker;II)V

    :cond_0
    return-void
.end method


# virtual methods
.method public onRestoreInstanceState(Landroid/os/Bundle;)V
    .locals 4

    invoke-super {p0, p1}, Landroid/app/AlertDialog;->onRestoreInstanceState(Landroid/os/Bundle;)V

    const-string v0, "miui:hour"

    invoke-virtual {p1, v0}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result v0

    const-string v1, "miui:minute"

    invoke-virtual {p1, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result v1

    iget-object v2, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    const-string v3, "miui:is24hour"

    invoke-virtual {p1, v3}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;)Z

    move-result p1

    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p1

    invoke-virtual {v2, p1}, Lmiui/widget/TimePicker;->setIs24HourView(Ljava/lang/Boolean;)V

    iget-object p1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p1, v0}, Lmiui/widget/TimePicker;->setCurrentHour(Ljava/lang/Integer;)V

    iget-object p0, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, p1}, Lmiui/widget/TimePicker;->setCurrentMinute(Ljava/lang/Integer;)V

    return-void
.end method

.method public onSaveInstanceState()Landroid/os/Bundle;
    .locals 3

    invoke-super {p0}, Landroid/app/AlertDialog;->onSaveInstanceState()Landroid/os/Bundle;

    move-result-object v0

    iget-object v1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-virtual {v1}, Lmiui/widget/TimePicker;->getCurrentHour()Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    const-string v2, "miui:hour"

    invoke-virtual {v0, v2, v1}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    iget-object v1, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-virtual {v1}, Lmiui/widget/TimePicker;->getCurrentMinute()Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    const-string v2, "miui:minute"

    invoke-virtual {v0, v2, v1}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    iget-object p0, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-virtual {p0}, Lmiui/widget/TimePicker;->is24HourView()Z

    move-result p0

    const-string v1, "miui:is24hour"

    invoke-virtual {v0, v1, p0}, Landroid/os/Bundle;->putBoolean(Ljava/lang/String;Z)V

    return-object v0
.end method

.method public updateTime(II)V
    .locals 1

    iget-object v0, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {v0, p1}, Lmiui/widget/TimePicker;->setCurrentHour(Ljava/lang/Integer;)V

    iget-object p0, p0, Lmiui/app/TimePickerDialog;->mTimePicker:Lmiui/widget/TimePicker;

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, p1}, Lmiui/widget/TimePicker;->setCurrentMinute(Ljava/lang/Integer;)V

    return-void
.end method
