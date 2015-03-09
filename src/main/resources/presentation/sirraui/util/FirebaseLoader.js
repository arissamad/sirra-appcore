/**
 * Load the firebase script when needed.
 */
function FirebaseLoader(action) {
	if(gv.loadedFirebase != true) {
		loadJs("https://cdn.firebase.com/js/client/2.2.2/firebase.js", function() {
			action.call();
		});
	} else {
		action.call();
	}
}