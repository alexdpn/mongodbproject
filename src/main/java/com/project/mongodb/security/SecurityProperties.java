package com.project.mongodb.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecurityProperties {
    //the passwords should be put in environment variables but we'll keep it simple
    private String keystorePath = "src/main/resources/META-INF/security/keystore.jks";
    private String keyStorePassword = "pass123";
}
