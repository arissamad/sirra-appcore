package com.sirra.server.rest.annotations;

import java.lang.annotation.*;

/**
 * If specified, only the specified attributes will be returned. Otherwise,
 * all attributes will be returned.
 * 
 * Additional attributes can be returned by specifying the "fields" query parameter in your GET request.
 * 
 * @author aris
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultAttributes {
	String[] value();
}
