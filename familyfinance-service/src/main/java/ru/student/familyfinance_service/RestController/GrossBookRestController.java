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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.student.familyfinance_service.Model.AutorizateData;
import ru.student.familyfinance_service.Model.GrossBook;
import ru.student.familyfinance_service.Model.Target;

@Component
public class GrossBookRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrossBookRestController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;

    public List<GrossBook> getGrossBooks(LocalDate begin, LocalDate end) {
        List<GrossBook> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<GrossBook>> responseType = new ParameterizedTypeReference<List<GrossBook>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<GrossBook>> response = restTemplate.exchange(url + "/grossbooks/{beginDate}/{endDate}", HttpMethod.GET, request,responseType,begin,end);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<GrossBook>)response.getBody();
            } else {
                LOGGER.info("Список видов записей не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка записей: " + e.getMessage());
        }

        return result;
    }

    public GrossBook getGrossBookById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<GrossBook> response = new ResponseEntity<>(HttpStatus.OK);
        GrossBook result = null;
        try {
            response = restTemplate.getForEntity(url + "/grossbooks/{id}", GrossBook.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения записи. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения записи: " + e.getMessage());
        }

        return result;
    }

    public GrossBook addGrossBook(GrossBook grossBook) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<GrossBook> response = new ResponseEntity<>(HttpStatus.OK);
        GrossBook result = null;

        try {
            response = restTemplate.postForEntity(url + "/grossbooks", grossBook, GrossBook.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения новой записи. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения новой записи: " + e.getMessage());
        }

        return result;
    }

    public GrossBook editGrossBook(GrossBook grossBook) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(grossBook);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e);
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<GrossBook> responseType = new ParameterizedTypeReference<GrossBook>() {};
        RestTemplate restTemplate = new RestTemplate();
        GrossBook result = null;

        try {
            ResponseEntity<GrossBook> response = restTemplate.exchange(url + "/grossbooks", HttpMethod.PUT, request,responseType, grossBook);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения записи. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения записи: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteGrossBookById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/grossbooks/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления записи. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления записи: " + e.getMessage());
        }

        return result;
    }

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
