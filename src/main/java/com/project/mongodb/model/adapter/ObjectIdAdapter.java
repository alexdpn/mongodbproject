package com.project.mongodb.model.adapter;

import org.bson.types.ObjectId;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ObjectIdAdapter extends XmlAdapter<String, ObjectId>{

    @Override
    public String marshal(ObjectId id){
        return id.toString();
    }

    @Override
    public ObjectId unmarshal(String id){
        return new ObjectId(id);
    }
}
