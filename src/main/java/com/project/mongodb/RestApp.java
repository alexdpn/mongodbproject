package com.project.mongodb;

import com.project.mongodb.config.CompanyBinder;
import com.project.mongodb.controller.CompanyController;
import com.project.mongodb.helper.EmbeddedMongoDbHelper;

import javax.ws.rs.core.UriBuilder;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.glassfish.grizzly.http.server.HttpServer;

import java.net.URI;

@Slf4j
public class RestApp {

    public static void main(String[] args) {
        log.info("Configuring Jersey");
        URI uri = UriBuilder.fromUri("http://localhost/").port(4000).build();
        ResourceConfig resourceConfig = new ResourceConfig(CompanyController.class);
        resourceConfig.register(new CompanyBinder());
        resourceConfig.register(DeclarativeLinkingFeature.class);
        resourceConfig.register(JacksonFeature.class);

        log.info("Starting embedded database");
        EmbeddedMongoDbHelper.startDatabase();

        log.info("Starting embedded http server on port {}", uri.getPort());
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig);
    }
}
