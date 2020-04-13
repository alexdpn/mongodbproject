package com.project.mongodb.service;

import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import com.project.mongodb.repository.CompanyRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

@Singleton
public class CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Inject
    private CompanyRepository companyRepository;

    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }

    public Company getCompanyById(String id) {
        return companyRepository.getCompanyById(id);
    }

    public Office getOffice(String id) {
        return companyRepository.getOffice(id);
    }

    public Address getAddress(String id) {
        return companyRepository.getAddress(id);
    }

    public Response deleteCompany(String id){
        List<ObjectId> list = new ArrayList<>(20);
        companyRepository.getCompanies().forEach(company -> list.add(company.getId()));

        logger.info("Searching company with id {}", id);
        boolean flag = false;
        for(ObjectId objectId : list) {
            if(objectId.toString().equals(new ObjectId(id).toString()))
                flag = true;
        }

        if(!flag) {
            logger.warn("Company not found for id {}", id);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        } else {
            logger.info("Deleting company with id {}", id);;

            companyRepository.deleteCompanyById(id);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
    }

    public Response insertCompany(Company company, UriInfo uriInfo){
        logger.info("Inserting company {} ", company);

        companyRepository.insertCompany(company);

        Company company2 = companyRepository.getMongoCollection()
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

    public Response putCompany(String id, Company company){
        List<Company> list = companyRepository.getCompanies();
        boolean flag = false;

        for(Company companyFromList : list) {
            if(new ObjectId(id).toString().equals(companyFromList.getId().toString()))
                flag = true;
        }

        if(!flag) {
            logger.info("Inserting company {} ", company);

            companyRepository.insertCompany(company);

            return Response
                    .status(Response.Status.CREATED)
                    .build();
        } else {
            logger.info("Updating company with id {}", id);

            companyRepository.replaceCompany(id, company);

            return Response
                    .status(Response.Status.OK)
                    .build();
        }
    }
}
