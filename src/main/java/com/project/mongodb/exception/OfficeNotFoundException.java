package com.project.mongodb.exception;

import com.project.mongodb.model.error.Error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class OfficeNotFoundException extends WebApplicationException {
    public OfficeNotFoundException(Error message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message)
                .type(APPLICATION_JSON_TYPE)
                .build());
    }
}
