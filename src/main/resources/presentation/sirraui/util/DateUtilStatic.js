function DateUtilStatic() {
	
}

/**
 * Returns a friendly date string. Can handle Date, plain-date string, and ms.
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

DateUtilStatic.prototype.toPlainDate = function(inDate) {
	return moment(inDate).format("YYYY-MM-DD");
};

var DateUtil = new DateUtilStatic();