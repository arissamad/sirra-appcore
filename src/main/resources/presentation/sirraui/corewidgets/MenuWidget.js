function MenuWidget(menus) {
	pushCurrent();
	current = $(".menu-container");
	
	ClassUtil.mixin(MenuWidget, this, Widget);
	Widget.call(this, "MenuWidget");

	var menuItemMaster = this.widget.find(".menuItem");
	this.menuParent = menuItemMaster.parent();
	menuItemMaster.detach();
	
	this.menuJqLookup = {};
	this.firstMenuMetaId;
	
	for(var i=0; i<menus.length; i++) {
		var menu = menus[i];
		
		var menuJq = menuItemMaster.clone();
		menuJq.data("menu", menu);
		
		this.menuJqLookup[menu.metaId] = menuJq;
		
		menuJq.find(".menuName").setText(menu.name);
		this.menuParent.append(menuJq);
		
		menuJq.click($IA(this, "select", menu.metaId));
	}

	if(menus.length > 1) {
		this.firstMenuMetaId = menus[0].metaId;
	}
	
	popCurrent();
};

MenuWidget.prototype.select = function(metaId) {
	if(metaId == null) metaId = this.firstMenuMetaId;
	
	this.highlight(metaId);
	this._renderPage(metaId);
}

MenuWidget.prototype.highlight = function(metaId) {
	this.menuParent.find(".menuItem").removeClass("selected");
	
	if(this.menuJqLookup[metaId] == null) {
		log("Menu with metaId \"" + metaId + "\" not found. Is the user retrieving the right list of menus?");
		return;
	}
	this.menuJqLookup[metaId].addClass("selected");
};

MenuWidget.prototype._renderPage = function(metaId) {
	var menu = this.menuJqLookup[metaId].data("menu");
	
	History.pushState({}, menu.name, "/" + menu.metaId);
	trackPage();
	
	current = $(".app-content");
	current.empty();
	
	var channelClass = window[menu.jsClass];
	if(channelClass == null) {
		log("Unknown channel class: " + menu.jsClass);
	}
	
	new channelClass();
};
