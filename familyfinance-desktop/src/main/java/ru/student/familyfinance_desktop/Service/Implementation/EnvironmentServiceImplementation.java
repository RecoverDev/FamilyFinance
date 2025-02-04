package ru.student.familyfinance_desktop.Service.Implementation;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Service.EnvironmentService;
import ru.student.familyfinance_desktop.Service.IncomeService;

@Component
@RequiredArgsConstructor
public class EnvironmentServiceImplementation implements EnvironmentService {
    private final IncomeService incomeService;

    @Override
    public void setEnvironment() {
        incomeService.setIncomes();
    }
}
