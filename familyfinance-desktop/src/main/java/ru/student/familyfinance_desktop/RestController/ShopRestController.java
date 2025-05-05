package ru.student.familyfinance_desktop.RestController;

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

import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.Model.Shop;

@Component
public class ShopRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopRestController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;

    public List<Shop> getShops() {
        List<Shop> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Shop>> responseType = new ParameterizedTypeReference<List<Shop>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Shop>> response = restTemplate.exchange(url + "/shops", HttpMethod.GET, request,responseType);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Shop>)response.getBody();
            } else {
                LOGGER.info("Список магазинов не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка магазинов: " + e.getMessage());
        }

        return result;
    }

    public Shop getShopById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Shop> response = new ResponseEntity<>(HttpStatus.OK);
        Shop result = null;
        try {
            response = restTemplate.getForEntity(url + "/shops/{id}", Shop.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения магазина. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения магазина: " + e.getMessage());
        }

        return result;
    }

    public Shop addShop(Shop shop) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Shop> response = new ResponseEntity<>(HttpStatus.OK);
        Shop result = null;

        try {
            response = restTemplate.postForEntity(url + "/shops", shop, Shop.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения нового магазина. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения нового магазина: " + e.getMessage());
        }

        return result;
    }

    public Shop editShop(Shop shop) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(shop);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e.getMessage());
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<Shop> responseType = new ParameterizedTypeReference<Shop>() {};
        RestTemplate restTemplate = new RestTemplate();
        Shop result = null;

        try {
            ResponseEntity<Shop> response = restTemplate.exchange(url + "/shops", HttpMethod.PUT, request, responseType, shop);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения магазина. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения магазина: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteShopById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/shops/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления магазина. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления магазина: " + e.getMessage());
        }

        return result;
    }
}
