package com.project.mongodb.security;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;

import java.io.IOException;
import java.io.InputStream;;

import static org.apache.commons.io.IOUtils.toByteArray;

@Slf4j
public class SecurityConfiguration {

    public static SSLContextConfigurator sslContextConfigurator() {
        SecurityProperties securityProperties = new SecurityProperties();
        SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
        byte[] keyStoreAsByteArray = getKeystoreAsByteArray(securityProperties.getKeystorePath());

        sslContextConfigurator.setKeyStoreBytes(keyStoreAsByteArray);
        sslContextConfigurator.setKeyStorePass(securityProperties.getKeyStorePassword());

        return sslContextConfigurator;
    }

    private static byte[] getKeystoreAsByteArray(String path) {
        try(InputStream inputStream = SecurityConfiguration.class.getResourceAsStream(path)) {
            return toByteArray(inputStream);
        } catch(IOException exception) {
            log.error("Error while processing keystore");
            return null;
        }
    }
}
