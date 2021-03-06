package com.project.mongodb.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mongodb.model.Company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.project.mongodb.util.Constants.PATH;

public class DataUtil {

    public static List<Company> getCompaniesFromJsonFile() {
        List<Company> list = new ArrayList<>(5);

        try {
            InputStream stream = DataUtil.class.getResourceAsStream(PATH);

            ObjectMapper objectMapper = new ObjectMapper();
            list = objectMapper.readValue(stream, new TypeReference<List<Company>>() {});

            return list;
        }catch(Exception e ) {
            e.printStackTrace();
        }
        return list;
    }
}
