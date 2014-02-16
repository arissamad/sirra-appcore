package com.sirra.appcore.hibernatetypes;

import org.hibernate.type.descriptor.*;
import org.hibernate.type.descriptor.java.*;

import com.sirra.appcore.util.*;

/**
 * Many of our custom types are simply strings. This handles some of the boiler plate.
 * 
 * @author aris
 */
public abstract class BaseStringTypeDescriptor<T> extends AbstractTypeDescriptor<T> {

	public BaseStringTypeDescriptor(Class<T> ourClass) {
		super(ourClass);
	}
	
	public abstract Class<T> ourClass();
	
	@SuppressWarnings({ "unchecked" })
	public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if ( ourClass().isAssignableFrom( type ) ) {
			return (X) value;
		}
		if ( String.class.isAssignableFrom( type ) ) {
			return (X) toString( value );
		}
		throw unknownUnwrap( type );
	}

	public <X> T wrap(X value, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if ( ourClass().isInstance( value ) ) {
			return (T) value;
		}
		if ( String.class.isInstance( value ) ) {
			return fromString( (String)value );
		}
		throw unknownWrap( value.getClass() );
	}
}
