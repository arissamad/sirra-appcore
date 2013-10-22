function DateUtilStatic() {
	
}

/**
 * Returns a friendly date string.
 */
DateUtilStatic.prototype.toDate = function(inDate) {
	if(inDate instanceof Date) {
		return moment(inDate).format('L');	
	} else {
		// Moment can handle ms!
		return moment(inDate).format('L');
	}
};

DateUtilStatic.prototype.toTime = function(inDate) {
	if(inDate instanceof Date) {
		return moment(inDate).format('LT');	
	} else {
		// Moment can handle ms!
		return moment(inDate).format('LT');
	}
};

DateUtilStatic.prototype.toDateAndTime = function(inDate) {
	return this.toDate(inDate) + " " + this.toTime(inDate);
};

var DateUtil = new DateUtilStatic();