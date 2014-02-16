package com.sirra.appcore.hibernatetypes;

import java.util.*;

import org.hibernate.type.descriptor.java.*;

import com.sirra.appcore.util.*;
import com.sirra.server.json.*;

public class ListTypeDescriptor extends BaseStringTypeDescriptor<List> {
	public static final ListTypeDescriptor INSTANCE = new ListTypeDescriptor();

	public ListTypeDescriptor() {
		super(List.class);
	}
	
	public Class ourClass() {
		return List.class;
	}

	public String toString(List value) {
		return JsonUtil.getInstance().convert(value).toString();
	}

	public List fromString(String string) {
		if ( string == null ) {
			return null;
		}

		return (List) JsonUtil.getInstance().parse(string);
	}
}