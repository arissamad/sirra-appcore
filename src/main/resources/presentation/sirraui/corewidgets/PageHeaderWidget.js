function PageHeaderWidget(text) {
	ClassUtil.mixin(PageHeaderWidget, this, Widget);
	Widget.call(this, "PageHeaderWidget");
	
	this.setValue(text);
	
	$(document).find(".mobile-header .title").text(text);
}