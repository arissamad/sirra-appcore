function MarkerWidget(settings) {
	ClassUtil.mixin(MarkerWidget, this, Widget);
	Widget.call(this, "MarkerWidget", true, settings);
}

MarkerWidget.prototype.activate = function() {
	current = this.widget;
	current.empty();
};

MarkerWidget.prototype.setActive = function() {
	current = this.widget;
};