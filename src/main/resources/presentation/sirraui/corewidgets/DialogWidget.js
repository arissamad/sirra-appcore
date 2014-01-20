function DialogWidget(title, settings) {
	ClassUtil.mixin(DialogWidget, this, Widget);
	Widget.call(this, "DialogWidget", true, settings);
	
	if(settings == null) {
		settings = {};
	}
	settings.title = title;
	settings.modal = true;
	
	this.widget.dialog(settings);
	
	current = this.widget.find(".dw-content");
}

DialogWidget.prototype.reposition = function() {
	this.widget.dialog({position:[null, (window.innerHeight/2) - (this.widget.height()/2) - 80]});
};

DialogWidget.prototype.close = function() {
	this.widget.dialog("close");
};