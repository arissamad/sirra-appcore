function LabelWidget(text) {
	ClassUtil.mixin(LabelWidget, this, Widget);
	Widget.call(this, "LabelWidget");
	
	this.setValue(text);
}