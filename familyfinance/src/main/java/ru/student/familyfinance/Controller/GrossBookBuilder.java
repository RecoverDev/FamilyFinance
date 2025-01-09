package ru.student.familyfinance.Controller;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.DTO.GrossBookDTO;
import ru.student.familyfinance.Mapper.MapperGrossBook;
import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Service.ExpensesService;
import ru.student.familyfinance.Service.IncomeService;
import ru.student.familyfinance.Service.TargetService;

@Component
@RequiredArgsConstructor
public class GrossBookBuilder {
    private final MapperGrossBook mapper;
    private final IncomeService incomeService;
    private final ExpensesService expensesService;
    private final TargetService targetService;

    public GrossBook buildGrossBook(Person person, GrossBookDTO grossBookDTO) {
        GrossBook result = mapper.toGrossBook(grossBookDTO);
        result.setPerson(person);
        result.setIncome(incomeService.getIncomeById(grossBookDTO.getIncome_id()));
        result.setExpenses(expensesService.getExpensesById(grossBookDTO.getExpenses_id()));
        result.setTarget(targetService.getTargetById(grossBookDTO.getTarget_id()));

        return result;
    }
}
