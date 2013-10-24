function DurationUtilStatic() {	
}

DurationUtilStatic.prototype.getPretty = function(numHours) {
	var hasSome = false;
	var str = "";
	
	var hour = Math.floor(numHours);
	if(hour > 1) {
		str += moment.duration(hour, "hours").humanize() + " ";
		hasSome = true;
	}
	
	var balance = moment.duration(numHours - hour, "hours");
	
	var balanceRound = Math.round(balance.asMinutes());
	if(balanceRound == 1) {
		str += " 1 minute";
	} else if(balanceRound >= 1) {
		str += balanceRound + " minutes";
		hasSome = true;
	}
	
	if(!hasSome) str = "0";
	
	return str;
};

var DurationUtil = new DurationUtilStatic();