function LinkWidget(text, action, settings) {
	ClassUtil.mixin(LinkWidget, this, Widget);
	Widget.call(this, "LinkWidget", true, settings);
	
	this.widget.disableTextSelect();
	if(!text != null) this.setValue(text);
	
	this.clickFunction = function(event) {
		event.stopPropagation();
		action.call();
		
	}
	this.widget.on("click", this.clickFunction);
}

LinkWidget.prototype.loading = function() {
	current = this.widget.find(".right-section");
	this.widget.addClass("inactive");
	this.widget.removeClass("active");
	
	this.lw = new LoadingWidget();
	this.widget.off("click");
};

LinkWidget.prototype.doneLoading = function() {
	current = this.widget.find(".right-section");
	this.widget.removeClass("inactive");
	this.widget.addClass("active");
	this.lw.remove();
	this.widget.off("click");
	this.widget.on("click", this.clickFunction);
};

LinkWidget.prototype.trigger = function() {
	this.widget.trigger("click");
};