function MessageDialog(title, message) {
	var dialog = new DialogWidget(title, 600);
	new TextWidget(message);
	dialog.buttons();
	
	var updateButton = new ButtonWidget("Close", $A(this, function() {
		dialog.close();
	}));
	
	dialog.reposition();
}