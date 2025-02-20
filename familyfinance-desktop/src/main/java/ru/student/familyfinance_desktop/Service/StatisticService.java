package ru.student.familyfinance_desktop.Service;

import java.time.LocalDate;
import java.util.Map;

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

}
