// The stuff here may need to be reorganized
var isFunction = function(theObject) {
	if(theObject != null && typeof theObject == "function") {
		return true;
	} else {
		return false;
	}
}

//Google Analytics
function trackPage() {
	var location = window.location.protocol +
    '//' + window.location.hostname +
    window.location.pathname +
    window.location.search;
	
	log("Tracking " + location);
	if(window.ga != null) ga('send', 'pageview');
}