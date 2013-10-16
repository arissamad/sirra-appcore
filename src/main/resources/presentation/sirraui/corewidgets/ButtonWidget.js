function ButtonWidget(text, action) {
	ClassUtil.mixin(ButtonWidget, this, Widget);
	Widget.call(this, "ButtonWidget");
	
	this.widget.disableTextSelect();
	if(!text != null) this.setValue(text);
	
	this.clickFunction = function(event) {
		event.stopPropagation();
		action.call();
		
	}
	this.widget.on("click", this.clickFunction);
}

ButtonWidget.prototype.loading = function() {
	current = this.widget.find(".right-section");
	this.widget.addClass("inactive");
	this.widget.removeClass("active");
	
	this.lw = new LoadingWidget();
	this.widget.off("click");
};

ButtonWidget.prototype.doneLoading = function() {
	current = this.widget.find(".right-section");
	this.widget.removeClass("inactive");
	this.widget.addClass("active");
	this.lw.remove();
	this.widget.off("click");
	this.widget.on("click", this.clickFunction);
};