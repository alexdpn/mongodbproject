package com.project.mongodb.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mongodb.model.Company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DataUtil {
    private static final String RELATIVE_PATH = "/src/main/resources/companies.json";
    private static final String BASE_PATH = System.getProperty("user.dir");

    public static List<Company> getCompaniesFromJsonFile() {
        List<Company> list = new ArrayList<>(5);
        File file = new File(BASE_PATH + RELATIVE_PATH);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            list = objectMapper.readValue(file, new TypeReference<List<Company>>() {});

            return list;
        }catch(Exception e ) {
            e.printStackTrace();
        }
        return list;
    }
}
