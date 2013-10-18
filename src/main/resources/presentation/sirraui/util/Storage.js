function StorageStatic() {
	
}

StorageStatic.prototype.put = function(key, value) {
	try {
		localStorage.setItem(key, value);
	} catch(e) {
		console.log("Problem with storing key " + key + ": " + e);
	}
};

StorageStatic.prototype.get = function(key) {
	try {
		return localStorage.getItem(key);
	} catch(e) {
		console.log("Problem with retrieving key " + key + ": " + e);
		return null;
	}
};

StorageStatic.prototype.putObject = function(key, value) {
	try {
		localStorage.setItem(key, JSON.stringify(value));
	} catch(e) {
		console.log("Problem with storing key " + key + ": " + e);
	}
};

StorageStatic.prototype.getObject = function(key) {
	try {
		var str = localStorage.getItem(key);
		return JSON.parse(str);
	} catch(e) {
		console.log("Problem with retrieving object with key " + key + ": " + e);
		return this.get(key);
	}
};

StorageStatic.prototype.clear = function(key) {
	try {
		localStorage.removeItem(key);
	} catch(e) {
		// OK
	}
};

var Storage = new StorageStatic();
