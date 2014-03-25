function DropDownWidget(list, idAttribute, valueAttribute, initialId, settings) {
	ClassUtil.mixin(DropDownWidget, this, Widget);
	Widget.call(this, "DropDownWidget", true, settings);
	
	this.select = this.widget.find("select");
	
	this.optionTemplate = this.widget.find("option");
	this.optionTemplate.detach();
	
	this.appendList(list, idAttribute, valueAttribute, initialId);
}

DropDownWidget.prototype.appendList = function(list, idAttribute, valueAttribute, initialId) {
	this.list = list;
	this.idAttribute = idAttribute;
	this.valueAttribute = valueAttribute;
	
	for(var i=0; i<list.length; i++) {
		var item = list[i];
		
		var option = this.optionTemplate.clone();
		
		option.text(item[valueAttribute]);
		option.attr("id", item[idAttribute]);
		
		this.select.append(option);
	}
	
	if(initialId != null) this.setValue(initialId)
};

DropDownWidget.prototype.setValue = function(id) {
	var option = this.select.find("[id='" + id + "']");
	if(option != null) option.attr("selected", true);
	
	this.hasSetValue = true;
};

// Exclusiely for FormWidget.setValues
DropDownWidget.prototype._hasSetValue = function() {
	if(this.hasSetValue == true) return true;
	else return false;
};

DropDownWidget.prototype.getLabel = function() {
	var selected = this.select.find(":selected");
	var label = selected.text();
	
	return label;
};

DropDownWidget.prototype.getValue = function() {
	var selected = this.select.find(":selected");
	var id = selected.attr("id");
	
	return id;
};