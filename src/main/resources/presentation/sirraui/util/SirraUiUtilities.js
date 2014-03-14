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

// Record the initial URL
var initialUrl = location.href;

// This allows the passing of "instructions" to pages via the URL
function getInitialUrlParam(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(initialUrl);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}