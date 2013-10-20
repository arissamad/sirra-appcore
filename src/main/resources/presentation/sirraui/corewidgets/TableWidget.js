function TableWidget(context, settings) {
	ClassUtil.mixin(TableWidget, this, Widget);
	Widget.call(this, "TableWidget", null, settings);
	
	this.context = context;
	
	this.table = this.widget.find("table");
	
	this.th = this.widget.find("th");
	this.thParent = this.th.parent().empty();
	
	this.td = this.widget.find("tbody > tr > td").empty();
	this.tr = this.td.parent().empty();
	this.tbody = this.tr.parent().empty();
	
	this.columns = [];
	this.selectors = [];
	this.page = 0;
	this.searchTerm = null;
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
	this.table.css("opacity", 1);
};

TableWidget.prototype.setSearchAction = function(searchAction) {
	var input = this.widget.find(".search-outer input");
	
	this.widget.find(".search-outer").show();
	this.widget.find(".search").click($IA(this, function() {
		this.page = 0;
		searchAction.call(input.val());
	}));
	input.keypress($IA(this, function(e) {
		if(e.keyCode == 13) {
			this.page = 0;
			searchAction.call(input.val());
		}
	}));
	
	this.searchAction = searchAction;
};

TableWidget.prototype.turnOnSearching = function() {
	var input = this.widget.find(".search-outer input");
	
	this.widget.find(".search-outer").show();
	this.widget.find(".search").click($IA(this, function() {
		this.page = 0;
		this.searchTerm = input.val();
		this.refresh();
	}));
	input.keypress($IA(this, function(e) {
		if(e.keyCode == 13) {
			this.page = 0;
			this.searchTerm = input.val();
			this.refresh();
		}
	}));
};

TableWidget.prototype.turnOnPaging = function() {
	this.widget.find(".pager").show();
	this.pageJq = this.widget.find(".page-number");
	
	this.widget.find(".mover.left").click($IA(this, function() {
		this.page--;
		if(this.page < 0) { this.page = 0; return; }
		this.refresh();
	}));
	this.widget.find(".mover.right").click($IA(this, function() {
		this.page++;
		this.refresh();
	}));
};

TableWidget.prototype.setLoader = function(loaderAction) {
	this.loaderAction = $A(this, function() {
		this.table.css("opacity", 0.3);
		var sqlParams = new Object();
		sqlParams._page = this.page;
		if(this.searchTerm != null) sqlParams._search = this.searchTerm;
		loaderAction.call(sqlParams);
	});
};

TableWidget.prototype.render = function() {
	this.loaderAction.call();
};

TableWidget.prototype.refresh = function() {
	this.pageJq.setText("" + (this.page+1));
	this.loaderAction.call();
};