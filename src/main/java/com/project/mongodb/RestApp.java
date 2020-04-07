package com.project.mongodb;

import com.project.mongodb.controller.CompanyController;
import com.sun.net.httpserver.HttpServer;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class RestApp {

    private static final Logger logger = LoggerFactory.getLogger(RestApp.class);

    public static void main(String[] args) {
        //configure the embedded jdk server
        URI uri = UriBuilder.fromUri("http://localhost/").port(4000).build();
        ResourceConfig resourceConfig = new ResourceConfig(CompanyController.class);

        logger.info("Starting embedded http server on port {}", uri.getPort());
        HttpServer httpServer = JdkHttpServerFactory.createHttpServer(uri, resourceConfig);
    }
}
