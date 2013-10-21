function HeaderWidget(text) {
	ClassUtil.mixin(HeaderWidget, this, Widget);
	Widget.call(this, "HeaderWidget");
	
	this.setValue(text);
}