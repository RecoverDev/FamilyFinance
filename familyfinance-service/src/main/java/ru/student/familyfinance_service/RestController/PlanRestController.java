package ru.student.familyfinance_service.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.student.familyfinance_service.Model.AutorizateData;
import ru.student.familyfinance_service.Model.Plan;

@RestController
public class PlanRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanRestController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;

    public List<Plan> getPlans(LocalDate period) {
        List<Plan> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Plan>> responseType = new ParameterizedTypeReference<List<Plan>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Plan>> response = restTemplate.exchange(url + "/plans/{date}", HttpMethod.GET, request,responseType, period);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Plan>)response.getBody();
            } else {
                LOGGER.info("Список записей планов пользователя не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка записей планов пользователя: " + e.getMessage());
        }

        return result;
    }

    public List<Plan> getPlansSeveralMonth(LocalDate begin, LocalDate end) {
        List<Plan> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Plan>> responseType = new ParameterizedTypeReference<List<Plan>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Plan>> response = restTemplate.exchange(url + "/plans/{beginDate}/{endDate}", HttpMethod.GET, request,responseType, begin, end);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Plan>)response.getBody();
            } else {
                LOGGER.info("Список записей планов пользователя не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка записей планов пользователя: " + e.getMessage());
        }

        return result;
    }

    public Plan getPlanById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Plan> response = new ResponseEntity<>(HttpStatus.OK);
        Plan result = null;
        try {
            response = restTemplate.getForEntity(url + "/plans/{id}", Plan.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения плана пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения плана пользователя: " + e.getMessage());
        }

        return result;
    }

    public Plan addPlan(Plan plan) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Plan> response = new ResponseEntity<>(HttpStatus.OK);
        Plan result = null;

        try {
            response = restTemplate.postForEntity(url + "/plans", plan, Plan.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения нового плана пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения нового плана пользователя: " + e.getMessage());
        }

        return result;
    }

    public Plan editPlan(Plan plan) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(plan);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e);
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<Plan> responseType = new ParameterizedTypeReference<Plan>() {};
        RestTemplate restTemplate = new RestTemplate();
        Plan result = null;

        try {
            ResponseEntity<Plan> response = restTemplate.exchange(url + "/plans", HttpMethod.PUT, request,responseType,plan);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения плана пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения плана пользователя: " + e.getMessage());
        }

        return result;
    }

    public boolean deletePlanById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/plans/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления плана пользователя. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления плана пользователя: " + e.getMessage());
        }

        return result;
    }

}
