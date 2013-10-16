function DialogWidget(title) {
	ClassUtil.mixin(DialogWidget, this, Widget);
	Widget.call(this, "DialogWidget");
	
	this.widget.dialog({
		title: title
	});
	
	current = this.widget.find(".dw-content");
}

DialogWidget.prototype.reposition = function() {
	this.widget.dialog({
		position: "center"
	});
};

DialogWidget.prototype.close = function() {
	this.widget.dialog("close");
};