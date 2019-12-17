package com.sample.inventory.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static <T> T read(String value, Class<T> clazz) {
        try {
            return MAPPER.readValue(value, clazz);
        } catch (Exception var3) {
            throw new RuntimeException(String.format("Could not read value '%s' as '%s'", value, clazz.getSimpleName()), var3);
        }
    }

    static {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.configure(MapperFeature.USE_ANNOTATIONS, false);
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }
}
