function TextAreaWidget(text, settings) {
	ClassUtil.mixin(TextAreaWidget, this, Widget);
	Widget.call(this, "TextAreaWidget", true, settings);
	
	if(this.settings.has("css")) {
		this.widget.find("textarea").css(this.settings.get("css"));
	}
	
	if(text != null) this.setValue(text);
}