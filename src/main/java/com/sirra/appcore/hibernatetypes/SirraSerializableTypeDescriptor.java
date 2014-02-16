package com.sirra.appcore.hibernatetypes;

import com.sirra.server.json.*;

public class SirraSerializableTypeDescriptor extends BaseStringTypeDescriptor<SirraSerializable> {
	public static final SirraSerializableTypeDescriptor INSTANCE = new SirraSerializableTypeDescriptor();

	public SirraSerializableTypeDescriptor() {
		super(SirraSerializable.class);
	}
	
	public Class ourClass() {
		return SirraSerializable.class;
	}

	public String toString(SirraSerializable value) {
		return JsonUtil.getInstance().convert(value).toString();
	}

	public SirraSerializable fromString(String string) {
		if ( string == null ) {
			return null;
		}

		return (SirraSerializable) JsonUtil.getInstance().parse(string);
	}
}
