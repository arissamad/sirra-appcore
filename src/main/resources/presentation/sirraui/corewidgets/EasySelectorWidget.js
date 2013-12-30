function EasySelectorWidget(list, idAttribute, valueAttribute, initialIds, settings) {
	ClassUtil.mixin(EasySelectorWidget, this, Widget);
	Widget.call(this, "EasySelectorWidget", true, settings);
	
	this.list = list;
	this.idAttribute = idAttribute;
	this.valueAttribute = valueAttribute;
	
	this.itemHolder = this.widget.find(".item-holder");
	
	this.option = this.widget.find(".option");
	this.item = this.widget.find(".item");
	this.item.remove();
	
	this.outer = this.widget.find(".outer");
	this.outer.hide();
	
	this.frame = this.widget.find(".frame");
	this.frame.empty();
	
	this.newValue = this.widget.find(".new");
	
	for(var i=0; i<list.length; i++) {
		var option = list[i];
		
		this.addOption(option);
	}
	
	if(initialIds != null) {
		this.setValue(initialIds);
	}
	
	this.input = this.widget.find("input");
	
	this.widget.click($IA(this, function(e) {
		this.input.focus();
	}));
	
	this.input.click($IA(this, function(e) {
		e.stopPropagation();
	}));
	
	this.input.keyup($IA(this, function(event) {
		var currText = this.input.val();
		if(event.keyCode == 13) {
			if(currText == "") return;
			this.triggerFirstVisible();
			return;
		}
		var size = currText.length + 2;
		this.input.attr("size", size);
		
		this.filterList(currText);
		this.newValue.text(currText);
	}));
	
	this.input.focusin($IA(this, function() {
		this.outer.show();
	}));
	
	this.input.focusout($IA(this, function() {
		setTimeout($IA(this, function() {
			this.outer.hide();	
		}), 100);
	}));
}

EasySelectorWidget.prototype.addItem = function(itemId) {
	var item;
	for(var i=0; i<this.list.length; i++) {
		var currItem = this.list[i];
		if(currItem[this.idAttribute] == itemId) {
			item = currItem;
			break;
		}
	}
	
	if(item == null) return;
	
	// Check for duplicates
	var ids = this.getValue();
	for(var i=0; i<ids.length; i++) {
		if(ids[i] == itemId) return;
	}
	
	var itemJq = this.item.clone();
	itemJq.data("item", item);
	itemJq.find("span").text(item[this.valueAttribute]);
	itemJq.find(".delete").click($IA(this, function(e) {
		itemJq.remove();
		e.stopPropagation();
	}));
	
	this.itemHolder.append(itemJq);
};

EasySelectorWidget.prototype.addOption = function(item) {
	var option = this.option.clone();
	option.text(item[this.valueAttribute]);
	option.data("item", item);
	
	option.click($IA(this, function(e) {
		this.input.blur();
		this.input.val("");
		this.addItem(item[this.idAttribute]);
		this.filterList("");
		e.stopPropagation();

	}));
	
	this.frame.append(option);
};

EasySelectorWidget.prototype.filterList = function(text) {
	text = text.toLowerCase();
	var itemJqs = this.frame.children();
	
	if(text == "") {
		itemJqs.show();
		return;
	}
	
	for(var i=0; i<itemJqs.length; i++) {
		var itemJq = $(itemJqs[i]);
		var item = itemJq.data("item");
		
		var value = item[this.valueAttribute];
		if(value.toLowerCase().indexOf(text) >= 0) {
			itemJq.show();
		} else {
			itemJq.hide();
		}
	}
};

EasySelectorWidget.prototype.triggerFirstVisible = function() {
	var visibleOptions = this.frame.children(":visible");
	if(visibleOptions.length > 0) {
		$(visibleOptions[0]).trigger("click");
	}
};

EasySelectorWidget.prototype.getItems = function() {
	var itemJqs = this.itemHolder.children();
	var items = [];
	
	for(var i=0; i<itemJqs.length; i++) {
		var item = $(itemJqs[i]).data("item");
		items.push(item);
	}
	
	return items;
};

EasySelectorWidget.prototype.getValue = function() {
	var ids = [];
	var items = this.getItems();
	for(var i=0; i<items.length; i++) {
		ids.push(items[i][this.idAttribute]);
	}
	
	return ids;
};

EasySelectorWidget.prototype.setValue = function(ids) {
	this.itemHolder.empty();
	if(ids == null) return;
	
	for(var i=0; i<ids.length; i++) {
		var item = ids[i];
		
		this.addItem(item);
	}
};