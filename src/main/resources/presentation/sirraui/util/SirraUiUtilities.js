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
	log("Tracking " + window.location.pathname);
	if(window.ga != null) ga('send', 'pageview', window.location.pathname);
}