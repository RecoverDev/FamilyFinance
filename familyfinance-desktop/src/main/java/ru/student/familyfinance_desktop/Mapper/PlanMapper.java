package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.PlanDTO;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Service.ExpensesService;
import ru.student.familyfinance_desktop.Service.IncomeService;
import ru.student.familyfinance_desktop.Service.TargetService;

@Mapper(componentModel = SPRING)
public abstract class PlanMapper {

    @Autowired
    protected Person person;
    
    @Autowired
    protected IncomeService incomeService;

    @Autowired
    protected ExpensesService expensesService;

    @Autowired
    protected TargetService targetService;

    @Mapping(target = "dateOfOperation", expression = "java(dateToString(plan))")
    @Mapping(target = "description", expression = "java(setDescription(plan))")
    @Mapping(target = "type", expression = "java(setType(plan))")
    public abstract PlanDTO toPlanDTO(Plan plan);

    public abstract List<PlanDTO> toListPlanDTO(List<Plan> plans);

    @Mapping(target = "person_id", expression = "java(person.getId())")
    @Mapping(target = "dateOfOperation", expression = "java(stringToDate(planDTO))")
    public abstract Plan toPlan(PlanDTO planDTO);

    public abstract List<Plan> toListPlan(List<PlanDTO> plansDTO);

    protected String dateToString(Plan plan) {
        return plan.getDateOfOperation().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    protected LocalDate stringToDate(PlanDTO planDTO) {
        return LocalDate.parse(planDTO.getDateOfOperation(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    protected String setDescription(Plan plan) {
        String result = "";

        if (plan.getIncome_id() > 0) {
            return incomeService.getIncomeById(plan.getIncome_id()).getName();
        }
        if (plan.getExpenses_id() > 0) {
            return expensesService.getExpensesById(plan.getExpenses_id()).getName();
        }
        if (plan.getTarget_id() > 0) {
            return targetService.getTargetById(plan.getTarget_id()).getName();
        }
        return result;
    }

    protected int setType(Plan plan) {
        if (plan.getIncome_id() > 0) {
            return 1;
        }
        if (plan.getExpenses_id() > 0) {
            return 2;
        }
        if (plan.getTarget_id() > 0) {
            return 3;
        }
        return 0;
    }
}
