package com.sirra.appcore.hibernatetypes;

import java.util.*;

import org.hibernate.type.*;
import org.hibernate.type.descriptor.sql.*;

public class MapType extends AbstractSingleColumnStandardBasicType<Map> {
public static final MapType INSTANCE = new MapType();
	
	public MapType() {
		super( VarcharTypeDescriptor.INSTANCE, MapTypeDescriptor.INSTANCE );
	}

	public String getName() {
		return "map";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}
