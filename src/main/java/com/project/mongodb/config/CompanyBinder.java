package com.project.mongodb.config;

import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.repository.impl.CompanyRepositoryImpl;
import com.project.mongodb.service.CompanyService;
import com.project.mongodb.service.impl.CompanyServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class CompanyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CompanyRepositoryImpl.class).to(CompanyRepository.class).in(Singleton.class);
        bind(CompanyServiceImpl.class).to(CompanyService.class);
    }
}
