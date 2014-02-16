package com.sirra.appcore.hibernatetypes;

import com.sirra.appcore.util.*;

public class PlainDateTypeDescriptor extends BaseStringTypeDescriptor<PlainDate> {
	public static final PlainDateTypeDescriptor INSTANCE = new PlainDateTypeDescriptor();

	public PlainDateTypeDescriptor() {
		super(PlainDate.class);
	}
	
	public Class ourClass() {
		return PlainDate.class;
	}

	public String toString(PlainDate value) {
		return value.toString();
	}

	public PlainDate fromString(String string) {
		if ( string == null ) {
			return null;
		}

		return new PlainDate(string);
	}
}