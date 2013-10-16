function LinkWidget(text, action) {
	ClassUtil.mixin(LinkWidget, this, Widget);
	Widget.call(this, "LinkWidget");
	
	this.widget.disableTextSelect();
	if(!text != null) this.setValue(text);
	
	this.clickFunction = function(event) {
		event.stopPropagation();
		action.call();
		
	}
	this.widget.on("click", this.clickFunction);
}