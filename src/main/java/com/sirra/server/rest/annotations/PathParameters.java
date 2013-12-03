package com.sirra.server.rest.annotations;

import java.lang.annotation.*;
import java.util.*;

/**
 * We want to support REST path parameters with more than 1 parameter.
 * Use this to define the mapping between path parameter and java method parameter. (The java method parameter is
 * defined with the @Parameters annotation).
 * 
 * For example, for the path /{studentId}/homework/{homeworkId}:
 * 
 *   @PathParameters({"studentId", "homework", "homeworkId"})
 * 
 * @author aris
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PathParameters {
	String[] value();
}
