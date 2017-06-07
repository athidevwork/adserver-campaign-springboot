/**
 * 
 */
package com.adserver.campaign;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author User
 *
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	  @Override
	  public Response toResponse(Throwable exception) {
	    return Response.serverError().entity(exception.getMessage()).build();
	  }
}
