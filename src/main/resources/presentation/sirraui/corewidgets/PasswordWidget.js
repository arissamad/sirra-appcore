function PasswordWidget(text, settings) {
	ClassUtil.mixin(PasswordWidget, this, Widget);
	Widget.call(this, "PasswordWidget", true, settings);
	
	if(text != null) this.setValue(text);
}