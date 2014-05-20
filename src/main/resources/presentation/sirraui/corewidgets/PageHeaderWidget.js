function PageHeaderWidget(text, hasLimitedWidth) {
	ClassUtil.mixin(PageHeaderWidget, this, Widget);
	Widget.call(this, "PageHeaderWidget");
	
	this.setValue(text);
	
	$(document).find(".mobile-header .title").text(text);
	
	if(hasLimitedWidth != null && hasLimitedWidth == true) {
		$(document).find(".app-content").addClass("limited-width");
	}
}