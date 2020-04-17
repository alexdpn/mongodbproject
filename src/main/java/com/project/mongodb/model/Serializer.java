package com.project.mongodb.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.bson.types.ObjectId;

import java.io.IOException;

public class Serializer {

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

    public static class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {
        @Override
        public ObjectId deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode objectId = ((JsonNode) jp.readValueAsTree()).get("id");
            return new ObjectId(objectId.toString());
        }
    }
}
