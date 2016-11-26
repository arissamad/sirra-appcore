// To use new google version of firebase.
// Put the gv.firebaseConfig at the top of your channel file, in just one place.
function GoogleFirebaseLoader(action) {
	if(gv.loadedFirebase != true) {
		loadJs("https://www.gstatic.com/firebasejs/3.1.0/firebase.js", function() {
			firebase.initializeApp(gv.firebaseConfig);
			action.call();
		});
	} else {
		action.call();
	}
}