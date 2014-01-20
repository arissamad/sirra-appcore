function FullWidthWidget(settings) {
	ClassUtil.mixin(FullWidthWidget, this, Widget);
	Widget.call(this, "FullWidthWidget", true, settings);
	
	current = this.widget.find(".content");
}