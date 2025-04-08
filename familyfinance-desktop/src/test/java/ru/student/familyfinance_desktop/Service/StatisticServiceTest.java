package ru.student.familyfinance_desktop.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javafx.util.Pair;
import ru.student.familyfinance_desktop.Model.Expenses;
import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Model.Income;
import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Repository.Repository;
import ru.student.familyfinance_desktop.Repository.Implementation.ExpensesRepositoryImplementation;
import ru.student.familyfinance_desktop.Repository.Implementation.GrossBookRepositoryImplementation;
import ru.student.familyfinance_desktop.Repository.Implementation.IncomeRepositoryImplementation;
import ru.student.familyfinance_desktop.Repository.Implementation.PlanRepositoryImplementation;
import ru.student.familyfinance_desktop.Repository.Implementation.TargetRepositoryImplementation;
import ru.student.familyfinance_desktop.RestController.GrossBookRestController;
import ru.student.familyfinance_desktop.RestController.PlanRestController;
import ru.student.familyfinance_desktop.Service.Implementation.StatisticServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTest {
    private Repository<Income> incomeRepository = new IncomeRepositoryImplementation();
    private Repository<Expenses> expensesRepository = new ExpensesRepositoryImplementation();
    private Repository<Target> targetRepository = new TargetRepositoryImplementation();
    private Repository<Plan> planRepository = new PlanRepositoryImplementation();
    private Repository<GrossBook> grossbookRepository = new GrossBookRepositoryImplementation();

    @Mock
    private GrossBookRestController grossBookController;

    @Mock
    private PlanRestController planController;

    private StatisticService service;

    @BeforeEach
    private void init() {
        MockitoAnnotations.openMocks(this);
        service = new StatisticServiceImplementation(grossBookController, planController, incomeRepository, expensesRepository, targetRepository, planRepository, grossbookRepository);
        getListIncome();
        getListExpenses();
        getListTargets();
    }

    @Test
    @DisplayName("Возвращает данные о доходах за период общей суммой")
    public void getIncomeStatisticTest() {
        LocalDate begin = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        doReturn(getListGrossBook()).when(grossBookController).getGrossBooks(begin, end);

        Map<String, Map<String,Double>> result = Map.ofEntries(Map.entry("Доходы за период", Map.ofEntries(Map.entry("02.2025", 15100.0))));

        assertThat(service.getIncomeStatistic(begin, end, false)).isEqualTo(result);
        Mockito.verify(grossBookController, Mockito.times(1)).getGrossBooks(begin, end);
    }

    @Test
    @DisplayName("Возвращает данные о доходах за период с разделением по видам доходов")
    public void getIncomeStatisticSeparateIncomesTest() {
        LocalDate begin = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        doReturn(getListGrossBook()).when(grossBookController).getGrossBooks(begin, end);

        Map<String, Map<String,Double>> result = Map.ofEntries(Map.entry("Заработная плата", Map.ofEntries(Map.entry("02.2025", 10000.0))),
                                                               Map.entry("Пенсия", Map.ofEntries(Map.entry("02.2025", 5000.0))),
                                                               Map.entry("Процент от вкладов", Map.ofEntries(Map.entry("02.2025", 100.0))));

        assertThat(service.getIncomeStatistic(begin, end, true)).isEqualTo(result);
        Mockito.verify(grossBookController, Mockito.times(1)).getGrossBooks(begin, end);
    }

    @Test
    @DisplayName("Возвращает данные о расходах за период общей суммой")
    public void getExpensesStatisticTest() {
        LocalDate begin = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        doReturn(getListGrossBook()).when(grossBookController).getGrossBooks(begin, end);

        Map<String, Map<String,Double>> result = Map.ofEntries(Map.entry("Расходы за период", Map.ofEntries(Map.entry("02.2025", 800.0))));

        assertThat(service.getExpensesStatistic(begin, end, false)).isEqualTo(result);
        Mockito.verify(grossBookController, Mockito.times(1)).getGrossBooks(begin, end);
    }

    @Test
    @DisplayName("Возвращает данные о расходах за период с разделением по видам расходов")
    public void getExpensesStatisticSeparateExpensesTest() {
        LocalDate begin = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        doReturn(getListGrossBook()).when(grossBookController).getGrossBooks(begin, end);

        Map<String, Map<String,Double>> result = Map.ofEntries(Map.entry("Продукты", Map.ofEntries(Map.entry("02.2025", 100.0))),
                                                               Map.entry("Коммунальные платежи", Map.ofEntries(Map.entry("02.2025", 500.0))),
                                                               Map.entry("Оплата за квартиру", Map.ofEntries(Map.entry("02.2025", 200.0))));

        assertThat(service.getExpensesStatistic(begin, end, true)).isEqualTo(result);
        Mockito.verify(grossBookController, Mockito.times(1)).getGrossBooks(begin, end);
    }

    @Test
    @DisplayName("Исполнение бюджета")
    public void getBudgetTest() {
        LocalDate begin = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        doReturn(getListGrossBook()).when(grossBookController).getGrossBooks(begin, end);
        doReturn(getListPlans()).when(planController).getPlansSeveralMonth(begin, end);

        Map<String, Map<String,Double>> result = Map.ofEntries(Map.entry("Факт",Map.ofEntries(Map.entry("Заработная плата", 10000.0),
                                                                                                Map.entry("Пенсия", 5000.0),
                                                                                                Map.entry("Процент от вкладов", 100.0),
                                                                                                Map.entry("Продукты", 100.0),
                                                                                                Map.entry("Коммунальные платежи", 500.0),
                                                                                                Map.entry("Оплата за квартиру", 200.0),
                                                                                                Map.entry("Самолет", 10000.0))),
                                                               Map.entry("План",Map.ofEntries(Map.entry("Заработная плата", 10000.0),
                                                                                                Map.entry("Пенсия", 5000.0),
                                                                                                Map.entry("Процент от вкладов", 100.0),
                                                                                                Map.entry("Продукты", 100.0),
                                                                                                Map.entry("Коммунальные платежи", 500.0),
                                                                                                Map.entry("Оплата за квартиру", 200.0),
                                                                                                Map.entry("Самолет", 10000.0))));

        assertThat(service.getBudget(begin)).isEqualTo(result);
        Mockito.verify(grossBookController, Mockito.times(1)).getGrossBooks(begin, end);
        Mockito.verify(planController, Mockito.times(1)).getPlansSeveralMonth(begin, end);
    }

    @Test
    @DisplayName("График расходов")
    public void ExpensesSheduleTest() {
        grossbookRepository.setCollection(getListGrossBook());
        planRepository.setCollection(getListPlans());
        LocalDate date = LocalDate.of(2025, 2, 1);
        Map<LocalDate, Pair<Double, Double>> progress = Map.ofEntries(Map.entry(LocalDate.of(2025,2,8), new Pair<Double, Double>(800.0,800.0)),
                                                                    Map.entry(LocalDate.of(2025,2,15), new Pair<Double, Double>(0.0,0.0)),
                                                                    Map.entry(LocalDate.of(2025,2,22), new Pair<Double, Double>(0.0,0.0)),
                                                                    Map.entry(LocalDate.of(2025,2,28), new Pair<Double, Double>(0.0,0.0)));
        
        Map<String,Map<LocalDate, Pair<Double, Double>>> result = Map.ofEntries(Map.entry("Расходы", progress));
        Map<String,Map<LocalDate, Pair<Double, Double>>> response = service.getExpensesShedule(date, false);

        assertThat(response).isEqualTo(result);
        
    }

    private void getListIncome() {
        List.of(new Income(1, 1, "Заработная плата"),
                       new Income(2, 1, "Пенсия"),
                       new Income(3, 1, "Процент от вкладов"))
        .forEach(i -> incomeRepository.addItem(i));
    }

    private void getListExpenses() {
        List.of(new Expenses(1, 1, "Продукты", 1),
                       new Expenses(2, 1, "Коммунальные платежи", 1),
                       new Expenses(3, 1, "Оплата за квартиру", 1))
        .forEach(e -> expensesRepository.addItem(e));
    }

    private void getListTargets() {
        List.of(new Target(1, 1, "Самолет", 1000000, LocalDate.of(2024,12,31)))
        .forEach(t ->targetRepository.addItem(t));
    }
    
    private List<GrossBook> getListGrossBook() {
        return List.of( new GrossBook(1, LocalDate.of(2025, 2, 10), 1, 0, 0, 1, 10000.0),
                        new GrossBook(2, LocalDate.of(2025, 2, 2), 1, 0, 0, 2, 5000.0),
                        new GrossBook(3, LocalDate.of(2025, 2, 20), 1, 0, 0, 3, 100.0),
                        new GrossBook(4, LocalDate.of(2025, 2, 3), 1, 0, 1, 0, 100.0),
                        new GrossBook(5, LocalDate.of(2025, 2, 4), 1, 0, 2, 0, 500.0),
                        new GrossBook(6, LocalDate.of(2025, 2, 5), 1, 0, 3, 0, 200.0),
                        new GrossBook(7, LocalDate.of(2025, 2, 6), 1, 1, 0, 0, 10000.0));

    }

    private List<Plan> getListPlans() {
        LocalDate planDate = LocalDate.of(2025, 2, 1);
        return List.of(new Plan(1, planDate, 1, 0, 0, 1, 10000),
                       new Plan(2, planDate, 1, 0, 0, 2, 5000.0),
                       new Plan(3, planDate, 1, 0, 0, 3, 100.0),
                       new Plan(4, planDate, 1, 0, 1, 0, 100.0),
                       new Plan(5, planDate, 1, 0, 2, 0, 500.0),
                       new Plan(6, planDate, 1, 0, 3, 0, 200.0),
                       new Plan(7, planDate, 1, 1, 0, 0, 10000.0));
    }

}
