package com.project.mongodb.repository.impl;

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
import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.util.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static com.mongodb.client.model.Filters.eq;
import static com.project.mongodb.util.Constants.COLLECTION;
import static com.project.mongodb.util.Constants.CONNECTION_STRING;
import static com.project.mongodb.util.Constants.DATABASE;
import static com.project.mongodb.util.Constants.ID;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
public class CompanyRepositoryImpl implements CompanyRepository {

    private MongoClient mongoClient;
    private MongoCollection<Company> mongoCollection;

    public CompanyRepositoryImpl() {}

    @Override
    @PostConstruct
    public void init(){
        if(mongoClient != null || mongoCollection != null)
            return;

        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        log.info("Configuring MongoDb Client");
        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .codecRegistry(codecRegistry)
                        .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                        .build());

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE);
        mongoCollection = mongoDatabase.getCollection(COLLECTION, Company.class);

        log.info("Inserting default list of companies into the database");
        List<Company> companies = DataUtil.getCompaniesFromJsonFile();
        if (companies != null)
            companies.forEach(company -> mongoCollection.insertOne(company));
    }

    @Override
    public MongoCollection<Company> getMongoCollection() {
        return this.mongoCollection;
    }

    @Override
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

    @Override
    public Company getCompanyById(String id){
        try {
            return mongoCollection
                    .find(eq(ID, new ObjectId(id)))
                    .first();
        } catch(IllegalArgumentException e) {
            log.error("Company not found for id " + id, e);
            throw e;
        }
    }

    @Override
    public Office getOfficeByCompanyId(String id){
        if(getCompanyById(id) == null || getCompanyById(id).getOffice() == null)
            return null;

        return getCompanyById(id).getOffice();
    }

    @Override
    public Address getAddressByCompanyId(String id){
        if(getOfficeByCompanyId(id) == null || getOfficeByCompanyId(id).getAddress() == null)
            return null;

        return getCompanyById(id).getOffice().getAddress();
    }

    @Override
    public void deleteCompanyById(String id){
        mongoCollection.deleteOne(eq(ID, new ObjectId(id)));
    }

    @Override
    public void insertCompany(Company company){
        mongoCollection.insertOne(company);
    }

    @Override
    public void replaceCompany(String id, Company company){
       mongoCollection.replaceOne(eq(ID, new ObjectId(id)), company);
    }

    @Override
    @PreDestroy
    public void cleanUp() {
        mongoClient.close();
        EmbeddedMongoDbHelper.stopDatabase();
    }
}
