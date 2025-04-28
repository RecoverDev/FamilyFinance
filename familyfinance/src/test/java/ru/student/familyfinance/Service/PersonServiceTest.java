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
import ru.student.familyfinance.Repository.ExpensesRepository;
import ru.student.familyfinance.Repository.IncomeRepository;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Repository.ProductRepository;
import ru.student.familyfinance.Repository.ShopRepository;
import ru.student.familyfinance.Service.Implementation.PersonServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    IncomeRepository incomeRepository;

    @Mock
    ExpensesRepository expensesRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    ShopRepository shopRepository;

    PersonService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new PersonServiceImplementation(personRepository, incomeRepository, expensesRepository, shopRepository, productRepository);
    }

    @Test
    @DisplayName("Добавление нового пользователя")
    public void addPersonTest() {
        Person person = new Person(1,"first","First User", "111", "first@server.com", 0, false);
        doReturn(person).when(personRepository).save(person);
        assertThat(service.addPerson(person)).isTrue();
        Mockito.verify(personRepository,Mockito.times(1)).save(person);
    }

    @Test
    @DisplayName("Редактирование нового пользователя")
    public void editPersonTest() {
        Person person = new Person(1,"first","First User", "111", "first@server.com", 0, false);
        doReturn(person).when(personRepository).save(person);
        assertThat(service.editPerson(person)).isTrue();
        Mockito.verify(personRepository,Mockito.times(1)).save(person);
    }

    @Test
    @DisplayName("Удаление пользователя по ID")
    public void deletePersonTest() {
        doReturn(false).when(personRepository).existsById(1L);
        assertThat(service.removePerson(1)).isTrue();
        Mockito.verify(personRepository,Mockito.times(1)).deleteById(1L);
        Mockito.verify(personRepository,Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("получение списка пользователей")
    public void getPersonsTest() {
        List<Person> list = List.of(new Person(1,"first","First User", "111", "first@server.com", 0, false),
                                    new Person(2,"second","Second User", "222", "second@server.com", 0, false),
                                    new Person(3,"admin","Admin", "333", "admin@server.com", 1, false));
        doReturn(list).when(personRepository).findAll();
        assertThat(service.getPersons()).isEqualTo(list);
        Mockito.verify(personRepository,Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    public void getPersonByIdTest() {
        Person person = new Person(1,"first","First User", "111", "first@server.com", 0, false);
        doReturn(Optional.of(person)).when(personRepository).findById(1L);
        assertThat(service.getPersonById(1L)).isEqualTo(person);
        Mockito.verify(personRepository,Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Получение пользователя по имени")
    public void getPersonByUsernameTest() {
        Person person = new Person(1,"first","First User", "111", "first@server.com", 0, false);
        doReturn(person).when(personRepository).findByUsername("first");
        assertThat(service.getPersonByUsername("first")).isEqualTo(person);
        Mockito.verify(personRepository,Mockito.times(1)).findByUsername("first");
    }

}
