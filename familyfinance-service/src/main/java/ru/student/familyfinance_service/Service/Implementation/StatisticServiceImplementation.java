package ru.student.familyfinance_service.Service.Implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import org.springframework.data.util.Pair;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_service.Model.Expenses;
import ru.student.familyfinance_service.Model.GrossBook;
import ru.student.familyfinance_service.Model.Income;
import ru.student.familyfinance_service.Model.Plan;
import ru.student.familyfinance_service.Model.Target;
import ru.student.familyfinance_service.Repository.Repository;
import ru.student.familyfinance_service.RestController.GrossBookRestController;
import ru.student.familyfinance_service.RestController.PlanRestController;
import ru.student.familyfinance_service.Service.StatisticService;

@Service
@RequiredArgsConstructor
public class StatisticServiceImplementation implements StatisticService {
    private final GrossBookRestController grossBookController;
    private final PlanRestController planController;
    private final Repository<Income> incomeRepository;
    private final Repository<Expenses> expensesRepository;
    private final Repository<Target> targetRepository;
    private final Repository<Plan> planRepository;
    private final Repository<GrossBook> grossBookRepository;

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

    @Override
    public Map<String,Map<LocalDate, Pair<Double, Double>>> getExpensesShedule(LocalDate period, boolean flag) {
        Map<String,Map<LocalDate, Pair<Double, Double>>> result = new HashMap<>();

        LocalDate begin = LocalDate.of(period.getYear(), period.getMonthValue(), 1);
        LocalDate end = LocalDate.of(period.getYear(), period.getMonthValue(), period.lengthOfMonth());

        List<LocalDate> dates = Stream.concat(Stream.iterate(begin, d -> d.plusDays(7)).limit(ChronoUnit.DAYS.between(begin,end)/7 + 1), Stream.<LocalDate>builder().add(end).build())
                                      .collect(Collectors.toList());

        double planSumm = 0;
        Map<Long,Map<LocalDate,Double>> ddd = new HashMap<>();

        if (flag) {
            ddd = grossBookRepository.getCollection()
                                            .stream()
                                            .filter(g -> g.getExpenses_id() > 0)
                                            .map(g -> getGrossBook(g, dates))
                                            .collect(Collectors.groupingBy(g -> g.getExpenses_id(), Collectors.groupingBy(g -> g.getDateOfOperation(), LinkedHashMap::new,  Collectors.summingDouble(GrossBook::getSumm))));
        } else {
            Map<LocalDate,Double> sss = grossBookRepository.getCollection()
                                            .stream()
                                            .filter(g -> g.getExpenses_id() > 0)
                                            .map(g -> getGrossBook(g, dates))
                                            .collect(Collectors.groupingBy(g -> g.getDateOfOperation(), LinkedHashMap::new, Collectors.summingDouble(GrossBook::getSumm)));
            ddd.put(0L, sss);
        }

        for (Map.Entry<Long,Map<LocalDate,Double>> entry : ddd.entrySet()) {
            String nameGraphic = "";
            if (entry.getKey() == 0) {
                planSumm = planRepository.getCollection().stream().filter(p -> p.getExpenses_id() > 0).mapToDouble(p -> p.getSumm()).sum();
                nameGraphic = "Расходы";
            } else {
                planSumm = planRepository.getCollection().stream().filter(p -> p.getExpenses_id() == entry.getKey()).mapToDouble(p -> p.getSumm()).sum();
                nameGraphic = expensesRepository.getItemById(entry.getKey()).getName();
            }
            Map<LocalDate, Pair<Double,Double>> graphicMap = new LinkedHashMap<>();
            double averageSumm = planSumm / (dates.size());
            Map<LocalDate,Double> map = entry.getValue();
            for (int d = 1; d < dates.size(); ++d) {
                double weeklySumm = 0.0;
                if (map.containsKey(dates.get(d))) {
                    weeklySumm = map.get(dates.get(d));
                }
                graphicMap.put(dates.get(d), Pair.of(weeklySumm, Math.max(weeklySumm, averageSumm)));
                if (weeklySumm != averageSumm && LocalDate.now().isAfter(dates.get(d))) {
                    int index = dates.indexOf(dates.get(d));
                    averageSumm = (planSumm - weeklySumm) / (dates.size() - index);
                }
            }
            result.put(nameGraphic, graphicMap);
        }

        return result;
    }

    private GrossBook getGrossBook(GrossBook grossBook, List<LocalDate> dates) {
        LocalDate date = grossBook.getDateOfOperation();
        for (int i = 1; i < dates.size(); ++i) {
            if (date.isAfter(dates.get(i - 1)) & date.isBefore(dates.get(i))) {
                date = dates.get(i);
            }
        }
        return new GrossBook(grossBook.getId(), date, grossBook.getPerson_id(), grossBook.getTarget_id(), grossBook.getExpenses_id(), grossBook.getIncome_id(), grossBook.getSumm());
    }

    private String getDescriptionGrossBook(GrossBook element) {
        if(element.getIncome_id() > 0) {
            return incomeRepository.getItemById(element.getIncome_id()).getName();
        } else if (element.getExpenses_id() > 0) {
            return expensesRepository.getItemById(element.getExpenses_id()).getName();
        } else {
            return targetRepository.getItemById(element.getTarget_id()).getName();
        }
    }

    private String getDescriptionPlan(Plan element) {
        if(element.getIncome_id() > 0) {
            return incomeRepository.getItemById(element.getIncome_id()).getName();
        } else if (element.getExpenses_id() > 0) {
            return expensesRepository.getItemById(element.getExpenses_id()).getName();
        } else {
            return targetRepository.getItemById(element.getTarget_id()).getName();
        }
    }
    


}
