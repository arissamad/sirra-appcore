package com.sirra.server.rest.annotations;

import java.lang.annotation.*;

/**
 * REST methods annotated with this don't need an authenticated user. It can be called by anyone.
 *  
 * @author aris
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicAccess {

}
