package com.project.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoDbHelper {
    private MongoCollection<Company> mongoCollection;

    public MongoDbHelper(){
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().codecRegistry(codecRegistry).build());

        MongoDatabase mongoDatabase = mongoClient.getDatabase("pojodb");
        mongoCollection = mongoDatabase.getCollection("companies", Company.class);
    }

    public MongoCollection<Company> getMongoCollection() {
        return this.mongoCollection;
    }

    public List<Company> getCompanies(){
        List<Company> companyList = new ArrayList<>(20);
        mongoCollection.find()
                .forEach(new Consumer<Company>() {
                    @Override
                    public void accept(Company company) {
                        companyList.add(company);
                    }
                });

        return companyList;
    }

    public Company getCompanyById(String id){
        return mongoCollection
                .find(eq("_id", new ObjectId(id)))
                .first();
    }

    public Office getOffice(String id){
        return getCompanyById(id).getOffice();
    }

    public Address getAddress(String id){
        return getCompanyById(id).getOffice().getAddress();
    }

    public void deleteCompanyById(String id){
        mongoCollection.deleteOne(eq("_id", new ObjectId(id)));
    }

    public void insertCompany(Company company){
        mongoCollection.insertOne(company);
    }

    public void replaceCompany(String id, Company company){
       mongoCollection.replaceOne(eq("_id", new ObjectId(id)), company);
    }
}
