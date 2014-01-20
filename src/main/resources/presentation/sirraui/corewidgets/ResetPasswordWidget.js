function ResetPasswordWidget() {
	$(".spine-top").hide();
	
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	this.hash = getParameterByName("h");
	new HeaderWidget("Resetting password...");
	
	this.marker = new MarkerWidget();
	
	var lw = new LoadingWidget();
	Rest.get("/api/users/checkresethash", {hash: this.hash}, $A(this, function(results) {
		lw.widget.remove();
		if(results.isSuccessful == true) {
			this.render();
		} else {
			new TextWidget("The link to reset password has expired or is incorrect. Please try to reset your password again.");
		}
	}));
}

ResetPasswordWidget.prototype.render = function() {
	this.marker.activate();
	new TextWidget("Please enter a new password below to reset your password.");
	
	new LW(4);
	
	new DivWidget({css: {width: "500px", margin: "0 auto", "text-align": "center"}});
	
	var fw = new FormWidget({
		tableWidth: "100%"
	});
	
	fw.label();
	new LabelWidget("Password");
	
	fw.value();
	fw.link("password", new PasswordWidget());
	
	fw.label();
	new LabelWidget("Confirm Password");
	
	fw.value();
	fw.link("confirmPassword", new PasswordWidget());

	fw.focus();
	fw.submitOnEnter($A(this, function() {
		updateButton.trigger();
	}));
	fw.finish();
	
	new LW(2);
	var message = new TextWidget("", {classes: ["note"]})
	
	new LW(4);
	var updateButton = new ButtonWidget("Reset Password", $A(this, function() {
		var pw = fw.getValue("password");
		message.setValue("");
		
		if(pw == "") {
			message.setValue("Password cannot be blank.");
			return;
		}
		
		if(pw.length < 4) {
			message.setValue("Password must be at least 4 characters.");
			return;
		}
		
		if(pw != fw.getValue("confirmPassword")) {
			message.setValue("Passwords do not match.");
			return;
		}

		Rest.post("/api/users/resetpassword", {hash: this.hash, password: fw.getValue("password")}, $A(this, function(results) {
			if(results.isSuccessful == true) {
				this.marker.activate();
				new TextWidget("You have successfully reset your password. Click below to proceed to login to SirraTeam.");
				new LW(2);
				var goButton = new ButtonWidget("Go to login screen", $A(this, function() {
					goButton.loading();
					setTimeout(function() {
						location.href = "/login";	
					}, 100);
				}));
			} else {
				message.setValue(results.error);
			}
		}));
		
	}));
};