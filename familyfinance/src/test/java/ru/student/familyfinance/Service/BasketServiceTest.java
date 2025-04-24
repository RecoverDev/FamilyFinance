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
import org.springframework.data.util.Pair;

import ru.student.familyfinance.Model.Basket;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Repository.BasketRepository;
import ru.student.familyfinance.Repository.GrossBookRepository;
import ru.student.familyfinance.Service.Implementation.BasketServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private GrossBookRepository grossBookRepository;

    private BasketService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new BasketServiceImplementation(basketRepository, grossBookRepository);
    }

    @Test
    @DisplayName("Добавление нового товара в список")
    public void addBasketTest() {
        Basket basket = new Basket();
        doReturn(basket).when(basketRepository).save(basket);
        assertThat(service.addBasket(basket)).isEqualTo(basket);
        Mockito.verify(basketRepository, Mockito.times(1)).save(basket);
    }

    @Test
    @DisplayName("Редактировние товара в списке")
    public void editBasketTest() {
        Basket basket = new Basket();
        doReturn(basket).when(basketRepository).save(basket);
        assertThat(service.editBasket(basket)).isEqualTo(basket);
        Mockito.verify(basketRepository, Mockito.times(1)).save(basket);
    }

    @Test
    @DisplayName("Удаление товара из списка")
    public void removeBasketTest() {
        doReturn(false).when(basketRepository).existsById(7L);
        assertThat(service.removeBasket(7L)).isTrue();
        Mockito.verify(basketRepository, Mockito.times(1)).existsById(7L);
    }

    @Test
    @DisplayName("Получение списка товара")
    public void getBasketTest() {
        doReturn(getBaskets()).when(basketRepository).findByPerson(getPerson());
        assertThat(service.getBasket(getPerson())).isEqualTo(getBaskets());
        Mockito.verify(basketRepository, Mockito.times(1)).findByPerson(getPerson());
    }

    @Test
    @DisplayName("Получение товара из списка по идентификатору")
    public void getBasketByIdTest() {
        doReturn(Optional.of(getBaskets().get(0))).when(basketRepository).findById(1L);
        assertThat(service.getBasketById(1L)).isEqualTo(getBaskets().get(0));
        Mockito.verify(basketRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Получение списка товаров по магазину")
    public void getBasketByShopTest() {
        List<Basket> list = getBaskets().stream().filter(b -> b.getShop().equals(getShops().get(0))).toList();
        doReturn(list).when(basketRepository).findByPersonAndShop(getPerson(), getShops().get(0));
        assertThat(service.getBasketByShop(getPerson(), getShops().get(0))).isEqualTo(list);
        Mockito.verify(basketRepository, Mockito.times(1)).findByPersonAndShop(getPerson(), getShops().get(0));
    }

    @Test
    @DisplayName("Создание покупки")
    public void makePurchaseTest() {
        List<Pair<Basket, Double>> purchase = getBaskets().stream()
                    .filter(b -> b.getShop().equals(getShops().get(0)))
                    .map(b -> Pair.of(b, 100.0)).toList();
        assertThat(service.makePurchase(getPerson(), purchase)).isTrue();
        
    }

    private Person getPerson() {
        return new Person();
    }

    private List<Shop> getShops() {
        return List.of(new Shop(1, "Shop 1", getPerson()),
                       new Shop(2, "Shop 2", getPerson()),
                       new Shop(3, "Shop 3", getPerson()));
    }

    private List<Product> getProducts() {
        return List.of(new Product(1, "Product 1", getPerson(), new Expenses()),
                       new Product(2, "Product 2", getPerson(), new Expenses()),
                       new Product(3, "Product 3", getPerson(), new Expenses()),
                       new Product(4, "Product 4", getPerson(), new Expenses()),
                       new Product(5, "Product 5", getPerson(), new Expenses()),
                       new Product(6, "Product 6", getPerson(), new Expenses()),
                       new Product(7, "Product 7", getPerson(), new Expenses()),
                       new Product(8, "Product 8", getPerson(), new Expenses()));
    }

    private List<Basket> getBaskets() {

        List<Shop> shops = getShops();
        List<Product> products = getProducts();
        Person person = getPerson();

        return List.of(new Basket(1, person, shops.get(0), products.get(0)),
                       new Basket(2, person, shops.get(0), products.get(1)),
                       new Basket(3, person, shops.get(0), products.get(2)),
                       new Basket(4, person, shops.get(1), products.get(3)),
                       new Basket(5, person, shops.get(1), products.get(4)),
                       new Basket(6, person, shops.get(1), products.get(5)),
                       new Basket(7, person, shops.get(2), products.get(6)),
                       new Basket(8, person, shops.get(2), products.get(7)));
    }

}
