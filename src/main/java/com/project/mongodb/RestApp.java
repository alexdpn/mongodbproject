package com.project.mongodb;

import com.project.mongodb.controller.CompanyController;
import com.sun.net.httpserver.HttpServer;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;

import java.net.URI;

public class RestApp {
    public static void main(String[] args) {
        //configure the embedded jdk server
        URI uri = UriBuilder.fromUri("http://localhost/").port(4000).build();
        ResourceConfig resourceConfig = new ResourceConfig(CompanyController.class);
        HttpServer httpServer = JdkHttpServerFactory.createHttpServer(uri, resourceConfig);
    }
}
