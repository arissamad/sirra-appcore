function UploadWidget(action, settings) {
	ClassUtil.mixin(UploadWidget, this, Widget);
	Widget.call(this, "UploadWidget", true, settings);
	
	this.action = action;
	
	if(window.transloadit_uuid == null) {
		log("Loading transloadit...");
		loadJs("//assets.transloadit.com/js/jquery.transloadit2-v2-latest.js", $IA(this, function() {
			log("Loaded transloadit");
			this.loadedScript();
		}));
	} else {
		log("Transloadit already loaded.");
		this.loadedScript();
	}
}

UploadWidget.prototype.loadedScript = function() {
	var action = this.action;
	
	this.widget.find("form").transloadit({
		wait: true,
		autoSubmit: false,
		triggerUploadOnFileSelection: true,
		onResult: function(step, result) {
			log("onResult", step, result);
			var imageUrl = result.ssl_url;
			log("Image URL: " + imageUrl);
			
			action.call(imageUrl);
		},
		onSuccess: function(assembly) {
			log("onSuccess", assembly);
		},
		params: {
			auth: {
				key: "581f47406a1111e3ad7f53e864f217b2"
			},
			template_id: "400682306fbc11e3b3378d986542efc1"
		}
	});
};