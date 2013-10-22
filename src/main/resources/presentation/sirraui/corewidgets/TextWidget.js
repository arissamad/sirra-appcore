function TextWidget(text, settings) {
	ClassUtil.mixin(TextWidget, this, Widget);
	Widget.call(this, "TextWidget", true, settings);
	
	this.setValue(text);
}