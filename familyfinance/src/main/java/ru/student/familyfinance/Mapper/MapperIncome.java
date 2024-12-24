package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.student.familyfinance.DTO.IncomeDTO;
import ru.student.familyfinance.Model.Income;

@Mapper(componentModel = "spring")
public interface MapperIncome {

    @Mapping(target="person_id", expression = "java(income.getPerson().getId())")
    IncomeDTO toIncomeDTO(Income income);

    List<IncomeDTO> toListIncomeDTO(List<Income> incomes);

    @Mapping(target = "person", ignore = true)
    Income toIncome(IncomeDTO incomeDTO);

    List<Income> toListIncomes(List<IncomeDTO> incomesDTO);

}
