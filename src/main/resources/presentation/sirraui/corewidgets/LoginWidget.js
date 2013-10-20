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
				gv.user = loginInfo.user;
				
				Storage.put("username", username.val());
				Storage.putObject("loginInfo", loginInfo);
				
				widget.remove();
				$(".spine-top").show();
				
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
}