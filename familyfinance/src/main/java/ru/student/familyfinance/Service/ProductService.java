package ru.student.familyfinance.Service;

import java.util.List;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;

/**
 * Сервис описывает действия со списком продуктов для покупок
 */
public interface ProductService {

    /**
     * Добавление нового товара в список
     * @param product - новый товар
     * @return - добавленный товар
     */
    Product addProduct(Product product);

    /**
     * Удаление товара из списка по идентификатору
     * @param id - идентификатор удаляемого товара
     * @return - результат операции true - успех/false - неудача
     */
    boolean removeProduct(long id);

    /**
     * Редактирование товара в списке
     * @param product - редактируемый товар
     * @return - отредактированный товар
     */
    Product editProduct(Product product);

    /**
     * Получение списка товаров пользователя
     * @param person - пользователь
     * @return - список товаров пользователя
     */
    List<Product> getProduct(Person person);

    /**
     * Получение товара по идентификатору
     * @param id - идентификатор товара
     * @return - найденный товар или NULL
     */
    Product getProductById(long id);
}
