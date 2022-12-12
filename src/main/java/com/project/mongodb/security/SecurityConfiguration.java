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
import java.nio.charset.StandardCharsets;
import java.util.Base64;;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.HttpHeaders.WWW_AUTHENTICATE;
import static org.apache.commons.io.IOUtils.toByteArray;

@Slf4j
public class SecurityConfiguration {

    public static class HttpsConfiguration {

        public static SSLContextConfigurator sslContextConfigurator() throws IOException {
            SecurityProperties securityProperties = new SecurityProperties();
            SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
            byte[] keyStoreAsByteArray = getKeystoreAsByteArray(securityProperties.getKeystorePath());

            sslContextConfigurator.setKeyStoreBytes(keyStoreAsByteArray);
            sslContextConfigurator.setKeyStorePass(securityProperties.getKeyStorePassword());

            return sslContextConfigurator;
        }

        private static byte[] getKeystoreAsByteArray(String path) throws IOException {
            try (InputStream inputStream = SecurityConfiguration.class.getResourceAsStream(path)) {
                return toByteArray(inputStream);
            } catch (IOException exception) {
                log.error("Error while processing keystore");
                throw exception;
            }
        }

        @Data
        @NoArgsConstructor
        private static class SecurityProperties {
            private String keystorePath = "/META-INF/security/keystore.jks";
            //the password should be placed in a properties file or in Vault but we'll keep it simple; passwords do not belong in code!!
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
            // the hashed credentials should be placed in a properties file or in Vault but we'll keep it simple; credentials do not belong in code!!
            String hashedCredentials = "Basic amVyc2V5OnBhc3MxMg==";
            String authorizationHeader = request.getHeaderString(AUTHORIZATION);
            String incomingCredentials = Base64.getEncoder().encodeToString(authorizationHeader.getBytes(StandardCharsets.UTF_8));

            return hashedCredentials.equals(incomingCredentials);
        }
    }

}
