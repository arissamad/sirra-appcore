function PageStackStatic() {
	this.stack = [];
}

// resourcePath: URL path like "/users/view"
PageStackStatic.prototype.push = function() {
	current = $(".app-content");
	
	var contents = current.children();
	
	if(contents.length > 0) {
		var scrollTop = $(window).scrollTop();
		
		this.stack.push({
			jq: contents.detach(),
			url: History.getPageUrl(),
			title: document.title,
			scrollTop: scrollTop
		});	
	}
};

PageStackStatic.prototype.pop = function() {
	var current = $(".app-content");
	current.empty();
	
	if(this.stack.length == 0) return false;
	
	var data = this.stack.pop();
	
	current.append(data.jq);
	
	History.pushState({}, data.title, data.url);
	
	$(window).scrollTop(data.scrollTop);
	
	return true;
};

var PageStack = new PageStackStatic();