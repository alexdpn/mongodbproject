package com.project.mongodb.security;

import org.glassfish.grizzly.ssl.SSLContextConfigurator;

public class SecurityConfiguration {

    public static SSLContextConfigurator sslContextConfigurator() {
        SecurityProperties securityProperties = new SecurityProperties();
        SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();

        sslContextConfigurator.setKeyStoreFile(securityProperties.getKeystorePath());
        sslContextConfigurator.setKeyStorePass(securityProperties.getKeyStorePassword());

        return sslContextConfigurator;
    }
}
