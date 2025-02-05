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
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.ExpensesRepository;
import ru.student.familyfinance.Service.Implementation.ExpensesServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class ExpensesServiceTest {

    @Mock
    ExpensesRepository repository;

    ExpensesService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new ExpensesServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление нового вида расходов")
    public void addExpensesTest() {
        Expenses expenses = new Expenses(1, new Person(), "Test Expenses", new ExpensesType());
        doReturn(expenses).when(repository).save(expenses);
        assertThat(service.addExpenses(expenses)).isEqualTo(expenses);
        Mockito.verify(repository,Mockito.times(1)).save(expenses);
    }

    @Test
    @DisplayName("Редактирование нового вида расходов")
    public void editExpensesTest() {
        Expenses expenses = new Expenses(1, new Person(), "Test Expenses", new ExpensesType());
        doReturn(expenses).when(repository).save(expenses);
        assertThat(service.editExpenses(expenses)).isEqualTo(expenses);
        Mockito.verify(repository,Mockito.times(1)).save(expenses);
    }

    @Test
    @DisplayName("Удаление вида расходов по ID")
    public void deleteExpensesByIdTest() {
        doReturn(false).when(repository).existsById(1L);
        assertThat(service.removeExpenses(1)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).deleteById(1L);
        Mockito.verify(repository,Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Получение списка видов расходов")
    public void getExpensesTest() {
        Person person = new Person();
        List<Expenses> list = List.of(new Expenses(1, person, "Test Expenses", new ExpensesType()),
                                      new Expenses(2, person, "Second Test Expenses", new ExpensesType()),
                                      new Expenses(1, person, "Test Expenses too", new ExpensesType()));
        doReturn(list).when(repository).findByPerson(person);
        assertThat(service.getExpenses(person)).isEqualTo(list);
        Mockito.verify(repository,Mockito.times(1)).findByPerson(person);
    }

    @Test
    @DisplayName("Получение вида расходов по ID")
    public void getExpensesByIdTest() {
        Expenses expenses = new Expenses(1, new Person(), "Test Expenses", new ExpensesType());
        doReturn(Optional.of(expenses)).when(repository).findById(1L);
        assertThat(service.getExpensesById(1L)).isEqualTo(expenses);
        Mockito.verify(repository,Mockito.times(1)).findById(1L);
    }
}
