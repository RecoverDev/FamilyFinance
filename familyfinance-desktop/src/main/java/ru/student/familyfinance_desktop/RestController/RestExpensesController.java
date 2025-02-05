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
import ru.student.familyfinance_desktop.Model.Expenses;

@Component
public class RestExpensesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExpensesController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;


    public List<Expenses> getExpenses() {
        List<Expenses> result = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Expenses>> responseType = new ParameterizedTypeReference<List<Expenses>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Expenses>> response = restTemplate.exchange(url + "/expenses", HttpMethod.GET, request,responseType);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Expenses>)response.getBody();
            } else {
                LOGGER.info("Список видов расходов не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка видов расходов: " + e.getMessage());
        }

        return result;
    }

    public Expenses getExpensesById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Expenses> response = new ResponseEntity<>(HttpStatus.OK);
        Expenses result = null;
        try {
            response = restTemplate.getForEntity(url + "/expenses/{id}", Expenses.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения вида расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения вида расходов: " + e.getMessage());
        }

        return result;
    }

    public Expenses addExpenses(Expenses expenses) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Expenses> response = new ResponseEntity<>(HttpStatus.OK);
        Expenses result = null;

        try {
            response = restTemplate.postForEntity(url + "/expenses", expenses, Expenses.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения нового вида расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения нового вида расходов: " + e.getMessage());
        }

        return result;
    }

    public Expenses editExpenses(Expenses expenses) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(expenses);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e);
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<Expenses> responseType = new ParameterizedTypeReference<Expenses>() {};
        RestTemplate restTemplate = new RestTemplate();
        Expenses result = null;

        try {
            ResponseEntity<Expenses> response = restTemplate.exchange(url + "/expenses", HttpMethod.PUT, request,responseType,expenses);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения вида расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения вида расходов: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteExpensesById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/expenses/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления вида расходов. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления вида расходов: " + e.getMessage());
        }

        return result;
    }
}
