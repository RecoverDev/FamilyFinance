package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.ExpensesDTO;
import ru.student.familyfinance_service.Model.Expenses;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Service.ExpensesTypeService;

@Mapper(componentModel = SPRING)
public abstract class ExpensesMapper {

    @Autowired
    protected ExpensesTypeService expensesTypeService;

    @Autowired
    protected Person person;

    @Mapping(target = "expensesType_name", expression = "java(expensesTypeService.getExpensesTypeById(expensesType_id).getName())")
    public abstract ExpensesDTO toExpensesDTO(Expenses expenses);

    public abstract List<ExpensesDTO> toListExpensesDTO(List<Expenses> listExpenses);

    @Mapping(target = "person_id", expression = "java(person.getId())")
    public abstract Expenses toExpenses(ExpensesDTO expensesDTO);

    public abstract List<Expenses> toListExpenses(List<ExpensesDTO> listExpensesDTO);

}
