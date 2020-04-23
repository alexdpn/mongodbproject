package com.project.mongodb.exception;

import com.project.mongodb.model.error.Error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CompanyNotFoundException extends WebApplicationException {
    public CompanyNotFoundException(Error  message) {
        super(Response.status(Response.Status.NOT_FOUND)
                       .entity(message)
                       .type(MediaType.APPLICATION_JSON_TYPE)
                       .build());
    }
}
