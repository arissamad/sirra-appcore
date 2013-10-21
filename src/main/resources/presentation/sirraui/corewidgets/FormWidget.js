/**
 * tableWidth
 * leftWidth
 * rightWidth
 */
function FormWidget(settings) {
	ClassUtil.mixin(FormWidget, this, Widget);
	Widget.call(this, "FormWidget");
	
	this.leftTd = this.widget.find("td.left");
	this.rightTd = this.widget.find("td.right");
	this.tr = this.widget.find("tr");
	this.table = this.widget.find("table");
	
	this.table.empty();
	
	this.settings = new Settings(settings);
	
	if(this.settings.has("tableWidth")) this.table.width(this.settings.get("tableWidth"));
	
	this.links = {};
}

FormWidget.prototype.label = function() {
	this.currentTr = this.tr.clone();
	this.table.append(this.currentTr);
	
	var td = this.leftTd.clone();
	this.currentTr.append(td);
	current = td;
	
	if(this.settings.has("leftWidth")) td.width(this.settings.get("leftWidth"));
};

FormWidget.prototype.value = function() {
	var td = this.rightTd.clone();
	this.currentTr.append(td);
	current = td;
	
	if(this.settings.has("rightWidth")) td.width(this.settings.get("rightWidth"));
};

FormWidget.prototype.setValues = function(valueObject) {
	if(valueObject != null) {
		for(var metaId in this.links) {
			var value = valueObject[metaId];
			
			if(value != null) {
				this.links[metaId].setValue(value);
			}
		}
	}
};

FormWidget.prototype.link = function(metaId, inputWidget) {
	this.links[metaId] = inputWidget;
};

FormWidget.prototype.getValue = function(metaId) {
	return this.links[metaId].getValue();
};