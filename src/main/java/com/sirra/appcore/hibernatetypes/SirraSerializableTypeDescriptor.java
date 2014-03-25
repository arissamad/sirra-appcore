package com.sirra.appcore.hibernatetypes;

import com.sirra.server.json.*;

public class SirraSerializableTypeDescriptor extends BaseStringTypeDescriptor<SirraSerializable> {

	protected Class sirraSerializableImplementationClass;
	
	public SirraSerializableTypeDescriptor(Class sirraSerializableImplementationClass) {
		super(sirraSerializableImplementationClass);
		this.sirraSerializableImplementationClass = sirraSerializableImplementationClass;
	}
	
	public Class ourClass() {
		return sirraSerializableImplementationClass;
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