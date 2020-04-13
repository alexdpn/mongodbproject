package com.project.mongodb.config;

import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.service.CompanyService;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class CompanyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CompanyRepository.class).to(CompanyRepository.class);
        bind(CompanyService.class).to(CompanyService.class);
    }
}
