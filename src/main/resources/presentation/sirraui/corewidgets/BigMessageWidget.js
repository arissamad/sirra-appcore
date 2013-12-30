function BigMessageWidget(htmlMessage) {
	this.widget = $(".big-message");
	this.widget.find(".text-section").html(htmlMessage);
	this.widget.show();
	
	this.widget.find(".x-section").click($IA(this, function() {
		this.remove();
	}));
}

BigMessageWidget.prototype.remove = function() {
	this.widget.empty();
	this.widget.hide();
};