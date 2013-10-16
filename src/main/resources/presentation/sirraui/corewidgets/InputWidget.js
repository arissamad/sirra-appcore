function InputWidget(text, settings) {
	ClassUtil.mixin(InputWidget, this, Widget);
	Widget.call(this, "InputWidget", true, settings);
	
	if(text != null) this.setValue(text);
}