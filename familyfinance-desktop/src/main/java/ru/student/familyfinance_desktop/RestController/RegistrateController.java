package ru.student.familyfinance_desktop.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.RegistrationPerson;

@RequiredArgsConstructor
@Component
public class RegistrateController {
    private final RestTemplate rest;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrateController.class);

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

}
