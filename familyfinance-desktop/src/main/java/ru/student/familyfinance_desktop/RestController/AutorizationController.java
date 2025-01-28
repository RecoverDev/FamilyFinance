package ru.student.familyfinance_desktop.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.Model.Person;

@RequiredArgsConstructor
@Component
public class AutorizationController {
    private final RestTemplate rest;
    private static final Logger LOGGER = LoggerFactory.getLogger(AutorizationController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    public Person login(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + loginData.getBase64String());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        Person result = null;
        try {
            ResponseEntity<Person> response = rest.exchange(url + "/login", HttpMethod.GET, request, Person.class);
            result = response.getBody();
        } catch (Exception e) {
            LOGGER.info("Попытка входа с неверными данными: " + loginData.toString());
        }

        return result;
    }
}
