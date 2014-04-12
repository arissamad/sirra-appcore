package com.sirra.appcore.hibernatetypes;

import org.hibernate.internal.util.compare.*;

import com.sirra.server.json.*;

public class SirraSerializableTypeDescriptor<SirraSerializable> extends BaseStringTypeDescriptor<SirraSerializable> {

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
	
	@Override
	public boolean areEqual(SirraSerializable one, SirraSerializable another) {
		if(one != null && another != null && one instanceof SirraPersistentSerializable && another instanceof SirraPersistentSerializable) {
			SirraPersistentSerializable sps = (SirraPersistentSerializable) one;
			return !sps.getIsDirty();
		} else {
			return EqualsHelper.equals( one, another );
		}
	}
}