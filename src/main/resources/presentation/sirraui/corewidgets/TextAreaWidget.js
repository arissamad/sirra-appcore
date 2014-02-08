/**
 * Example settings: {
 *  isGrowable: true,
 *  maxHeight: 200
 * }
 */
function TextAreaWidget(text, settings) {
	ClassUtil.mixin(TextAreaWidget, this, Widget);
	Widget.call(this, "TextAreaWidget", true, settings);
	
	if(this.settings.has("css")) {
		this.widget.find("textarea").css(this.settings.get("css"));
	}
	
	if(text != null) this.setValue(text);
	
	this.isGrowable = this.settings.get("isGrowable", true);
	
	if(this.isGrowable == true) {
		this.maxHeight = this.settings.get("maxHeight", 150);
		
		this.input.keyup($IA(this, "onChange"));
	}
}

TextAreaWidget.prototype.onChange = function(e) {
	if(this.isGrowable == true) {
		if(this.input.height() >= this.maxHeight) {
			return;
		}
		
		if(this.input[0].scrollHeight > (this.input.innerHeight() + 5)) { // Buffer of 10
			var padding = this.input.innerHeight() - this.input.height();
			
			var newHeight = this.input[0].scrollHeight - padding;
			
			if(newHeight > this.maxHeight) newHeight = this.maxHeight;
			
			this.input.height(newHeight);
		}
	}
};

TextAreaWidget.prototype.setValue = function() {
	Widget.prototype.setValue.apply(this, arguments);
	
	this.onChange();
};