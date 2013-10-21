function BoldLinkWidget(text, action) {
	ClassUtil.mixin(BoldLinkWidget, this, Widget);
	Widget.call(this, "LinkWidget");
	
	this.widget.addClass("bold-link");
	
	this.widget.disableTextSelect();
	if(!text != null) this.setValue(text);
	
	this.clickFunction = function(event) {
		event.stopPropagation();
		action.call();
		
	}
	this.widget.on("click", this.clickFunction);
}