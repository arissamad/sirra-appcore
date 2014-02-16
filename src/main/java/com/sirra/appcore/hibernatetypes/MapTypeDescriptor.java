package com.sirra.appcore.hibernatetypes;

import java.util.*;

import org.json.simple.*;

import com.sirra.server.json.*;

public class MapTypeDescriptor extends BaseStringTypeDescriptor<Map> {
	public static final MapTypeDescriptor INSTANCE = new MapTypeDescriptor();

	public MapTypeDescriptor() {
		super(Map.class);
	}
	
	public Class ourClass() {
		return Map.class;
	}

	public String toString(Map value) {
		return JsonUtil.getInstance().convert(value).toString();
	}

	public Map fromString(String string) {
		if ( string == null ) {
			return null;
		}

		JSONObject json = (JSONObject) JsonUtil.getInstance().parse(string);
		Map map = new HashMap(json);
		return map;
	}
}