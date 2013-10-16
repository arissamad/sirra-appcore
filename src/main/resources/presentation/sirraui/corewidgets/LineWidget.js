/** This is for line breaks */
function LineWidget(numLines) {
	ClassUtil.mixin(LineWidget, this, Widget);
	Widget.call(this, "LineWidget");
	
	if(numLines == null) numLines = 1;
	this.widget.css("height", numLines * 10);
}

var LW = LineWidget;