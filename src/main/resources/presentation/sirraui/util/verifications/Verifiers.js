$V = function(name) {
	var checkers = [];
	for(var i=1; i<arguments.length; i++) {
		checkers.push(arguments[i]);
	}
	
	return {
		name: name,
		checkers: checkers
	};
}

var v = {};

v.notEmpty = function() {
	return v.notEmpty;
}

v.notEmpty.verify = function(value) {
	if(value == null || value == "") return "Cannot be left blank.";
	return true;
}

v.int = function() {
	return v.int;
}

v.int.verify = function(value) {
	if(value == null || value == "") return true;
	if(isNaN(parseInt(value, 10)) ==  true) return "You must enter a number with no decimals.";
	if(parseFloat(value, 10) == parseInt(value,10)) return true;
	else return "You must enter a number with no decimals."; 
}

v.number = function() {
	return v.number;
}

v.number.verify = function(value) {
	if(value == null || value == "") return true;
	var num = Number(value);
	if(isNaN(num)) return "You must enter a number.";
	else return true;
}

v.lessThan = function(comparisonValue) {
	return {
		verify: function(value) {
			if(value < comparisonValue) return true;
			else return "The number must be less than " + comparisonValue;
		}
	};
}

v.lessThanOrEquals = function(comparisonValue) {
	return {
		verify: function(value) {
			if(value <= comparisonValue) return true;
			else return "The number must be less than or equals to " + comparisonValue;
		}
	};
}

v.moreThan = function(comparisonValue) {
	return {
		verify: function(value) {
			if(value > comparisonValue) return true;
			else return "The number must be more than " + comparisonValue;
		}
	};
}

v.moreThanOrEquals = function(comparisonValue) {
	return {
		verify: function(value) {
			if(value >= comparisonValue) return true;
			else return "The number must be more than or equals to " + comparisonValue;
		}
	};
}