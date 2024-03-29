package com.project.mongodb;

import javax.ws.rs.core.UriBuilder;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import java.net.URI;

import com.project.mongodb.config.CompanyBinder;
import com.project.mongodb.controller.CompanyController;
import com.project.mongodb.helper.EmbeddedMongoDbHelper;

import static com.project.mongodb.util.Constants.PORT;
import static com.project.mongodb.util.Constants.HTTPS_HOST;
import static com.project.mongodb.security.SecurityConfiguration.BasicAuthenticationFilter;
import static com.project.mongodb.security.SecurityConfiguration.HttpsConfiguration.sslContextConfigurator;

@Slf4j
public class RestApp {

    public static void main(String[] args) throws Exception {
        log.info("Configuring Jersey");
        URI uri = UriBuilder.fromUri(HTTPS_HOST).port(PORT).build();
        ResourceConfig resourceConfig = new ResourceConfig(CompanyController.class);
        resourceConfig.register(new CompanyBinder())
                .register(DeclarativeLinkingFeature.class)
                .register(JacksonFeature.class)
                .register(BasicAuthenticationFilter.class);

        log.info("Starting embedded database");
        EmbeddedMongoDbHelper.startDatabase();

        log.info("Starting embedded https server on port {}", uri.getPort());
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
                uri,
                resourceConfig,
                true,
                new SSLEngineConfigurator(sslContextConfigurator())
                        .setClientMode(false)
                        .setNeedClientAuth(false)
        );
    }
}
