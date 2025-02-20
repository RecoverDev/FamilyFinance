package ru.student.familyfinance_desktop.Service.Implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Repository.IncomeRepository;
import ru.student.familyfinance_desktop.RestController.GrossBookRestController;
import ru.student.familyfinance_desktop.Service.StatisticService;

@Service
@RequiredArgsConstructor
public class StatisticServiceImplementation implements StatisticService {
    private final GrossBookRestController controller;
    private final IncomeRepository incomeRepository;

    @Override
    public Map<String, Map<String, Double>> getIncomeStatistic(LocalDate begin, LocalDate end, boolean separate) {
        Map<String,Map<String,Double>> result = new HashMap<>();
        List<GrossBook> grossBooks = controller.getGrossBooks(begin, end);

        List<LocalDate> dates = grossBooks.stream()
                                          .filter(g -> g.getIncome_id() > 0)
                                          .map(g -> LocalDate.of(g.getDateOfOperation().getYear(), g.getDateOfOperation().getMonthValue(), 1))
                                          .distinct()
                                          .sorted()
                                          .toList();

        if (separate) {
            long[] incomes = grossBooks.stream()
                                       .filter(g -> g.getIncome_id() > 0)
                                       .mapToLong(g -> g.getIncome_id())
                                       .distinct()
                                       .toArray();
            for (long l : incomes) {
                Map<String, Double> set = new LinkedHashMap<>();
                for (LocalDate date : dates) {
                    Double summ = grossBooks.stream()
                                        .filter(g -> g.getIncome_id() == l & g.getDateOfOperation().getYear() == date.getYear() & g.getDateOfOperation().getMonthValue() == date.getMonthValue())
                                        .mapToDouble(g -> g.getSumm())
                                        .sum();
                    set.put(date.format(DateTimeFormatter.ofPattern("MM.yyyy")),summ);
                }
                result.put(incomeRepository.getIncomeById(l).getName(), set);
            }
        } else {
            Map<String, Double> set = new LinkedHashMap<>();
            for (LocalDate date : dates) {
                Double summ = grossBooks.stream()
                                    .filter(g -> g.getDateOfOperation().getYear() == date.getYear() & g.getDateOfOperation().getMonthValue() == date.getMonthValue())
                                    .mapToDouble(g -> g.getSumm())
                                    .sum();
                set.put(date.format(DateTimeFormatter.ofPattern("MM.yyyy")),summ);
            }
            result.put("Доходы за период", set);

        }
        return result;
    }

}
