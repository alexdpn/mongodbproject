package com.project.mongodb.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

import com.project.mongodb.helper.MongoDbHelper;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Sorts.*;

@Path("/app")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final MongoDbHelper mongo = new MongoDbHelper();

    @GET
    @Path("/companies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Company> getCompanies() {
        return mongo.getCompanies();
    }

    @GET
    @Path("/companies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Company getCompanyById(@PathParam("id") String id){
        return mongo.getCompanyById(id);
    }

    @GET
    @Path("/companies/{id}/office")
    @Produces(MediaType.APPLICATION_JSON)
    public Office getOffice(@PathParam("id") String id){
        return mongo.getOffice(id);
    }

    @GET
    @Path("/companies/{id}/office/address")
    @Produces(MediaType.APPLICATION_JSON)
    public Address getAddress(@PathParam("id") String id){
        return mongo.getAddress(id);
    }

    @DELETE
    @Path("/companies/{id}")
    public Response deleteCompany(@PathParam("id") String id){
        List<ObjectId> list = new ArrayList<>(20);
        mongo.getCompanies().forEach(company -> list.add(company.getId()));

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

            mongo.deleteCompanyById(id);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
    }

    @POST
    @Path("/companies")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCompany(Company company, @Context UriInfo uriInfo){
        logger.info("Inserting company {} ", company);

        mongo.insertCompany(company);

        Company company2 = mongo.getMongoCollection()
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

    @PUT
    @Path("/companies/{id}")
    public Response putCompany(@PathParam("id") String id, Company company){
        List<Company> list = mongo.getCompanies();
        boolean flag = false;

        for(Company companyFromList : list) {
            if(new ObjectId(id).toString().equals(companyFromList.getId().toString()))
                flag = true;
        }

        if(!flag) {
            logger.info("Inserting company {} ", company);

            mongo.insertCompany(company);

            return Response
                    .status(Response.Status.CREATED)
                    .build();
        } else {
            logger.info("Updating company with id {}", id);

            mongo.replaceCompany(id, company);

            return Response
                    .status(Response.Status.OK)
                    .build();
        }
    }
}
