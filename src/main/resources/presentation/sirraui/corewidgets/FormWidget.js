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
	
	this.leftTd.detach();
	this.rightTd.detach();
	
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
	if(this.settings.has("leftMinWidth")) td.css("min-width", this.settings.get("leftMinWidth"));
	if(this.settings.has("leftCss")) td.css(this.settings.get("leftCss"));
};

FormWidget.prototype.value = function() {
	var td = this.rightTd.clone();
	this.currentTr.append(td);
	current = td;
	
	if(this.settings.has("rightWidth")) td.width(this.settings.get("rightWidth"));
	if(this.settings.has("rightCss")) td.css(this.settings.get("rightCss"));
};

FormWidget.prototype.setValues = function(valueObject) {
	if(valueObject != null) {
		for(var metaId in this.links) {
			var value = valueObject[metaId];
			
			if(value != null) {
				var currValue = this.links[metaId].getValue();
				
				// For widgets that support _hasSetValue:
				if(this.links[metaId]._hasSetValue != null && this.links[metaId]._hasSetValue() == false) {
					this.links[metaId].setValue(value);
					continue;
				}
				
				// For normal widgets:
				if(currValue == null || currValue == "") {
					this.links[metaId].setValue(value);
				}
			}
		}
	}
};

FormWidget.prototype.link = function(metaId, inputWidget) {
	this.links[metaId] = inputWidget;
};

FormWidget.prototype.getValue = function(metaId) {
	if(this.links[metaId] == null) return null;
	return this.links[metaId].getValue();
};

FormWidget.prototype.focus = function(metaId) {
	if(metaId != null) {
		this.links[metaId].input.focus();
	} else {
		for(var attr in this.links) {
			this.links[attr].input.focus();
			break;
		}
	}
};

FormWidget.prototype.submitOnEnter = function(action) {
	for(var attr in this.links) {
		if(this.links[attr].input != null) {
			this.links[attr].input.enter(function() {
				action.call();
			});
		}
	}
};