package ru.student.familyfinance.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.GrossBookRepository;
import ru.student.familyfinance.Service.Implementation.GrossBookServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class GrossBookServiceTest {

    @Mock
    GrossBookRepository repository;

    GrossBookService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new GrossBookServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавлении новой записи в журнал")
    public void addGrossBookTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.now();
        GrossBook grossBook = new GrossBook(1, dateOperation, person, new Income(), null, null, 100.0);
        doReturn(grossBook).when(repository).save(grossBook);
        assertThat(service.addGrossBook(grossBook)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).save(grossBook);
    }

    @Test
    @DisplayName("Редактирование записи в журнал")
    public void editGrossBookTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.now();
        GrossBook grossBook = new GrossBook(1, dateOperation, person, new Income(), null, null, 100.0);
        doReturn(grossBook).when(repository).save(grossBook);
        assertThat(service.editGrossBook(grossBook)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).save(grossBook);
    }

    @Test
    @DisplayName("Удаление записи в журнале по ID")
    public void deleteGrossBookTest() {
        doReturn(false).when(repository).existsById(1L);
        assertThat(service.removeGrossBook(1)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).deleteById(1L);
        Mockito.verify(repository,Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Получение списка записей")
    public void getGrossBooksTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, new Income(), null, null, 300.0),
                                       new GrossBook(2, dateOperation, person, null, new Expenses(), null, 200.0),
                                       new GrossBook(3, dateOperation, person, null, null, new Target(), 100.0));
        LocalDate begin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        doReturn(list).when(repository).findByPersonAndDateOfOperationBetween(person, begin, end);
        assertThat(service.getGrossBooks(begin, end, person)).isEqualTo(list);
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndDateOfOperationBetween(person, begin, end);
    }

    @Test
    @DisplayName("Получение суммы дохода за период")
    public void getIncomeByPeriodTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, new Income(), null, null, 300.0),
                                       new GrossBook(2, dateOperation, person, null, new Expenses(), null, 200.0),
                                       new GrossBook(3, dateOperation, person, null, null, new Target(), 100.0));
        LocalDate begin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        doReturn(list).when(repository).findByPersonAndDateOfOperationBetween(person, begin, end);
        assertThat(service.getIncomeByPeriod(begin, end, person)).isEqualTo(300.0);
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndDateOfOperationBetween(person, begin, end);
    }

    @Test
    @DisplayName("Получение суммы расходов за период")
    public void getExpensesByPeriodTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, new Income(), null, null, 300.0),
                                       new GrossBook(2, dateOperation, person, null, new Expenses(), null, 200.0),
                                       new GrossBook(3, dateOperation, person, null, null, new Target(), 100.0));
        LocalDate begin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        doReturn(list).when(repository).findByPersonAndDateOfOperationBetween(person, begin, end);
        assertThat(service.getExpensesByPeriod(begin, end, person)).isEqualTo(200.0);
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndDateOfOperationBetween(person, begin, end);
    }

    @Test
    @DisplayName("Получение суммы отложенной на цели за период")
    public void getTargetByPeriodTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, new Income(), null, null, 300.0),
                                       new GrossBook(2, dateOperation, person, null, new Expenses(), null, 200.0),
                                       new GrossBook(3, dateOperation, person, null, null, new Target(), 100.0));
        LocalDate begin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        doReturn(list).when(repository).findByPersonAndDateOfOperationBetween(person, begin, end);
        assertThat(service.getTargetByPeriod(begin, end, person)).isEqualTo(100.0);
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndDateOfOperationBetween(person, begin, end);
    }

    @Test
    @DisplayName("Получение списка записей о доходах за период")
    public void getListIncomeByPeriodTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, new Income(), null, null, 300.0),
                                       new GrossBook(2, dateOperation, person, null, new Expenses(), null, 200.0),
                                       new GrossBook(3, dateOperation, person, null, null, new Target(), 100.0));
        LocalDate begin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        doReturn(list).when(repository).findByPersonAndDateOfOperationBetween(person, begin, end);
        assertThat(service.getListIncomesByPeriod(begin, end, person)).isEqualTo(List.of(list.get(0)));
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndDateOfOperationBetween(person, begin, end);
    }

    @Test
    @DisplayName("Получение списка записей о расходах за период")
    public void getListExpensesByPeriodTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, new Income(), null, null, 300.0),
                                       new GrossBook(2, dateOperation, person, null, new Expenses(), null, 200.0),
                                       new GrossBook(3, dateOperation, person, null, null, new Target(), 100.0));
        LocalDate begin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        doReturn(list).when(repository).findByPersonAndDateOfOperationBetween(person, begin, end);
        assertThat(service.getListExpensesByPeriod(begin, end, person)).isEqualTo(List.of(list.get(1)));
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndDateOfOperationBetween(person, begin, end);
    }

    @Test
    @DisplayName("Получение списка записей о целях за период")
    public void getListTargetByPeriodTest() {
        Person person = new Person();
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, new Income(), null, null, 300.0),
                                       new GrossBook(2, dateOperation, person, null, new Expenses(), null, 200.0),
                                       new GrossBook(3, dateOperation, person, null, null, new Target(), 100.0));
        LocalDate begin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        doReturn(list).when(repository).findByPersonAndDateOfOperationBetween(person, begin, end);
        assertThat(service.getListTargetByPeriod(begin, end, person)).isEqualTo(List.of(list.get(2)));
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndDateOfOperationBetween(person, begin, end);
    }

    @Test
    @DisplayName("Получение записей о целях по списку целей")
    public void getListTargetByScrollTest() {
        Person person = new Person();
        List<Target> listTarget = List.of(new Target(1, person, "FirstTarget", 100.0, LocalDate.of(2024, 10, 1)),
                                          new Target(2, person, "Second Target", 200.0, LocalDate.of(2024,10,10)),
                                          new Target(3, person, "Next Target", 300.0, LocalDate.of(2024,11,10)));
        LocalDate dateOperation = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 15);
        List<GrossBook> list = List.of(new GrossBook(1, dateOperation, person, null, null, listTarget.get(0), 300.0),
                                        new GrossBook(2, dateOperation, person, null, null, listTarget.get(1), 200.0),
                                        new GrossBook(3, dateOperation, person, null, null, listTarget.get(2), 100.0));

        doReturn(list).when(repository).findByPersonAndTargetIsIn(person, listTarget);
        assertThat(service.getListTargetByScroll(listTarget, person)).isEqualTo(list);
        Mockito.verify(repository, Mockito.times(1)).findByPersonAndTargetIsIn(person, listTarget);
    }
}
