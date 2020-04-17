package com.project.mongodb;

import com.project.mongodb.config.CompanyBinder;
import com.project.mongodb.controller.CompanyController;
import com.project.mongodb.helper.EmbeddedMongoDbHelper;
import com.sun.net.httpserver.HttpServer;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.jboss.weld.environment.se.Weld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class RestApp {

    private static final Logger logger = LoggerFactory.getLogger(RestApp.class);

    public static void main(String[] args) {
        logger.info("Configuring Jersey");
        URI uri = UriBuilder.fromUri("http://localhost/").port(4000).build();
        ResourceConfig resourceConfig = new ResourceConfig(CompanyController.class);
        resourceConfig.register(new CompanyBinder());
        resourceConfig.register(JacksonFeature.class);

        logger.info("Starting Weld");
        Weld weld = new Weld();

        logger.info("Starting embedded database");
        EmbeddedMongoDbHelper.startDatabase();

        logger.info("Starting embedded http server on port {}", uri.getPort());
        HttpServer httpServer = JdkHttpServerFactory.createHttpServer(uri, resourceConfig);
    }
}
