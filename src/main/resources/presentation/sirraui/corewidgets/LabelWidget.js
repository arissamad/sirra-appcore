function LabelWidget(text, settings) {
	ClassUtil.mixin(LabelWidget, this, Widget);
	Widget.call(this, "LabelWidget", true, settings);
	
	this.setValue(text);
}