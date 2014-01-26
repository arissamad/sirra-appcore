function DialogWidget(title, settings) {
	ClassUtil.mixin(DialogWidget, this, Widget);
	Widget.call(this, "DialogWidget", true, settings);
	
	if(settings == null) {
		settings = {};
	}
	settings.title = title;
	settings.modal = true;
	
	settings.dialogClass = "sirra-dialog";
	settings.buttons = [{}];
	
	settings.close = $IA(this, function() {
		this.widget.remove();
	});
	
	this.widget.dialog(settings);
	
	var jqDialog = this.widget.closest(".ui-dialog");
	this.buttonSetJq = jqDialog.find(".ui-dialog-buttonpane");
	this.buttonSetJq.empty();
	
	current = this.buttonSetJq;
	var cols = new ColumnWidget({css:{width: "100%"}});
	this.leftButtons = current;
	
	cols.addColumn();
	this.rightButtons = current;
	this.rightButtons.addClass("right-buttons");
	
	current = this.widget.find(".dw-content");
}

DialogWidget.prototype.buttons = function() {
	current = this.leftButtons;
};

DialogWidget.prototype.right = function() {
	current = this.rightButtons;
};

DialogWidget.prototype.reposition = function() {
	this.widget.dialog({position:[null, (window.innerHeight/2) - (this.widget.height()/2) - 80]});
};

DialogWidget.prototype.close = function() {
	this.widget.dialog("close");
};