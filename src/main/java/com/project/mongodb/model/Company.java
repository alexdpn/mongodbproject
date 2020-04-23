package com.project.mongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.mongodb.config.JsonConfiguration;
import com.project.mongodb.controller.CompanyController;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import static org.glassfish.jersey.linking.InjectLink.Style;

import javax.ws.rs.core.Link;
import java.util.List;

public class Company {

    @JsonSerialize(using = JsonConfiguration.ObjectIdSerializer.class)
    private ObjectId id;

    private String name;
    private String ceo;
    private String areaOfActivity;
    private Office office;

    @InjectLinks({
            @InjectLink(
                    resource= CompanyController.class,
                    method="getCompanyById",
                    style= Style.ABSOLUTE,
                    rel="self"),
            @InjectLink(
                    resource= CompanyController.class,
                    method="getOffice",
                    style= Style.ABSOLUTE,
                    rel="self"),
            @InjectLink(
                    resource= CompanyController.class,
                    method="getAddress",
                    style= Style.ABSOLUTE,
                    rel="self")})
    @BsonIgnore
    @JsonIgnoreProperties({ "uriBuilder", "params", "type", "rels", "title"})
    private List<Link> links;

    public Company(){}

    public Company(String name, String ceo, String areaOfActivity, Office office) {
        this.name = name;
        this.ceo = ceo;
        this.areaOfActivity = areaOfActivity;
        this.office = office;
    }

    public ObjectId getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCeo(){
        return this.ceo;
    }

    public String getAreaOfActivity() {
        return this.areaOfActivity;
    }

    public Office getOffice(){
        return this.office;
    }

    public void setId(ObjectId id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCeo(String ceo){
        this.ceo = ceo;
    }

    public void setAreaOfActivity(String areaOfActivity){
        this.areaOfActivity = areaOfActivity;
    }

    public void setOffice(Office office){
        this.office = office;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString(){
        return "id: " + this.id
                + ", name: " + this.name
                + ", ceo: " + this.ceo
                + ", areaOfActivity: " + this.areaOfActivity
                + ", office: " + this.office;
    }

}
