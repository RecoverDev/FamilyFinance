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

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Repository.ShopRepository;
import ru.student.familyfinance.Service.Implementation.ShopServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

    @Mock
    ShopRepository repository;

    ShopService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new ShopServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление нового магазина")
    public void addShopTest() {
        Person person = new Person();
        Shop shop = new Shop(7, "Test Shop", person);
        doReturn(shop).when(repository).save(shop);
        assertThat(service.addShop(shop)).isEqualTo(shop);
        Mockito.verify(repository, Mockito.times(1)).save(shop);
    }

    @Test
    @DisplayName("Удаление магазина из списка")
    public void removeShopTest() {
        doReturn(false).when(repository).existsById(1L);
        assertThat(service.removeShop(1L)).isTrue();
        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
        Mockito.verify(repository, Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Редактирование магазина")
    public void editShopTest() {
        Person person = new Person();
        Shop shop = new Shop(7, "Test Shop", person);
        doReturn(shop).when(repository).save(shop);
        assertThat(service.editShop(shop)).isEqualTo(shop);
        Mockito.verify(repository, Mockito.times(1)).save(shop);
    }

    @Test
    @DisplayName("Получение списка магазинов")
    public void getShopsTest() {
        Person person = new Person();
        doReturn(getShops()).when(repository).findByPerson(person);
        assertThat(service.getShops(person)).isEqualTo(getShops());
        Mockito.verify(repository, Mockito.times(1)).findByPerson(person);
    }

    @Test
    @DisplayName("Получение магазина по идентификатору")
    public void getShopByIdTest() {
        Person person = new Person();
        Shop shop = new Shop(7, "Test Shop", person);
        doReturn(Optional.of(shop)).when(repository).findById(7L);
        assertThat(service.getShopById(7L)).isEqualTo(shop);
        Mockito.verify(repository, Mockito.times(1)).findById(7L);
    }

    private List<Shop> getShops() {
        Person person = new Person();
        return List.of(new Shop(1, "Shop 1", person),
                       new Shop(2, "Shop 2", person),
                       new Shop(3, "Shop 3", person),
                       new Shop(4, "Shop 4", person),
                       new Shop(5, "Shop 5", person),
                       new Shop(6, "Shop 6", person));
    }

}
