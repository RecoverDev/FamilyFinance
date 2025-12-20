package ru.student.familyfinance_service.Service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.util.Pair;

/**
 * Сервис по расчету статистик
 */
public interface StatisticService {

    /**
     * Возвращает данные о доходах за период
     * @param begin - начало отчетного периода
     * @param end - конец отчетного периода
     * @param separate - раздельно по видам доходов
     * @return - список данных для построения графиков
     */
    Map<String, Map<String, Double>> getIncomeStatistic(LocalDate begin, LocalDate end, boolean separate);

    /**
     * Возвращает данные о расходах за период
     * @param begin - начало отчетного периода
     * @param end - конец отчетного периода
     * @param separate - раздельно по видам доходов
     * @return - список данных для построения графиков
     */
    Map<String, Map<String, Double>> getExpensesStatistic(LocalDate begin, LocalDate end, boolean separate);

    /**
     * Возвращает данные для визуализации исполнения бюджета за выбранный период
     * @param period - период (первое число месяца)
     * @return - список данных
     */
    Map<String, Map<String, Double>> getBudget(LocalDate period);

    /**
     * Возвращает данные для визуализации выполнения графика расходов
     * @param flag - разделять по видам расходов
     * @param period - период, за который рассчитывается статистика
     * @return - Map<String,Map<LocalDate, Pair<Double, Double>>> (дата окончания недели, потраченная сумма, максимально возможная сумма)
     */
    Map<String,Map<LocalDate, Pair<Double, Double>>> getExpensesShedule(LocalDate period, boolean flag);
}
