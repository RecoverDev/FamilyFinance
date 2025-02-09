package ru.student.familyfinance_desktop.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.Model.Target;

@Component
public class TargetRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TargetRestController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;


    public List<Target> getTargets() {
        List<Target> result = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Target>> responseType = new ParameterizedTypeReference<List<Target>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Target>> response = restTemplate.exchange(url + "/targets", HttpMethod.GET, request,responseType);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Target>)response.getBody();
            } else {
                LOGGER.info("Список целей пользователя не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка целей пользователя: " + e.getMessage());
        }

        return result;
    }

    public Target getTargetById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Target> response = new ResponseEntity<>(HttpStatus.OK);
        Target result = null;
        try {
            response = restTemplate.getForEntity(url + "/targets/{id}", Target.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения цели пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения цели пользователя: " + e.getMessage());
        }

        return result;
    }

    public Target addTarget(Target target) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Target> response = new ResponseEntity<>(HttpStatus.OK);
        Target result = null;

        try {
            response = restTemplate.postForEntity(url + "/targets", target, Target.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения новой цели пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения новой цели пользователя: " + e.getMessage());
        }

        return result;
    }

    public Target editTarget(Target target) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e);
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<Target> responseType = new ParameterizedTypeReference<Target>() {};
        RestTemplate restTemplate = new RestTemplate();
        Target result = null;

        try {
            ResponseEntity<Target> response = restTemplate.exchange(url + "/targets", HttpMethod.PUT, request,responseType, target);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения цели пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения цели пользователя: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteIncomeById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/targets/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления цели пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления цели пользователя: " + e.getMessage());
        }

        return result;
    }
}
