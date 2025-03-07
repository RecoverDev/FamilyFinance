package ru.student.familyfinance_desktop.Service.Implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.Expenses;
import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Repository.IncomeRepository;
import ru.student.familyfinance_desktop.Repository.Repository;
import ru.student.familyfinance_desktop.RestController.GrossBookRestController;
import ru.student.familyfinance_desktop.RestController.PlanRestController;
import ru.student.familyfinance_desktop.Service.StatisticService;

@Service
@RequiredArgsConstructor
public class StatisticServiceImplementation implements StatisticService {
    private final GrossBookRestController grossBookController;
    private final PlanRestController planController;
    private final IncomeRepository incomeRepository;
    private final Repository<Expenses> expensesRepository;
    private final Repository<Target> targetRepository;

    @Override
    public Map<String, Map<String, Double>> getIncomeStatistic(LocalDate begin, LocalDate end, boolean separate) {
        Map<String,Map<String,Double>> result = new HashMap<>();
        List<GrossBook> grossBooks = grossBookController.getGrossBooks(begin, end);

        if (separate) {
            result = grossBooks.stream()
                                .filter(g -> g.getIncome_id() > 0)
                                .map(g -> new GrossBook(g.getId(), LocalDate.of(g.getDateOfOperation().getYear(),g.getDateOfOperation().getMonthValue(),1), g.getPerson_id(), g.getTarget_id(), g.getExpenses_id(), g.getIncome_id(), g.getSumm()))
                                .sorted((a, b) -> a.getDateOfOperation().compareTo(b.getDateOfOperation()))
                                .collect(Collectors.groupingBy(g -> getDescriptionGrossBook(g),Collectors.groupingBy(g -> g.getDateOfOperation().format(DateTimeFormatter.ofPattern("MM.yyyy")), LinkedHashMap::new, Collectors.summingDouble(GrossBook::getSumm))));
        } else {
            Map<String, Double> set = grossBooks.stream()
                               .filter(g -> g.getIncome_id() > 0)
                               .sorted((a, b) -> a.getDateOfOperation().compareTo(b.getDateOfOperation()))
                               .map(g -> new GrossBook(g.getId(), LocalDate.of(g.getDateOfOperation().getYear(),g.getDateOfOperation().getMonthValue(),1), g.getPerson_id(), g.getTarget_id(), g.getExpenses_id(), g.getIncome_id(), g.getSumm()))
                               .collect(Collectors.groupingBy(g -> g.getDateOfOperation().format(DateTimeFormatter.ofPattern("MM.yyyy")), LinkedHashMap::new, Collectors.summingDouble(GrossBook::getSumm)));
            result.put("Доходы за период", set);

        }
        return result;
    }

    @Override
    public Map<String, Map<String, Double>> getExpensesStatistic(LocalDate begin, LocalDate end, boolean separate) {
        Map<String,Map<String,Double>> result = new HashMap<>();
        List<GrossBook> grossBooks = grossBookController.getGrossBooks(begin, end);

        if (separate) {
            result = grossBooks.stream()
                                .filter(g -> g.getExpenses_id() > 0)
                                .sorted((a, b) -> a.getDateOfOperation().compareTo(b.getDateOfOperation()))
                                .map(g -> new GrossBook(g.getId(), LocalDate.of(g.getDateOfOperation().getYear(),g.getDateOfOperation().getMonthValue(),1), g.getPerson_id(), g.getTarget_id(), g.getExpenses_id(), g.getIncome_id(), g.getSumm()))
                                .collect(Collectors.groupingBy(g -> getDescriptionGrossBook(g),Collectors.groupingBy(g -> g.getDateOfOperation().format(DateTimeFormatter.ofPattern("MM.yyyy")), LinkedHashMap::new, Collectors.summingDouble(GrossBook::getSumm))));
        } else {
            Map<String, Double> set = grossBooks.stream()
                               .filter(g -> g.getExpenses_id() > 0)
                               .sorted((a, b) -> a.getDateOfOperation().compareTo(b.getDateOfOperation()))
                               .map(g -> new GrossBook(g.getId(), LocalDate.of(g.getDateOfOperation().getYear(),g.getDateOfOperation().getMonthValue(),1), g.getPerson_id(), g.getTarget_id(), g.getExpenses_id(), g.getIncome_id(), g.getSumm()))
                               .collect(Collectors.groupingBy(g -> g.getDateOfOperation().format(DateTimeFormatter.ofPattern("MM.yyyy")), LinkedHashMap::new, Collectors.summingDouble(GrossBook::getSumm)));
            result.put("Расходы за период", set);

        }
        return result;
    }

    @Override
    public Map<String, Map<String, Double>> getBudget(LocalDate period) {
        Map<String, Map<String, Double>> result = new HashMap<>();

        LocalDate beginDate = LocalDate.of(period.getYear(),period.getMonthValue(),1);
        LocalDate endDate = LocalDate.of(period.getYear(), period.getMonthValue(), period.lengthOfMonth()); 
        List<GrossBook> grossBooks = grossBookController.getGrossBooks(beginDate, endDate);
        List<Plan> plans = planController.getPlansSeveralMonth(beginDate, endDate);

        Map<String,Double> grossBookSummary = grossBooks.stream()
                                               .collect(Collectors.groupingBy(g -> getDescriptionGrossBook(g), Collectors.summingDouble(GrossBook::getSumm)));
        
        Map<String,Double> planSummary = plans.stream()
                                               .collect(Collectors.groupingBy(p -> getDescriptionPlan(p), Collectors.summingDouble(Plan::getSumm)));
        result.put("Факт", grossBookSummary);
        result.put("План", planSummary);

        return result;
    }

    private String getDescriptionGrossBook(GrossBook element) {
        if(element.getIncome_id() > 0) {
            return incomeRepository.getIncomeById(element.getIncome_id()).getName();
        } else if (element.getExpenses_id() > 0) {
            return expensesRepository.getItemById(element.getExpenses_id()).getName();
        } else {
            return targetRepository.getItemById(element.getTarget_id()).getName();
        }
    }

    private String getDescriptionPlan(Plan element) {
        if(element.getIncome_id() > 0) {
            return incomeRepository.getIncomeById(element.getIncome_id()).getName();
        } else if (element.getExpenses_id() > 0) {
            return expensesRepository.getItemById(element.getExpenses_id()).getName();
        } else {
            return targetRepository.getItemById(element.getTarget_id()).getName();
        }
    }
    


}
