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

import javafx.util.Pair;
import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.Model.Basket;

@Component
public class BasketRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasketRestController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;

    public List<Basket> getBaskets() {
        List<Basket> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Basket>> responseType = new ParameterizedTypeReference<List<Basket>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Basket>> response = restTemplate.exchange(url + "/baskets", HttpMethod.GET, request,responseType);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Basket>)response.getBody();
            } else {
                LOGGER.info("Список покупок не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка покупок: " + e.getMessage());
        }

        return result;
    }

    public Basket getProductById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Basket> response = new ResponseEntity<>(HttpStatus.OK);
        Basket result = null;
        try {
            response = restTemplate.getForEntity(url + "/baskets/{id}", Basket.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения покупки. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения покупки: " + e.getMessage());
        }

        return result;
    }

    public Basket addBasket(Basket basket) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Basket> response = new ResponseEntity<>(HttpStatus.OK);
        Basket result = null;

        try {
            response = restTemplate.postForEntity(url + "/baskets", basket, Basket.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения новой покупки. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения новой покупки: " + e.getMessage());
        }

        return result;
    }

    public Basket editBasket(Basket basket) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(basket);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e.getMessage());
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<Basket> responseType = new ParameterizedTypeReference<Basket>() {};
        RestTemplate restTemplate = new RestTemplate();
        Basket result = null;

        try {
            ResponseEntity<Basket> response = restTemplate.exchange(url + "/baskets", HttpMethod.PUT, request, responseType, basket);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения покупки. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения покупки: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteBasketById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/baskets/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления покупки. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления покупки: " + e.getMessage());
        }

        return result;
    }

    public List<Basket> getBasketByShop(long id) {
        List<Basket> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Basket>> responseType = new ParameterizedTypeReference<List<Basket>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Basket>> response = restTemplate.exchange(url + "/baskets/shop/{id}", HttpMethod.GET, request,responseType, id);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Basket>)response.getBody();
            } else {
                LOGGER.info("Список покупок по идентификатору магазина не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка покупок по идентификатору магазина: " + e.getMessage());
        }

        return result;
    }

    public boolean postPurchase(List<Pair<Basket,Double>> list) {
        boolean result = false;

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e.getMessage());
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<HttpStatus> responseType = new ParameterizedTypeReference<HttpStatus>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/baskets/purchase", HttpMethod.POST, request, responseType, list);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка формирование расходной операции. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка формирование расходной операции: " + e.getMessage());
        }

        return result;
    }
}
