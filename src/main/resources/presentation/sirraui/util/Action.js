function Action(actionObject, actionMethod, params) {
	
	var theArguments = arguments;
	if(theArguments.length == 0) {
		return;
	}
	
	if(theArguments[0] instanceof Array) {
		// The "arguments" array was passed in via $A
		theArguments = actionObject;	
	}
	
	this.actionObject = theArguments[0];
	this.actionMethod = theArguments[1];
	
	this.args = [];
	for(var i=2; i<theArguments.length; i++) {
		this.args[this.args.length] = theArguments[i];
	}
};

Action.prototype.call = function(postParameters) {
	
	if(this.actionObject == null) {
		return;
	}
	var theArgs = Array.prototype.slice.call(this.args);
	
	for(var i=0; i<arguments.length; i++) {
		theArgs[theArgs.length] = arguments[i];
	}
	
	if(isFunction(this.actionMethod)) {
		return this.actionMethod.apply(this.actionObject, theArgs);
	} else {
		if(this.actionObject[this.actionMethod] == null) {
			throw new Error("Action: Method \"" + this.actionMethod + "\" does not exist on reference object: ", this.actionObject);
		}
		return this.actionObject[this.actionMethod].apply(this.actionObject, theArgs);
	}
}

function $A(actionObject, actionMethod, params) {
	return new Action(Array.prototype.slice.call(arguments));
}

// Inline Action: For JQuery methods that only take a function argument without context.
function $IA(actionObject, actionMethod, params) {
	
	var action = new Action(Array.prototype.slice.call(arguments));
	return function() {
		var paramArray = Array.prototype.slice.call(arguments);
		action.call.apply(action, paramArray);
	}
}