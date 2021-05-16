package com.project.mongodb.service;

import com.project.mongodb.exception.AddressNotFoundException;
import com.project.mongodb.exception.CompanyNotFoundException;
import com.project.mongodb.exception.OfficeNotFoundException;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public interface CompanyService {
    List<Company> getCompanies();

    Company getCompanyById(String id) throws CompanyNotFoundException;

    Office getOffice(String id) throws OfficeNotFoundException;

    Address getAddress(String id) throws AddressNotFoundException;

    Response deleteCompany(String id) throws CompanyNotFoundException;

    Response insertCompany(Company company, UriInfo uriInfo);

    Response putCompany(String id, Company company);
}
