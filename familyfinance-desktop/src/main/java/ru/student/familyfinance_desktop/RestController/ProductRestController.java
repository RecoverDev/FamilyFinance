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
import ru.student.familyfinance_desktop.Model.Product;

@Component
public class ProductRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    @Value("${backend.url}")
    private String url;

    @Autowired
    private AutorizateData loginData;

    @Autowired
    private ObjectMapper mapper;

    public List<Product> getProducts() {
        List<Product> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ParameterizedTypeReference<List<Product>> responseType = new ParameterizedTypeReference<List<Product>>() {};
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<Product>> response = restTemplate.exchange(url + "/products", HttpMethod.GET, request,responseType);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = (List<Product>)response.getBody();
            } else {
                LOGGER.info("Список товаров не получен: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка при получении списка товаров: " + e.getMessage());
        }

        return result;
    }

    public Product getProductById(long id) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.OK);
        Product result = null;
        try {
            response = restTemplate.getForEntity(url + "/products/{id}", Product.class, id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка получения товара. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка получения товара: " + e.getMessage());
        }

        return result;
    }

    public Product addProduct(Product product) {
        RestTemplate restTemplate = (new RestTemplateBuilder()).basicAuthentication(loginData.getUsername(), loginData.getPassword()).build();
        ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.OK);
        Product result = null;

        try {
            response = restTemplate.postForEntity(url + "/products", product, Product.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка сохранения нового товара. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка сохранения нового товара: " + e.getMessage());
        }

        return result;
    }

    public Product editProduct(Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        try {
            body = mapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка преобразования объекта в JSON: ", e.getMessage());
        }
        
        HttpEntity<String> request = new HttpEntity<String>(body,headers);
        ParameterizedTypeReference<Product> responseType = new ParameterizedTypeReference<Product>() {};
        RestTemplate restTemplate = new RestTemplate();
        Product result = null;

        try {
            ResponseEntity<Product> response = restTemplate.exchange(url + "/products", HttpMethod.PUT, request, responseType, product);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = response.getBody();
            } else {
                LOGGER.info("Ошибка изменения товара. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка изменения товара: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteProductById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginData.getUsername(), loginData.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        boolean result = false;

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(url + "/products/{id}", HttpMethod.DELETE,request,HttpStatus.class,id);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                result = true;
            } else {
                LOGGER.info("Ошибка удаления товара. Код возврата: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            LOGGER.info("Ошибка удаления товара: " + e.getMessage());
        }

        return result;
    }
}
