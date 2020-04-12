package com.project.mongodb.config;

import com.project.mongodb.helper.MongoDBRepository;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class CompanyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(MongoDBRepository.class).to(MongoDBRepository.class);
    }
}
