/**
 * The sole purpose of this widget is to act like a normal widget, but to return null when getValue() is called.
 * 
 * Plays especially nice with Rest.js, which does not include null values in parameters.
 */
function NullWidget() {}

NullWidget.prototype.getValue = function() {
	return null;
};