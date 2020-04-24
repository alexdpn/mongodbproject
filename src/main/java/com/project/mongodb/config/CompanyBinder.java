package com.project.mongodb.config;

import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.service.CompanyService;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class CompanyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CompanyRepository.class).to(CompanyRepository.class).in(Singleton.class);
        bind(CompanyService.class).to(CompanyService.class);
    }
}
