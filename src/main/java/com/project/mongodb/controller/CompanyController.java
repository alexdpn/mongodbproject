package com.project.mongodb.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import com.project.mongodb.service.CompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/app")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Inject
    private CompanyService companyService;

    @GET
    @Path("/companies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @GET
    @Path("/companies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Company getCompanyById(@PathParam("id") String id){
        return companyService.getCompanyById(id);
    }

    @GET
    @Path("/companies/{id}/office")
    @Produces(MediaType.APPLICATION_JSON)
    public Office getOffice(@PathParam("id") String id){
        return companyService.getOffice(id);
    }

    @GET
    @Path("/companies/{id}/office/address")
    @Produces(MediaType.APPLICATION_JSON)
    public Address getAddress(@PathParam("id") String id){
        return companyService.getAddress(id);
    }

    @DELETE
    @Path("/companies/{id}")
    public Response deleteCompany(@PathParam("id") String id){
        return companyService.deleteCompany(id);
    }

    @POST
    @Path("/companies")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCompany(Company company, @Context UriInfo uriInfo){
        return companyService.insertCompany(company, uriInfo);
    }

    @PUT
    @Path("/companies/{id}")
    public Response putCompany(@PathParam("id") String id, Company company){
        return companyService.putCompany(id, company);
    }
}
