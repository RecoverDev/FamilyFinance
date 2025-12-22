package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.IncomeDTO;
import ru.student.familyfinance_service.Model.Income;
import ru.student.familyfinance_service.Model.Person;

@Mapper(componentModel = SPRING)
public abstract class IncomeMapper {

    @Autowired
    protected Person person;

    public abstract IncomeDTO toIncomeDTO(Income income);
    public abstract List<IncomeDTO> toListIncomeDTO(List<Income> incomes);

    @Mapping(target = "person_id", expression = "java(person.getId())")
    public abstract Income toIncome(IncomeDTO incomeDTO);
    public abstract List<Income> toListIncome(List<IncomeDTO> incomesDTO);

}
