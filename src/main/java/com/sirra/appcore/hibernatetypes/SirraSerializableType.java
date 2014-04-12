package com.sirra.appcore.hibernatetypes;

import org.hibernate.*;
import org.hibernate.engine.spi.*;
import org.hibernate.internal.util.compare.*;
import org.hibernate.type.*;
import org.hibernate.type.descriptor.sql.*;

import com.sirra.server.json.*;

public class SirraSerializableType extends AbstractSingleColumnStandardBasicType<SirraSerializable> {

	protected String key;
	
	public SirraSerializableType(Class sirraSerializableImplementationClass) {
		super( VarcharTypeDescriptor.INSTANCE, new SirraSerializableTypeDescriptor(sirraSerializableImplementationClass));
		key = sirraSerializableImplementationClass.getName();
	}

	public String getName() {
		return key;
	}
	
	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}