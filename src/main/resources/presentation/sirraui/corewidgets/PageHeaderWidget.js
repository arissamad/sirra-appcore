function PageHeaderWidget(text) {
	ClassUtil.mixin(PageHeaderWidget, this, Widget);
	Widget.call(this, "PageHeaderWidget");
	
	this.setValue(text);
}