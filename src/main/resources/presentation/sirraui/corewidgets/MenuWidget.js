function MenuWidget(menus, initialMenuMetaId) {
	ClassUtil.mixin(MenuWidget, this, Widget);
	Widget.call(this, "MenuWidget");

	var menuItemMaster = this.widget.find(".menuItem");
	var menuParent = menuItemMaster.parent();
	menuItemMaster.detach();
	
	this.menuLookup = {};
	
	var selectedMenu;
	
	for(var i=0; i<menus.length; i++) {
		var menu = menus[i];
		
		var newMenu = menuItemMaster.clone();
		this.menuLookup[menu.metaId] = newMenu;
		
		newMenu.find(".menuName").setText(menu.name);
		menuParent.append(newMenu);
		
		if(initialMenuMetaId == menu.metaId) {
			selectedMenu = newMenu;
		}
		
		newMenu.click(function(menu, menuJq) {
			return function() {
				// History.pushState({}, "Sirra Voicemail - " + menu.name, "/app/" + menu.metaId);
				menuParent.find(".menuItem").removeClass("selected");
				menuJq.addClass("selected");
				
				current = $(".app-content");
				current.empty();
				
				var channelClass = window[menu.jsClass];
				if(channelClass == null) {
					log("Unknown channel class: " + menu.jsClass);
				}
				
				new channelClass();
			};
		}(menu, newMenu));
	}

	if(menus.length > 1) {
		if(selectedMenu == null) {
			var firstMenu = menuParent.children().first();
			firstMenu.click();
		} else {
			selectedMenu.click();
		}
	}
};

MenuWidget.prototype.select = function(metaId) {
	this.menuLookup[metaId].click();
}