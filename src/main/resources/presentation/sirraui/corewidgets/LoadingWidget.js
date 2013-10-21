function LoadingWidget(settings) {
	ClassUtil.mixin(LoadingWidget, this, Widget);
	Widget.call(this, "LoadingWidget", true, settings);

	var right = this.widget.find(".right");
	
	if(this.settings.get("showMessage", false) == true) {	
		right.setText("Loading...");
		right.addClass("active");
	}
	
	if(this.settings.get("message", null) != null) {
		right.setText(this.settings.get("message"));
		right.addClass("active");
	}
	
	if(this.settings.get("pageMode") == true) {
		this.widget.addClass("page-mode");
	}
	
	current.append(this.widget);
}