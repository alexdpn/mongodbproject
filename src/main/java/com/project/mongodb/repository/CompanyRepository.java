package com.project.mongodb.repository;

import java.util.List;

import com.mongodb.client.MongoCollection;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;

public interface CompanyRepository {
    void init();

    MongoCollection<Company> getMongoCollection();

    List<Company> getCompanies();

    Company getCompanyById(String id);

    Office getOfficeByCompanyId(String id);

    Address getAddressByCompanyId(String id);

    void deleteCompanyById(String id);

    void insertCompany(Company company);

    void replaceCompany(String id, Company company);

    void cleanUp();
}
