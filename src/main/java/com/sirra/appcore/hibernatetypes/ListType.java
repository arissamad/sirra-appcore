package com.sirra.appcore.hibernatetypes;

import java.util.*;

import org.hibernate.type.*;
import org.hibernate.type.descriptor.sql.*;

/**
 * Be careful! We haven't yet implemented a custom "equals()" method, so the same list with updated items will NOT 
 * save to the DB!
 * 
 * @author aris
 *
 */
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
