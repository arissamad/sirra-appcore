function TemplateStatic() {}

/**
 * You can specify as many key/value pairs as you want in a sequence.
 */
TemplateStatic.prototype.replace = function(template, key, value) {
	for(var i=1; i<arguments.length; i+=2) {
		var key = arguments[i];
		var value = arguments[i+1];
		
		var re = new RegExp("\\${" + key + "}", "g");
		template = template.replace(re, value);
	}
	return template;
};

var Template = new TemplateStatic();