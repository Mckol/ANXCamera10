.class Lmiui/widget/DropDownSingleChoiceMenu$3;
.super Ljava/lang/Object;
.source "DropDownSingleChoiceMenu.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lmiui/widget/DropDownSingleChoiceMenu;->show()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lmiui/widget/DropDownSingleChoiceMenu;


# direct methods
.method constructor <init>(Lmiui/widget/DropDownSingleChoiceMenu;)V
    .locals 0

    iput-object p1, p0, Lmiui/widget/DropDownSingleChoiceMenu$3;->this$0:Lmiui/widget/DropDownSingleChoiceMenu;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView<",
            "*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    iget-object p1, p0, Lmiui/widget/DropDownSingleChoiceMenu$3;->this$0:Lmiui/widget/DropDownSingleChoiceMenu;

    invoke-static {p1, p3}, Lmiui/widget/DropDownSingleChoiceMenu;->access$202(Lmiui/widget/DropDownSingleChoiceMenu;I)I

    iget-object p1, p0, Lmiui/widget/DropDownSingleChoiceMenu$3;->this$0:Lmiui/widget/DropDownSingleChoiceMenu;

    invoke-static {p1}, Lmiui/widget/DropDownSingleChoiceMenu;->access$000(Lmiui/widget/DropDownSingleChoiceMenu;)Lmiui/widget/DropDownSingleChoiceMenu$OnMenuListener;

    move-result-object p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lmiui/widget/DropDownSingleChoiceMenu$3;->this$0:Lmiui/widget/DropDownSingleChoiceMenu;

    invoke-static {p1}, Lmiui/widget/DropDownSingleChoiceMenu;->access$000(Lmiui/widget/DropDownSingleChoiceMenu;)Lmiui/widget/DropDownSingleChoiceMenu$OnMenuListener;

    move-result-object p1

    iget-object p2, p0, Lmiui/widget/DropDownSingleChoiceMenu$3;->this$0:Lmiui/widget/DropDownSingleChoiceMenu;

    invoke-interface {p1, p2, p3}, Lmiui/widget/DropDownSingleChoiceMenu$OnMenuListener;->onItemSelected(Lmiui/widget/DropDownSingleChoiceMenu;I)V

    :cond_0
    iget-object p0, p0, Lmiui/widget/DropDownSingleChoiceMenu$3;->this$0:Lmiui/widget/DropDownSingleChoiceMenu;

    invoke-virtual {p0}, Lmiui/widget/DropDownSingleChoiceMenu;->dismiss()V

    return-void
.end method
