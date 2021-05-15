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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import com.project.mongodb.service.CompanyService;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/app/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Inject
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Company getCompanyById(@PathParam("id") String id){
        return companyService.getCompanyById(id);
    }

    @GET
    @Path("{id}/office")
    @Produces(APPLICATION_JSON)
    public Office getOffice(@PathParam("id") String id){
        return companyService.getOffice(id);
    }

    @GET
    @Path("{id}/office/address")
    @Produces(APPLICATION_JSON)
    public Address getAddress(@PathParam("id") String id){
        return companyService.getAddress(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCompany(@PathParam("id") String id){
        return companyService.deleteCompany(id);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response insertCompany(Company company, @Context UriInfo uriInfo){
        return companyService.insertCompany(company, uriInfo);
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    public Response putCompany(@PathParam("id") String id, Company company){
        return companyService.putCompany(id, company);
    }
}
