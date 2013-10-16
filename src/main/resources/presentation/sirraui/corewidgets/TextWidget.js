function TextWidget(text) {
	ClassUtil.mixin(TextWidget, this, Widget);
	Widget.call(this, "TextWidget");
	
	this.setValue(text);
}