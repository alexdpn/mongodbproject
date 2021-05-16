package com.project.mongodb.service.impl;

import com.project.mongodb.exception.AddressNotFoundException;
import com.project.mongodb.exception.CompanyNotFoundException;
import com.project.mongodb.exception.OfficeNotFoundException;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import com.project.mongodb.model.error.ResponseMessage;
import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

@Slf4j
public class CompanyServiceImpl implements CompanyService {
    
    private final CompanyRepository companyRepository;
    
    @Inject
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }

    @Override
    public Company getCompanyById(String id) throws CompanyNotFoundException {
        Company company = companyRepository.getCompanyById(id);

        if(company == null)
            throw new CompanyNotFoundException(new ResponseMessage(id, "Company with id " + id + " was not found on this server"));

        return company;
    }

    @Override
    public Office getOffice(String id) throws OfficeNotFoundException {
        Office office = companyRepository.getOfficeByCompanyId(id);

        if(office == null)
            throw new OfficeNotFoundException(new ResponseMessage(id, "Office was not found for company with id " + id));

        return office;
    }

    @Override
    public Address getAddress(String id) throws AddressNotFoundException {
        Address address = companyRepository.getAddressByCompanyId(id);

        if(address == null)
            throw new AddressNotFoundException(new ResponseMessage(id, "Address was not found for company with id " + id));

        return companyRepository.getAddressByCompanyId(id);
    }

    @Override
    public Response deleteCompany(String id) throws CompanyNotFoundException {
        List<ObjectId> list = new ArrayList<>(20);
        companyRepository.getCompanies().forEach(company -> list.add(company.getId()));

        log.info("Searching company with id {}", id);
        boolean flag = false;
        for(ObjectId objectId : list) {
            if(objectId.toString().equals(new ObjectId(id).toString()))
                flag = true;
        }

        if(!flag) {
            log.info("Company not found for id {}", id);
            throw new CompanyNotFoundException(new ResponseMessage(id, "Company with id " + id + " was not found on this server"));
        } else {
            log.info("Deleting company with id {}", id);;
            companyRepository.deleteCompanyById(id);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
    }

    @Override
    public Response insertCompany(Company company, UriInfo uriInfo){
        log.info("Inserting company {} ", company);

        companyRepository.insertCompany(company);

        Company company2 =  companyRepository.getMongoCollection()
                .find()
                .sort(descending("_id"))
                .limit(1)
                .first();

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(company2.getId().toString())
                .build();

        return Response
                .status(Response.Status.CREATED)
                .location(uri)
                .build();

    }

    @Override
    public Response putCompany(String id, Company company){
        List<Company> list = companyRepository.getCompanies();
        boolean flag = false;

        for(Company companyFromList : list) {
            if(new ObjectId(id).toString().equals(companyFromList.getId().toString()))
                flag = true;
        }

        if(!flag) {
            log.info("Inserting company {} ", company);

            companyRepository.insertCompany(company);

            return Response
                    .status(Response.Status.CREATED)
                    .build();
        } else {
            log.info("Updating company with id {}", id);

            companyRepository.replaceCompany(id, company);

            return Response
                    .status(Response.Status.OK)
                    .build();
        }
    }
}
