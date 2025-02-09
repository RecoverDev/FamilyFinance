package ru.student.familyfinance_desktop.Service.Implementation;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Service.EnvironmentService;
import ru.student.familyfinance_desktop.Service.ExpensesService;
import ru.student.familyfinance_desktop.Service.ExpensesTypeService;
import ru.student.familyfinance_desktop.Service.IncomeService;
import ru.student.familyfinance_desktop.Service.TargetService;

@Component
@RequiredArgsConstructor
public class EnvironmentServiceImplementation implements EnvironmentService {
    private final IncomeService incomeService;
    private final ExpensesTypeService expensesTypeService;
    private final ExpensesService expensesService;
    private final TargetService targetService;

    @Override
    public void setEnvironment() {
        incomeService.setIncomes();
        expensesTypeService.setExpensesTypes();
        expensesService.setExpenses();
        targetService.setTargets();
    }
}
