package ru.student.familyfinance_desktop.Service.Implementation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.WorkPeriod;
import ru.student.familyfinance_desktop.Service.EnvironmentService;
import ru.student.familyfinance_desktop.Service.ExpensesService;
import ru.student.familyfinance_desktop.Service.ExpensesTypeService;
import ru.student.familyfinance_desktop.Service.GrossBookService;
import ru.student.familyfinance_desktop.Service.IncomeService;
import ru.student.familyfinance_desktop.Service.PlanService;
import ru.student.familyfinance_desktop.Service.TargetService;

@Component
@RequiredArgsConstructor
public class EnvironmentServiceImplementation implements EnvironmentService {
    private final IncomeService incomeService;
    private final ExpensesTypeService expensesTypeService;
    private final ExpensesService expensesService;
    private final TargetService targetService;
    private final PlanService planService;
    private final GrossBookService grossBookService;

    @Autowired
    private WorkPeriod currentPeriod;

    @Override
    public void setEnvironment() {
        incomeService.setIncomes();
        expensesTypeService.setExpensesTypes();
        expensesService.setExpenses();
        targetService.setTargets();
        planService.setPlans(currentPeriod.getCurrentPeriod());
        LocalDate begin = currentPeriod.getCurrentPeriod();
        LocalDate end = begin.plusDays(begin.lengthOfMonth() - 1);
        grossBookService.setGrossBooks(begin, end);
    }
}
