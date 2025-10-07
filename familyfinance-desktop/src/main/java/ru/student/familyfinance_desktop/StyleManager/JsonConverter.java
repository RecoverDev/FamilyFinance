package ru.student.familyfinance_desktop.StyleManager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConverter.class);

    @Autowired
    private  ObjectMapper jsonMapper;
    
    
    public String toJsonString(List<Style> styles) {

        String json = "";
        try {
            json = jsonMapper.writeValueAsString(styles);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования в формат JSON", e);
        }
        return json;
    }

    public List<Style> fromJson(String json) {
        List<Style> result = new ArrayList<>();
        TypeReference<List<Style>> convertType = new TypeReference<List<Style>>() {};

        try {
            result = jsonMapper.readValue(json, convertType);
        } catch (JsonMappingException e) {
            LOGGER.error("Ошибка преобразования из формата JSON", e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования из формата JSON", e);
        }
        return result;
    }
}
