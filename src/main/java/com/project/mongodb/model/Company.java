package com.project.mongodb.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.mongodb.config.JsonConfiguration;
import org.bson.types.ObjectId;

public class Company {

    @JsonSerialize(using = JsonConfiguration.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfiguration.ObjectIdDeserializer.class)
    private ObjectId id;

    private String name;
    private String ceo;
    private String areaOfActivity;
    private Office office;

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

    @Override
    public String toString(){
        return "id: " + this.id
                + ", name: " + this.name
                + ", ceo: " + this.ceo
                + ", areaOfActivity: " + this.areaOfActivity
                + ", office: " + this.office;
    }

}
