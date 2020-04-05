package com.project.mongodb.model;

import com.project.mongodb.model.adapter.ObjectIdAdapter;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = {"id", "name", "ceo", "areaOfActivity", "office"})
public class Company {
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


    @XmlJavaTypeAdapter(ObjectIdAdapter.class)
    public ObjectId getId(){
        return this.id;
    }

    @XmlElement
    public String getName() {
        return this.name;
    }

    @XmlElement
    public String getCeo(){
        return this.ceo;
    }

    @XmlElement
    public String getAreaOfActivity() {
        return this.areaOfActivity;
    }

    @XmlElement
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
