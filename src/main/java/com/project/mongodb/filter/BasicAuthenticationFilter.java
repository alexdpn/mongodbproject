package com.project.mongodb.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.util.Base64;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.HttpHeaders.WWW_AUTHENTICATE;

public class BasicAuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if(!checkBasicAuthCredentials(requestContext)) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header(WWW_AUTHENTICATE, "Basic realm=\"User Visible Realm\", charset=\"UTF-8\"")
                    .build());
        }
    }

    private boolean checkBasicAuthCredentials(ContainerRequestContext request) {
        String hashedCredentials = "Basic " + Base64.getEncoder().encodeToString("jersey:pass12".getBytes());
        String authorizationHeader = request.getHeaderString(AUTHORIZATION);

        return authorizationHeader != null && hashedCredentials.equals(authorizationHeader);
    }
}
