package ru.student.familyfinance_desktop.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Model.RegistrationPerson;

@RequiredArgsConstructor
@Component
public class RegistrateController {
    private final RestTemplate rest;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrateController.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AutorizateData loginData;

    @Value("${backend.url}")
    private String url;

    public boolean registrate(RegistrationPerson person) {
        boolean result = false;

        ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            response = rest.postForEntity(url + "/registration", person, HttpStatus.class);
            LOGGER.info("Зарегистрирован новый пользователь: " + person.toString());
            result = true;
        } catch (RestClientException e) {
            LOGGER.error("Ошибка регистрации нового пользователя. Код возврата: " + response.getStatusCode());

        }
        
        return result;
    }

    public boolean editPerson(Person person) {
        boolean result = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e);
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<HttpStatus> responseType = new ParameterizedTypeReference<HttpStatus>() {};

        try {
            ResponseEntity<HttpStatus> response = rest.exchange(url + "/persons", HttpMethod.PUT, request,responseType, person);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка изменения свойств пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения свойств пользователя: " + e.getMessage());
        }

        return result;
    }

}
