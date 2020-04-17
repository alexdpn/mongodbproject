package com.project.mongodb.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.bson.types.ObjectId;

import java.io.IOException;

public class JsonConfiguration {

    public static class ObjectIdSerializer extends JsonSerializer<ObjectId> {
        @Override
        public void serialize(ObjectId objectId, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
            if (objectId != null) {
                generator.writeString(objectId.toString());
            } else {
                generator.writeNull();
            }
        }
    }
}
