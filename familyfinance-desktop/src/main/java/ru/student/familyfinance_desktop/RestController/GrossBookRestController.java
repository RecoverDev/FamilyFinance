package ru.student.familyfinance_desktop.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Model.Target;

@Component
public class GrossBookRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrossBookRestController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;

    public List<GrossBook> getGrossBookByScroll(List<Target> targets) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(targets);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e);
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<List<GrossBook>> responseType = new ParameterizedTypeReference<List<GrossBook>>() {};
        RestTemplate restTemplate = new RestTemplate();
        List<GrossBook> result = null;

        try {
            ResponseEntity<List<GrossBook>> response = restTemplate.exchange(url + "/grossbooks/target/list", HttpMethod.POST, request,responseType);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения списка записей. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения списка записей: " + e.getMessage());
        }

        return result;
    }

}
