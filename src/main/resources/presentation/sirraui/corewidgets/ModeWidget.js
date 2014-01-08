function ModeWidget(settings) {
	ClassUtil.mixin(ModeWidget, this, Widget);
	Widget.call(this, "ModeWidget", true, settings);
}

ModeWidget.prototype.addMode = function(text, action, isActive) {
	pushCurrent();
	current = this.widget;
	
	var tw = new LinkWidget(text, $A(this, function() {
		this.widget.find(".LinkWidget").show();
		tw.widget.hide();
		action.call();
	}));
	
	if(isActive != true) {
		tw.widget.hide();
	}
	
	popCurrent();
};