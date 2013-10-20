/**
 * parameters can be array or object.
 *  - If array, it will map to the java parameters.
 *  - If object, it will use regular REST-style query parameters.
 */
function RestStatic() {
}

RestStatic.prototype.get = function(path, parameters, action) {
	this.call("GET", path, parameters, action);
};

RestStatic.prototype.post = function(path, parameters, action) {
	this.call("POST", path, parameters, action);
};

RestStatic.prototype.put = function(path, parameters, action) {
	this.call("PUT", path, parameters, action);
};

RestStatic.prototype.delete = function(path, parameters, action) {
	this.call("DELETE", path, parameters, action);
};

RestStatic.prototype.call = function(httpMethod, path, parameters, action) {
	var data = {};
	
	if(parameters instanceof Array) {
		data.parameters = JSON.stringify(parameters);
	} else {
		for(var attr in parameters) {
			var value = parameters[attr];
			
			var type = typeof(value);
			
			if(type == "string" || type == "number" || type == "boolean") {
				data[attr] = parameters[attr];
			} else {
				data[attr] = JSON.stringify(parameters[attr]);	
			}
		}
	}
	
	$.ajax({
	    type: httpMethod.toUpperCase(),
	    data: data,
	    dataType: "text",
	    url: path,
	    success: function(data) {
	    	var result = JSON.parse(data, function(key, value) {
	    		if(value != null && value.type != null) {
	    			var materializedObject = new window[value.type];
	    			
	    			for(var attr in value) {
	    				materializedObject[attr] = value[attr];
	    			}
	    			return materializedObject;
	    		}
	    		return value;
	    	});
	    	action.call(result);
	    },
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	        console.log("REST Error:" , errorThrown.message);
	    }
	});
};

var Rest = new RestStatic();