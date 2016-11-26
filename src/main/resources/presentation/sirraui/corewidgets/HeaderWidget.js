function HeaderWidget(text, settings) {
	ClassUtil.mixin(HeaderWidget, this, Widget);
	Widget.call(this, "HeaderWidget", null, settings);
	
	this.setValue(text);
}