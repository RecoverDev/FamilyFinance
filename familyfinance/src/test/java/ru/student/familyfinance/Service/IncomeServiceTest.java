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

import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.IncomeRepository;
import ru.student.familyfinance.Service.Implementation.IncomeServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    IncomeRepository repository;

    IncomeService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new IncomeServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление новой категории дохода")
    public void addIncomeTest() {
        Income income = new Income(1, new Person(), "Test Income");
        doReturn(income).when(repository).save(income);
        assertThat(service.addIncome(income)).isEqualTo(income);
        Mockito.verify(repository,Mockito.times(1)).save(income);
    }

    @Test
    @DisplayName("Редактирование новой категории дохода")
    public void editIncomeTest() {
        Income income = new Income(1, new Person(), "Test Income");
        doReturn(income).when(repository).save(income);
        assertThat(service.editIncome(income)).isEqualTo(income);
        Mockito.verify(repository,Mockito.times(1)).save(income);
    }

    @Test
    @DisplayName("Удаление категории доходов")
    public void deleteIncomeByIdTest() {
        doReturn(false).when(repository).existsById(1L);
        assertThat(service.removeIncome(1)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).deleteById(1L);
        Mockito.verify(repository,Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Получение списка категорий дохода")
    public void getIncomesTest() {
        Person person = new Person();
        List<Income> list = List.of(new Income(1, person, "Test Income"),
                                    new Income(2, person, "Second Test Income"),
                                    new Income(3, person, "Test Income too"));
        doReturn(list).when(repository).findByPerson(person);
        assertThat(service.getIncomes(person)).isEqualTo(list);
        Mockito.verify(repository,Mockito.times(1)).findByPerson(person);
    }

    @Test
    @DisplayName("Получение категории дохода по ID")
    public void getIncomeByIdTest() {
        Income income = new Income(1, new Person(), "Test Income");
        doReturn(Optional.of(income)).when(repository).findById(1L);
        assertThat(service.getIncomeById(1L)).isEqualTo(income);
        Mockito.verify(repository,Mockito.times(1)).findById(1L);
    }
 }
