package ru.student.familyfinance_desktop.Service;

import java.util.List;

import ru.student.familyfinance_desktop.Model.Product;

/**
 * Сервис по работе со списком товаров
 */
public interface ProductService {

    /**
     * Заполнение репозитория
     */
    void setProducts();

    /**
     * Получение полного списка товаров пользователя
     * @return - список товаров
     */
    List<Product> getProducts();

    /**
     * Получение товара по идентификатору
     * @param id - идентификатор товара
     * @return - товар Product
     */
    Product getProductById(long id);

    /**
     * Добавление нового товара
     * @param product - добавляемый товар
     * @return - true - успех/false - неудача
     */
    boolean addProduct(Product product);

    boolean editProduct(Product product);

    boolean deleteProductById(long id);

}
