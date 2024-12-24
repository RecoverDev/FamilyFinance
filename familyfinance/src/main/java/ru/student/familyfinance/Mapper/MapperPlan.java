package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.student.familyfinance.DTO.PlanDTO;
import ru.student.familyfinance.Model.Plan;

@Mapper(componentModel = "spring")
public interface MapperPlan {

    @Mapping(target = "person", ignore = true)
    @Mapping(target = "target", ignore = true)
    @Mapping(target = "income", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    Plan toPlan(PlanDTO planDTO);

    List<Plan> toListPlan(List<PlanDTO> plansDTO);

    @Mapping(target = "person_id", expression = "java(plan.getPerson().getId())")
    @Mapping(target = "income_id", expression = "java(plan.getIncome() != null ? plan.getIncome().getId() : 0)")
    @Mapping(target = "expenses_id", expression = "java(plan.getExpenses() != null ? plan.getExpenses().getId() : 0)")
    @Mapping(target = "target_id", expression = "java(plan.getTarget() != null ? plan.getTarget().getId() : 0)")
    PlanDTO toPlanDTO(Plan plan);

    List<PlanDTO> toListPlanDTO(List<Plan> plans);

}
