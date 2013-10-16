function TableWidget(context, settings) {
	ClassUtil.mixin(TableWidget, this, Widget);
	Widget.call(this, "TableWidget", null, settings);
	
	this.context = context;
	
	this.th = this.widget.find("th");
	this.thParent = this.th.parent().empty();
	
	this.td = this.widget.find("tbody > tr > td").empty();
	this.tr = this.td.parent().empty();
	this.tbody = this.tr.parent().empty();
	
	this.columns = [];
	this.selectors = [];
}

TableWidget.prototype.addSelectorColumn = function(action) {
	var ownerObject = this;
	
	this.addHeader("");
	this.addColumn(function(item) {
		var cb = new CheckboxWidget(false, {classes: ["selection"]});
		ownerObject.selectors.push({cb: cb, item: item});
		if(action != null) {
			cb.widget.click(function() {
				action.call(cb, item);
			});
		}
	});
};

TableWidget.prototype.getSelectedItems = function() {
	var selectedItems = [];
	for(var i=0; i<this.selectors.length; i++) {
		var obj = this.selectors[i];
		if(obj.cb.getValue() == true) selectedItems.push(obj.item);
	}
	return selectedItems;
};

TableWidget.prototype.addHeader = function(text, settings) {
	var newTh = this.th.clone();
	newTh.setText(text);
	this.thParent.append(newTh);
	
	var s = new Settings(settings);
	if(s.has("css")) {
		newTh.css(s.get("css"));
	}
};

// Settings like {css: {width: "200px"}}
TableWidget.prototype.addColumn = function(col1, col2AndSoOn, settings) {
	var col = this.columns[this.columns.length] = [];
	
	for(var i=0; i<arguments.length; i++) {
		if(arguments[i].css != null) {
			col.settings = new Settings(arguments[i]);
		} else {
			col.push(arguments[i]);
		}
	}
};

TableWidget.prototype.renderList = function(list) {
	this.tbody.empty();
	this.selectors = [];
	
	this.list = list;
	for(var i=0; i<list.length; i++) {
		var item = list[i];
		
		var newTr = this.tr.clone();
		this.tbody.append(newTr);
		
		for(var j=0; j<this.columns.length; j++) {
			var col = this.columns[j];
			var newTd = this.td.clone();
			
			if(col.settings != null && col.settings.has("css")) {
				newTd.css(col.settings.get("css"));
			}
			newTr.append(newTd);
			
			current = newTd;
			for(var k=0; k<col.length; k++) {
				if(isFunction(col[k])) col[k].call(this.context, item);
				else new TextWidget(item[col[k]]);
			}
		}
	}
	
	this.finish();
};

TableWidget.prototype.setLoader = function(loaderAction) {
	this.loaderAction = loaderAction;
};

TableWidget.prototype.render = function() {
	this.loaderAction.call();
};

TableWidget.prototype.refresh = function() {
	this.loaderAction.call();
};