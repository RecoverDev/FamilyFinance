package ru.student.familyfinance.Controller;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.DTO.PlanDTO;
import ru.student.familyfinance.Mapper.MapperPlan;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Plan;
import ru.student.familyfinance.Service.ExpensesService;
import ru.student.familyfinance.Service.IncomeService;
import ru.student.familyfinance.Service.TargetService;

@Component
@RequiredArgsConstructor
public class Builder {
    private final MapperPlan mapper;
    private final IncomeService incomeService;
    private final ExpensesService expensesService;
    private final TargetService targetService;

    protected Plan buildPlan(PlanDTO planDTO, Person person) {
        Plan plan = mapper.toPlan(planDTO);
        plan.setPerson(person);
        plan.setIncome(incomeService.getIncomeById(planDTO.getIncome_id()));
        plan.setExpenses(expensesService.getExpensesById(planDTO.getExpenses_id()));
        plan.setTarget(targetService.getTargetById(planDTO.getTarget_id()));

        return plan;
    }


}
