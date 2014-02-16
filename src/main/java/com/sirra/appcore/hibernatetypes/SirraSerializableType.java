package com.sirra.appcore.hibernatetypes;

import org.hibernate.type.*;
import org.hibernate.type.descriptor.sql.*;

import com.sirra.server.json.*;

public class SirraSerializableType extends AbstractSingleColumnStandardBasicType<SirraSerializable> {
public static final SirraSerializableType INSTANCE = new SirraSerializableType();
	
	public SirraSerializableType() {
		super( VarcharTypeDescriptor.INSTANCE, SirraSerializableTypeDescriptor.INSTANCE );
	}

	public String getName() {
		return "sirraserializable";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}