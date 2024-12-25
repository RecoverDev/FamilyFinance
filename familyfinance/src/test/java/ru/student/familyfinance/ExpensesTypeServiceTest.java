package ru.student.familyfinance;

import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.student.familyfinance.Repository.ExpensesTypeRepository;
import ru.student.familyfinance.Service.ExpensesTypeService;
import ru.student.familyfinance.Service.Implementation.ExpensesTypeServiceImplementation;
import ru.student.familyfinance.Model.ExpensesType;

@ExtendWith(MockitoExtension.class)
public class ExpensesTypeServiceTest {

    @Mock
    ExpensesTypeRepository repository;

    ExpensesTypeService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new ExpensesTypeServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление нового типа расходов")
    public void addExpensesTypeTest() {
        ExpensesType type = new ExpensesType(1,"Test expenses type");
        doReturn(type).when(repository).save(type);
        assertThat(service.addExpensesType(type)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).save(type);
    }

    @Test
    @DisplayName("Получение списка типов доходов")
    public void getListExpensesTypeTest() {
        List<ExpensesType> list = List.of(new ExpensesType(1,"Test expenses type"),
                                          new ExpensesType(2, "Second Expenses Type"),
                                          new ExpensesType(3, "Expenses Type too")
        );
        doReturn(list).when(repository).findAll();
        assertThat(service.getExpensesType()).isEqualTo(list);
        Mockito.verify(repository,Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Получение типа расходов по ID")
    public void getExpensesTypeByIdTest() {
        ExpensesType type = new ExpensesType(1,"Test expenses type");
        doReturn(Optional.of(type)).when(repository).findById(1L);
        assertThat(service.getExpensesTypeById(1)).isEqualTo(type);
        Mockito.verify(repository,Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Удаление типа расходов по ID")
    public void removeExpensesTypeTest() {
        doReturn(false).when(repository).existsById(1L);
        assertThat(service.removeExpensesType(1)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).deleteById(1L);;
        Mockito.verify(repository,Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Редактирование типа расходов")
    public void editExpensesTypeTest() {
        ExpensesType type = new ExpensesType(1,"Test expenses type");
        doReturn(type).when(repository).save(type);
        assertThat(service.editExpensesType(type)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).save(type);

    }

}
