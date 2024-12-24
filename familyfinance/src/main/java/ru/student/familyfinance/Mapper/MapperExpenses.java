package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.student.familyfinance.DTO.ExpensesDTO;
import ru.student.familyfinance.Model.Expenses;

@Mapper(componentModel = "spring")
public interface MapperExpenses {

    @Mapping(target = "person_id", expression = "java(expenses.getPerson().getId())")
    @Mapping(target = "expensesType_id", expression =  "java(expenses.getExpensesType().getId())")
    ExpensesDTO toExpensesDTO(Expenses expenses);

    List<ExpensesDTO> toListExpensesDTO(List<Expenses> expenses);
    
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "expensesType", ignore = true)
    Expenses toExpenses(ExpensesDTO expensesDTO);

    List<Expenses> toListExpenses(List<ExpensesDTO> expensesDTO);

}
