package com.project.mongodb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"numberOfWorkers", "address"})
public class Office {
    public Address address;
    public int numberOfWorkers;

    public Office(){}

    public Office(int numberOfWorkers, Address address){
        this.address = address;
        this.numberOfWorkers = numberOfWorkers;
    }

    @XmlElement
    public Address getAddress(){
        return this.address;
    }

    @XmlElement
    public int getNumberOfWorkers(){
        return this.numberOfWorkers;
    }


    public void setAddress(Address address){
        this.address = address;
    }

    public void setNumberOfWorkers(int numberOfWorkers){
        this.numberOfWorkers = numberOfWorkers;
    }

    @Override
    public String toString(){
        return  "numberOfWorkers: " + this.numberOfWorkers + " address: " + this.address;
    }
}
