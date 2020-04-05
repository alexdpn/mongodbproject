package com.project.mongodb.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"country", "city", "street"})
public class Address {
    public String country;
    public String city;
    private String street;

    public Address(){}

    public Address(String country, String city, String street){
        this.country = country;
        this.city = city;
        this.street = street;
    }

    @XmlElement
    public String getCountry(){
        return this.country;
    }

    @XmlElement
    public String getCity(){
        return this.city;
    }

    @XmlElement
    public String getStreet(){
        return this.street;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setStreet(String street){
        this.street = street;
    }

    @Override
    public String toString(){
        return "country: " + this.country + " city: " + this.city + " street: " + this.street;
    }
}

