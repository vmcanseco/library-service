/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.javaee8.integration;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author VMC027
 */

@Provider
public class PersistenceExceptionManager implements ExceptionMapper<PersistenceException> {
    
    public Response toResponse(PersistenceException exception){
        if (exception instanceof EntityNotFoundException){
            return Response.status(Response.Status.NOT_FOUND).build();
        }else{
            Map<String,String> response=new HashMap<>();
            response.put("code","ERR-GENERAL");
            response.put("type","DATABASE");
            response.put("message",exception.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
}
