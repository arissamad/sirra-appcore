function CheckboxWidget(settings) {
	ClassUtil.mixin(CheckboxWidget, this, Widget);
	Widget.call(this, "CheckboxWidget", true, settings);
}

CheckboxWidget.prototype.setValue = function(value) {
	if(value == true) {
		this.input.attr("checked", "yes");
	} else {
		this.input.removeAttr("checked");
	}
};

CheckboxWidget.prototype.getValue = function() {
	return this.input.is(":checked");
};