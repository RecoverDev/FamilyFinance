package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.student.familyfinance.DTO.GrossBookDTO;
import ru.student.familyfinance.Model.GrossBook;

@Mapper(componentModel = "spring")
public interface MapperGrossBook {

    @Mapping(target = "person", ignore = true)
    @Mapping(target = "target", ignore = true)
    @Mapping(target = "income", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    GrossBook toGrossBook(GrossBookDTO grossBookDTO);

    List<GrossBook> toListGrossBook(List<GrossBookDTO> listGrossBookDTO);

    @Mapping(target = "person_id", expression = "java(grossBook.getPerson().getId())")
    @Mapping(target = "income_id", expression = "java(grossBook.getIncome() != null ? grossBook.getIncome().getId() : 0)")
    @Mapping(target = "expenses_id", expression = "java(grossBook.getExpenses() != null ? grossBook.getExpenses().getId() : 0)")
    @Mapping(target = "target_id", expression = "java(grossBook.getTarget() != null ? grossBook.getTarget().getId() : 0)")
    GrossBookDTO toGrossBookDTO(GrossBook grossBook);

    List<GrossBookDTO> toListGrossBookDTO(List<GrossBook> listGrossBook);

}
