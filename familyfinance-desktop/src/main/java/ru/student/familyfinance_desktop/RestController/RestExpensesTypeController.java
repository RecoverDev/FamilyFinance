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
import ru.student.familyfinance_desktop.Model.ExpensesType;

@Component
public class RestExpensesTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExpensesTypeController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;

    public List<ExpensesType> getExpensesTypes() {
        List<ExpensesType> result = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<ExpensesType>> responseType = new ParameterizedTypeReference<List<ExpensesType>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<ExpensesType>> response = restTemplate.exchange(url + "/expensestypes", HttpMethod.GET, request,responseType);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<ExpensesType>)response.getBody();
            } else {
                LOGGER.info("Список типов расходов не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка типов расходов: " + e.getMessage());
        }

        return result;
    }

    public ExpensesType getExpensesTypeById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<ExpensesType> response = new ResponseEntity<>(HttpStatus.OK);
        ExpensesType result = null;
        try {
            response = restTemplate.getForEntity(url + "/expensestypes/{id}", ExpensesType.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения типа расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения типа расходов: " + e.getMessage());
        }

        return result;
    }

    public ExpensesType addExpensesType(ExpensesType expensesType) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<ExpensesType> response = new ResponseEntity<>(HttpStatus.OK);
        ExpensesType result = null;

        try {
            response = restTemplate.postForEntity(url + "/expensestypes", expensesType, ExpensesType.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения нового типа расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения нового типа расходов: " + e.getMessage());
        }

        return result;
    }

    public ExpensesType editExpensesType(ExpensesType expensesType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(expensesType);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e);
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<ExpensesType> responseType = new ParameterizedTypeReference<ExpensesType>() {};
        RestTemplate restTemplate = new RestTemplate();
        ExpensesType result = null;

        try {
            ResponseEntity<ExpensesType> response = restTemplate.exchange(url + "/expensestypes", HttpMethod.PUT, request, responseType, expensesType);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения типа расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения типа расходов: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteExpensesTypeById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/expensestypes/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления типа расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления типа расходов: " + e.getMessage());
        }

        return result;
    }


}
