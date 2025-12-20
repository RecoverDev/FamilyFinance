package ru.student.familyfinance_service.Service;

import java.beans.PropertyChangeListener;
import java.util.List;

import ru.student.familyfinance_service.Model.Product;

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

    /**
     * Изменение товара
     * @param product - изменяемый товар
     * @return - измененный товар Product
     */
    boolean editProduct(Product product);

    /**
     * Удаление товара по идентификатору
     * @param id - идентификатор удаляемого товара
     * @return - true - успех/false - неудача
     */
    boolean deleteProductById(long id);

    /**
     * Установить слушателя на изменение репозотория ShopRepository
     * @param listener
     */
    void setRepositoryListener(PropertyChangeListener listener);
}
