.class public Lcom/miui/internal/view/menu/SubMenuBuilder;
.super Lcom/miui/internal/view/menu/MenuBuilder;
.source "SubMenuBuilder.java"

# interfaces
.implements Landroid/view/SubMenu;


# instance fields
.field private mItem:Lcom/miui/internal/view/menu/MenuItemImpl;

.field private mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/miui/internal/view/menu/MenuBuilder;Lcom/miui/internal/view/menu/MenuItemImpl;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;-><init>(Landroid/content/Context;)V

    iput-object p2, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    iput-object p3, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mItem:Lcom/miui/internal/view/menu/MenuItemImpl;

    return-void
.end method


# virtual methods
.method public clearHeader()V
    .locals 0

    return-void
.end method

.method public collapseItemActionView(Lcom/miui/internal/view/menu/MenuItemImpl;)Z
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->collapseItemActionView(Lcom/miui/internal/view/menu/MenuItemImpl;)Z

    move-result p0

    return p0
.end method

.method public dispatchMenuItemSelected(Lcom/miui/internal/view/menu/MenuBuilder;Landroid/view/MenuItem;)Z
    .locals 1

    invoke-super {p0, p1, p2}, Lcom/miui/internal/view/menu/MenuBuilder;->dispatchMenuItemSelected(Lcom/miui/internal/view/menu/MenuBuilder;Landroid/view/MenuItem;)Z

    move-result v0

    if-nez v0, :cond_1

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0, p1, p2}, Lcom/miui/internal/view/menu/MenuBuilder;->dispatchMenuItemSelected(Lcom/miui/internal/view/menu/MenuBuilder;Landroid/view/MenuItem;)Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public expandItemActionView(Lcom/miui/internal/view/menu/MenuItemImpl;)Z
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->expandItemActionView(Lcom/miui/internal/view/menu/MenuItemImpl;)Z

    move-result p0

    return p0
.end method

.method public getActionViewStatesKey()Ljava/lang/String;
    .locals 2

    iget-object v0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mItem:Lcom/miui/internal/view/menu/MenuItemImpl;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/miui/internal/view/menu/MenuItemImpl;->getItemId()I

    move-result v0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    if-nez v0, :cond_1

    const/4 p0, 0x0

    return-object p0

    :cond_1
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-super {p0}, Lcom/miui/internal/view/menu/MenuBuilder;->getActionViewStatesKey()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p0, ":"

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public getItem()Landroid/view/MenuItem;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mItem:Lcom/miui/internal/view/menu/MenuItemImpl;

    return-object p0
.end method

.method public getParentMenu()Landroid/view/Menu;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    return-object p0
.end method

.method public getRootMenu()Lcom/miui/internal/view/menu/MenuBuilder;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    return-object p0
.end method

.method public isQwertyMode()Z
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0}, Lcom/miui/internal/view/menu/MenuBuilder;->isQwertyMode()Z

    move-result p0

    return p0
.end method

.method public isShortcutsVisible()Z
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0}, Lcom/miui/internal/view/menu/MenuBuilder;->isShortcutsVisible()Z

    move-result p0

    return p0
.end method

.method public setCallback(Lcom/miui/internal/view/menu/MenuBuilder$Callback;)V
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setCallback(Lcom/miui/internal/view/menu/MenuBuilder$Callback;)V

    return-void
.end method

.method public setHeaderIcon(I)Landroid/view/SubMenu;
    .locals 1

    invoke-virtual {p0}, Lcom/miui/internal/view/menu/MenuBuilder;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-super {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setHeaderIconInt(Landroid/graphics/drawable/Drawable;)Lcom/miui/internal/view/menu/MenuBuilder;

    return-object p0
.end method

.method public setHeaderIcon(Landroid/graphics/drawable/Drawable;)Landroid/view/SubMenu;
    .locals 0

    invoke-super {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setHeaderIconInt(Landroid/graphics/drawable/Drawable;)Lcom/miui/internal/view/menu/MenuBuilder;

    return-object p0
.end method

.method public setHeaderTitle(I)Landroid/view/SubMenu;
    .locals 1

    invoke-virtual {p0}, Lcom/miui/internal/view/menu/MenuBuilder;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-super {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setHeaderTitleInt(Ljava/lang/CharSequence;)Lcom/miui/internal/view/menu/MenuBuilder;

    return-object p0
.end method

.method public setHeaderTitle(Ljava/lang/CharSequence;)Landroid/view/SubMenu;
    .locals 0

    invoke-super {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setHeaderTitleInt(Ljava/lang/CharSequence;)Lcom/miui/internal/view/menu/MenuBuilder;

    return-object p0
.end method

.method public setHeaderView(Landroid/view/View;)Landroid/view/SubMenu;
    .locals 0

    invoke-super {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setHeaderViewInt(Landroid/view/View;)Lcom/miui/internal/view/menu/MenuBuilder;

    return-object p0
.end method

.method public setIcon(I)Landroid/view/SubMenu;
    .locals 1

    iget-object v0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mItem:Lcom/miui/internal/view/menu/MenuItemImpl;

    invoke-virtual {v0, p1}, Lcom/miui/internal/view/menu/MenuItemImpl;->setIcon(I)Landroid/view/MenuItem;

    return-object p0
.end method

.method public setIcon(Landroid/graphics/drawable/Drawable;)Landroid/view/SubMenu;
    .locals 1

    iget-object v0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mItem:Lcom/miui/internal/view/menu/MenuItemImpl;

    invoke-virtual {v0, p1}, Lcom/miui/internal/view/menu/MenuItemImpl;->setIcon(Landroid/graphics/drawable/Drawable;)Landroid/view/MenuItem;

    return-object p0
.end method

.method public setQwertyMode(Z)V
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setQwertyMode(Z)V

    return-void
.end method

.method public setShortcutsVisible(Z)V
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/view/menu/SubMenuBuilder;->mParentMenu:Lcom/miui/internal/view/menu/MenuBuilder;

    invoke-virtual {p0, p1}, Lcom/miui/internal/view/menu/MenuBuilder;->setShortcutsVisible(Z)V

    return-void
.end method
