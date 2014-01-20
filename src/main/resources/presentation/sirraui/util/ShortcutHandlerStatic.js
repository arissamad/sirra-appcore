function ShortcutHandlerStatic() {
	this.jqDoc = $(document);
	this.shortcuts = [];
}

ShortcutHandlerStatic.prototype.addShortcut = function(action) {
	var method = function(e) {
		action.call(e);
	};
	this.jqDoc.on("keydown", method);
	this.shortcuts[this.shortcuts.length] = method;
}

ShortcutHandlerStatic.prototype.clearAll = function() {
	for(var i=0; i<this.shortcuts.length; i++) {
		var method = this.shortcuts[i];
		this.jqDoc.off("keydown", method);
	}
	this.shortcuts = [];
}

var ShortcutHandler = new ShortcutHandlerStatic();