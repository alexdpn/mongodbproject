package com.project.mongodb.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.HttpHeaders.WWW_AUTHENTICATE;
import static org.apache.commons.io.IOUtils.toByteArray;

@Slf4j
public class SecurityConfiguration {

    public static class HttpsConfiguration {

        public static SSLContextConfigurator sslContextConfigurator() {
            SecurityProperties securityProperties = new SecurityProperties();
            SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
            byte[] keyStoreAsByteArray = getKeystoreAsByteArray(securityProperties.getKeystorePath());

            sslContextConfigurator.setKeyStoreBytes(keyStoreAsByteArray);
            sslContextConfigurator.setKeyStorePass(securityProperties.getKeyStorePassword());

            return sslContextConfigurator;
        }

        private static byte[] getKeystoreAsByteArray(String path) {
            try (InputStream inputStream = SecurityConfiguration.class.getResourceAsStream(path)) {
                return toByteArray(inputStream);
            } catch (IOException exception) {
                log.error("Error while processing keystore");
                return null;
            }
        }

        @Data
        @NoArgsConstructor
        private static class SecurityProperties {
            //the password should be put in a properties file but we'll keep it simple
            private String keystorePath = "/META-INF/security/keystore.jks";
            private String keyStorePassword = "pass123";
        }
    }

    public static class BasicAuthenticationFilter implements ContainerRequestFilter {

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
            // the credentials should be placed in a properties file but we'll keep it simple here
            String hashedCredentials = "Basic " + Base64.getEncoder().encodeToString("jersey:pass12".getBytes());
            String authorizationHeader = request.getHeaderString(AUTHORIZATION);

            return hashedCredentials.equals(authorizationHeader);
        }
    }

}
