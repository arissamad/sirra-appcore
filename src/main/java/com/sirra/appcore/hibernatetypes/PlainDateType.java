package com.sirra.appcore.hibernatetypes;

import org.hibernate.type.*;
import org.hibernate.type.descriptor.sql.*;

import com.sirra.appcore.util.*;

public class PlainDateType extends AbstractSingleColumnStandardBasicType<PlainDate> {
	public static final PlainDateType INSTANCE = new PlainDateType();
	
	public PlainDateType() {
		super( VarcharTypeDescriptor.INSTANCE, PlainDateTypeDescriptor.INSTANCE );
	}

	public String getName() {
		return "plaindate";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}
