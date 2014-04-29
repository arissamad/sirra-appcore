function CheckboxWidget(settings) {
	ClassUtil.mixin(CheckboxWidget, this, Widget);
	Widget.call(this, "CheckboxWidget", true, settings);
}

CheckboxWidget.prototype.setValue = function(value) {
	if(value == true) {
		this.input.prop("checked", true);
	} else {
		this.input.prop("checked", false);
	}
};

CheckboxWidget.prototype.getValue = function() {
	return this.input.is(":checked");
};