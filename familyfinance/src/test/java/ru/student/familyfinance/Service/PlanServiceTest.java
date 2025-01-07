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
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Plan;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.PlanRepository;
import ru.student.familyfinance.Service.Implementation.PlanServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {

    @Mock
    PlanRepository repository;

    PlanService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new PlanServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление нового пункта плана")
    public void addPlanTest() {
        Plan plan = new Plan(1, LocalDate.now(), new Person(), new Income(), null, null, 100.0);
        doReturn(plan).when(repository).save(plan);
        assertThat(service.addPlan(plan)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).save(plan);
    }

    @Test
    @DisplayName("Редактирование нового пункта плана")
    public void editPlanTest() {
        Plan plan = new Plan(1, LocalDate.now(), new Person(), new Income(), null, null, 100.0);
        doReturn(plan).when(repository).save(plan);
        assertThat(service.editPlan(plan)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).save(plan);
    }

    @Test
    @DisplayName("Удаление пункта плана по ID")
    public void deletePlanTest() {
        doReturn(false).when(repository).existsById(1L);
        assertThat(service.removePlan(1)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).deleteById(1L);
        Mockito.verify(repository,Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Получение списка записей плана")
    public void getPlanTest() {
        Person person = new Person();
        LocalDate datePlans = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        List<Plan> list = List.of(new Plan(1, datePlans, person, new Income(), null, null, 200.0),
                                  new Plan(2, datePlans, person, null, new  Expenses(), null, 100.0),
                                  new Plan(3, datePlans, person, null, null, new Target(), 100.0));
        doReturn(list).when(repository).findByPersonAndDateOfOperation(person, datePlans);
        assertThat(service.getPlans(person, datePlans)).isEqualTo(list);
        Mockito.verify(repository,Mockito.times(1)).findByPersonAndDateOfOperation(person, datePlans);
    }

    @Test
    @DisplayName("Получение планируемой суммы дохода за период")
    public void getIncomePlanTest() {
        Person person = new Person();
        LocalDate datePlans = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        List<Plan> list = List.of(new Plan(1, datePlans, person, new Income(), null, null, 200.0),
                                  new Plan(2, datePlans, person, null, new  Expenses(), null, 100.0),
                                  new Plan(3, datePlans, person, null, null, new Target(), 100.0));
        doReturn(list).when(repository).findByPersonAndDateOfOperation(person, datePlans);
        assertThat(service.getIncomePlan(person, datePlans)).isEqualTo(200.0);
        Mockito.verify(repository,Mockito.times(1)).findByPersonAndDateOfOperation(person, datePlans);
    }

    @Test
    @DisplayName("Получение планируемой суммы расходов за период")
    public void getExpensesPlanTest() {
        Person person = new Person();
        LocalDate datePlans = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        List<Plan> list = List.of(new Plan(1, datePlans, person, new Income(), null, null, 200.0),
                                  new Plan(2, datePlans, person, null, new  Expenses(), null, 100.0),
                                  new Plan(3, datePlans, person, null, null, new Target(), 100.0));
        doReturn(list).when(repository).findByPersonAndDateOfOperation(person, datePlans);
        assertThat(service.getExpensesPlan(person, datePlans)).isEqualTo(100.0);
        Mockito.verify(repository,Mockito.times(1)).findByPersonAndDateOfOperation(person, datePlans);
    }

    @Test
    @DisplayName("Получение планируемой суммы отложенной на цели за период")
    public void getTargetPlanTest() {
        Person person = new Person();
        LocalDate datePlans = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        List<Plan> list = List.of(new Plan(1, datePlans, person, new Income(), null, null, 200.0),
                                  new Plan(2, datePlans, person, null, new  Expenses(), null, 100.0),
                                  new Plan(3, datePlans, person, null, null, new Target(), 100.0));
        doReturn(list).when(repository).findByPersonAndDateOfOperation(person, datePlans);
        assertThat(service.getTargetPlan(person, datePlans)).isEqualTo(100.0);
        Mockito.verify(repository,Mockito.times(1)).findByPersonAndDateOfOperation(person, datePlans);
    }
}
