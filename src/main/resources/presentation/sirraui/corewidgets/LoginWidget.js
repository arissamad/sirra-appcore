function LoginWidget() {
	ClassUtil.mixin(LoginWidget, this, Widget);
	Widget.call(this, "LoginWidget", false);
	
	$(".spine-top").hide();
	
	current = $(".app-content");
	current.empty();
	
	current.append(this.widget);
	
	var username = this.widget.find(".username");
	var password = this.widget.find(".password");
	var loginButton = this.widget.find(".login-button");
	var loginMessage = this.widget.find(".login-message");
	
	var init = Storage.get("username");
	if(init == null || init == "") {
		username.focus();
	} else {
		username.val(init);
		password.focus();
	}
	
	var widget = this.widget;
	
	var submit = function() {
		
		loginButton.removeClass("active");
		loginButton.addClass("inactive");
		loginMessage.text("");
		
		Rest.post("/api/login", {username: username.val(), password: password.val()}, $A(this, function(loginInfo) {
			
			log("Login Info: ", loginInfo);
			
			loginButton.removeClass("inactive");
			loginButton.addClass("active");
			
			if(loginInfo.isSuccessful) {
				
				// If the URL is complex, simply reload now that the user has logged in.
				if(location.pathname != "/login") {
					location.reload();
					return;
				}
				
				gv.user = loginInfo.user;
				
				Storage.put("username", username.val());
				Storage.putObject("loginInfo", loginInfo);
				
				widget.remove();
				
				$(".website-header").remove();
				
				$(".spine-top").css("display", "");
				
				current = $(".menu-container");
				
				Rest.get("/api/menus", [], $A(this, function(menus) {
					new Page();
				}));
			} else {
				loginMessage.text(loginInfo.error);
			}
		}));
	}	

	loginButton.click(submit);
	password.keypress(function(e) {
		if(e.keyCode == 13) submit();
	});
	
	this.widget.find(".forgot-password").click($IA(this, function() {
		var dialog = new DialogWidget("Forgot password?", 500);
		
		new LW();
		new TextWidget("Enter your email below and we'll send you a link to change your password.")
		new LW(3);
		
		var fw = new FormWidget({
			tableWidth: "100%"
		});
		
		fw.label();
		new LabelWidget("Email");
		
		fw.value();
		fw.link("email", new InputWidget());

		fw.focus();
		fw.submitOnEnter($A(this, function() {
			updateButton.trigger();
		}));
		fw.finish();
		
		fw.setValue("email", username.val());
		
		new LW(4);

		var updateButton = new ButtonWidget("Send me link to change password", $A(this, function() {
			var parameters = {
				name : fw.getValue("email")
			};

			updateButton.loading();
			Rest.post("/api/users/forgotpassword/" + fw.getValue("email"), parameters, $A(this,
					function(result) {
						updateButton.doneLoading();
						dialog.close();

						var dialog2 = new DialogWidget("Forgot password?", 500);
						new LW(2);
						new TextWidget("Thank you. We've emailed you a link to reset your password. The link will expire in 24 hours, so please click on it soon.\n\n" +
								"If you're still facing problems logging in, please email support@sirrateam.com.")
						new LW(4);
						new ButtonWidget("Close", $A(this, function() {
							dialog2.close();
						}));
						
						dialog2.reposition();
					}));
		}));
		new SW();

		new ButtonWidget("Cancel", $A(this, function() {
			dialog.close();
		}));
				
		dialog.reposition();
	}));
}