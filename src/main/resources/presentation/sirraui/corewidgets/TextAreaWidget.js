function TextAreaWidget(text, settings) {
	ClassUtil.mixin(TextAreaWidget, this, Widget);
	Widget.call(this, "TextAreaWidget", true, settings);
	
	if(text != null) this.setValue(text);
}