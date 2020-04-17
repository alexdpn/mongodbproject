package com.project.mongodb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.project.mongodb.helper.EmbeddedMongoDbHelper;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import com.project.mongodb.util.DataUtil;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Singleton
public class CompanyRepository {
    private static MongoCollection<Company> mongoCollection;
    private static final String DATABASE = "pojodb";
    private static final String COLLECTION = "companies";
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static MongoClient mongoClient;

    static {
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .codecRegistry(codecRegistry)
                        .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                        .build());

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE);
        mongoCollection = mongoDatabase.getCollection(COLLECTION, Company.class);

        List<Company> companies = DataUtil.getCompaniesFromJsonFile();
        if (companies != null)
            companies.forEach(company -> mongoCollection.insertOne(company));
    }


    public CompanyRepository(){
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

    @PreDestroy
    public void cleanUp() {
        mongoClient.close();
        EmbeddedMongoDbHelper.stopDatabase();
    }
}
