package com.sirra.appcore.hibernatetypes;

import java.util.*;

import org.hibernate.type.*;
import org.hibernate.type.descriptor.sql.*;

public class ListType extends AbstractSingleColumnStandardBasicType<List> {
	public static final ListType INSTANCE = new ListType();
	
	public ListType() {
		super( VarcharTypeDescriptor.INSTANCE, ListTypeDescriptor.INSTANCE );
	}

	public String getName() {
		return "list";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}
