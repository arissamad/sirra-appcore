function MenuWidget(menus) {
	pushCurrent();
	current = $(".menu-container");
	
	ClassUtil.mixin(MenuWidget, this, Widget);
	Widget.call(this, "MenuWidget");

	var menuItemMaster = this.widget.find(".menuItem");
	this.menuParent = menuItemMaster.parent();
	menuItemMaster.detach();
	
	var supertopMenuItemMaster = this.widget.find(".supertop-menu-item");
	this.supertopMenuParent = supertopMenuItemMaster.parent();
	supertopMenuItemMaster.detach();
	
	this.menuJqLookup = {};
	this.firstMenuMetaId;
	
	for(var i=0; i<menus.length; i++) {
		var menu = menus[i];
		
		if(menu.isSupertop == true) {
			var menuJq = supertopMenuItemMaster.clone();
			this.supertopMenuParent.append(menuJq);
		} else {
			var menuJq = menuItemMaster.clone();
			this.menuParent.append(menuJq);
		}
		
		menuJq.data("menu", menu);
		
		this.menuJqLookup[menu.metaId] = menuJq;
		
		menuJq.find(".menuName").setText(menu.name);
		menuJq.click($IA(this, "select", menu.metaId));
	}

	if(menus.length > 1) {
		this.firstMenuMetaId = menus[0].metaId;
	}
	
	popCurrent();
	
	// Handle mobile version
	var clickedOnce = false;
	var menus = $(document).find(".spine .spine-top .menu-container");
	
	$(document).find(".mobile-header .menu-expander").click($IA(this, function(e) {
		e.stopPropagation();
		
		if(menus.is(":visible")) {
			menus.css("display", "");
		} else {
			menus.css("display", "block");
		}
		
		if(!clickedOnce) {
			clickedOnce = true;
			$(document).click(function() {
				menus.css("display", "");
				log("Boom click");
			});
		}
	}));
	
	// Load URL of self photo
	Rest.get("/api/users/self", {}, $A(this, function(user) {
		this.widget.find(".thumb img").attr("src", user.thumbUrl);
	}));
	
	this.isVisible = false;
	
	this.supertopPane = this.widget.find(".supertop-pane");
	this.widget.find(".oval").click($IA(this, function(e) {
		e.stopPropagation();
		if(this.isVisible == false) {
			this.turnOnSupertopPane();
		} else {
			this.turnOffSupertopPane();
		}
	}));
};

MenuWidget.prototype.turnOnSupertopPane = function() {
	this.supertopPane.show();
	this.isVisible = true;
	
	$(document).one("click", $IA(this, function() {
		this.turnOffSupertopPane();
	}));
};

MenuWidget.prototype.turnOffSupertopPane = function() {
	this.supertopPane.hide();
	this.isVisible = false;
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
	var menuJq = this.menuJqLookup[metaId];
	
	if(menuJq == null) {
		metaId = this.firstMenuMetaId;
		menuJq = this.menuJqLookup[metaId];
	}
	
	var menu = menuJq.data("menu");
	
	History.pushState({}, menu.name, "/" + menu.metaId);
	trackPage();
	
	current = $(".app-content");
	current.empty();
	current.removeClass("limited-width");
	
	var channelClass = window[menu.jsClass];
	if(channelClass == null) {
		log("Unknown channel class: " + menu.jsClass);
	}
	
	new channelClass();
};
