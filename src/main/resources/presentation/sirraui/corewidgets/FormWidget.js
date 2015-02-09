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
	
	if(this.settings.has("css")) this.widget.css(this.settings.get("css"));
	
	this.links = {};
	this.verifiers = {};
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

FormWidget.prototype.getCurrentRow = function() {
	return this.currentTr;
};

FormWidget.prototype.getWidget = function(metaId) {
	return this.links[metaId];
};

FormWidget.prototype.setValue = function(metaId, value) {
	if(this.links[metaId] != null) this.links[metaId].setValue(value);
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

FormWidget.prototype.link = function(metaId, inputWidget, verifier) {
	this.links[metaId] = inputWidget;
	if(verifier != null) this.verifiers[metaId] = verifier;
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
			
			if(this.links[attr].input.is("textarea")) return;
			
			this.links[attr].input.enter(function() {
				action.call();
			});
		}
	}
};

FormWidget.prototype.verify = function() {
	var problems = [];
	
	for(var metaId in this.links) {
		var widget = this.links[metaId];
		var value = widget.getValue();
		
		var verifier = this.verifiers[metaId];
		
		if(verifier == null) continue;
		
		for(var i=0; i<verifier.checkers.length; i++) {
			var checker = verifier.checkers[i];
			
			var result = checker.verify(value);
			if(result == true) continue;
			
			problems.push({name: verifier.name, message: result});
			break;
		}
	}
	
	if(problems.length == 0) return true;
	
	var dialog = new DialogWidget("Problems with your entries", 500);
	for(var i=0; i<problems.length; i++) {
		new TextWidget(problems[i].name + ": " + problems[i].message);
		new LW(0);
	}
	dialog.buttons();
	new ButtonWidget("OK", $A(dialog, "close"));
	dialog.reposition();
	
	return false;
};