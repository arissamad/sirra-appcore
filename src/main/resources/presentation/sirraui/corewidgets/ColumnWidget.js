function ColumnWidget(settings) {
	ClassUtil.mixin(ColumnWidget, this, Widget);
	Widget.call(this, "ColumnWidget", true, settings);
	
	this.td = this.widget.find("td");
	this.td.detach();
	
	this.tr = this.widget.find("tr");
	
	this.addColumn(0);
}

ColumnWidget.prototype.addColumn = function(paddingMultiplier, settings) {
	var newTd = this.td.clone();
	this.tr.append(newTd);
	
	if(paddingMultiplier != null) {
		newTd.css("padding-left", paddingMultiplier * 40);
	}
	
	current = newTd;
};

ColumnWidget.prototype.setActiveColumn = function(index) {
	var tds = this.tr.find("td.col");
	current = $(tds[index]);
	
	return current;
};