package ru.student.familyfinance.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Repository.ProductRepository;
import ru.student.familyfinance.Service.Implementation.ProductServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    private ProductService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new ProductServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление товара в список")
    public void addProductTest() {
        Person person = new Person();
        Expenses expenses = new Expenses();
        Product product = new Product(7, "Test Product", person, expenses);
        doReturn(product).when(repository).save(product);
        assertThat(service.addProduct(product)).isEqualTo(product);
        Mockito.verify(repository, Mockito.times(1)).save(product);
    }

    @Test
    @DisplayName("Удаление товара")
    public void removeProductTest() {
        doReturn(false).when(repository).existsById(7L);
        assertThat(service.removeProduct(7L)).isTrue();
        Mockito.verify(repository, Mockito.times(1)).deleteById(7L);
        Mockito.verify(repository, Mockito.times(1)).existsById(7L);
    }

    @Test
    @DisplayName("Редактирование товара")
    public void editProductTest() {
        Person person = new Person();
        Expenses expenses = new Expenses();
        Product product = new Product(7, "Test Product", person, expenses);
        doReturn(product).when(repository).save(product);
        assertThat(service.editProduct(product)).isEqualTo(product);
        Mockito.verify(repository, Mockito.times(1)).save(product);
    }

    @Test
    @DisplayName("Получение товара по идентификатору")
    public void getProductByIdTest() {
        Person person = new Person();
        Expenses expenses = new Expenses();
        Product product = new Product(7, "Test Product", person, expenses);
        doReturn(Optional.of(product)).when(repository).findById(7L);
        assertThat(service.geProductById(7L)).isEqualTo(product);
        Mockito.verify(repository, Mockito.times(1)).findById(7L);
    }

    @Test
    @DisplayName("Получение списка продуктов")
    public void getProductsTest() {
        Person person = new Person();
        doReturn(getProducts()).when(repository).findByPerson(person);
        assertThat(service.getProduct(person)).isEqualTo(getProducts());
        Mockito.verify(repository, Mockito.times(1)).findByPerson(person);
    }


    private List<Product> getProducts() {
        Person person = new Person();
        Expenses expenses = new Expenses();
        return List.of(new Product(1, "Product 1", person, expenses),
                       new Product(2, "Product 2", person, expenses),
                       new Product(3, "Product 3", person, expenses),
                       new Product(4, "Product 4", person, expenses),
                       new Product(5, "Product 5", person, expenses),
                       new Product(6, "Product 6", person, expenses));
    }

}
