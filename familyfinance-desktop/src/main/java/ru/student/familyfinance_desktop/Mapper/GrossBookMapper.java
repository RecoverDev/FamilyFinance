package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.GrossBookDTO;
import ru.student.familyfinance_service.Model.GrossBook;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Service.ExpensesService;
import ru.student.familyfinance_service.Service.IncomeService;
import ru.student.familyfinance_service.Service.TargetService;

@Mapper(componentModel = SPRING)
public abstract class GrossBookMapper {

    @Autowired
    protected Person person;
    
    @Autowired
    protected IncomeService incomeService;

    @Autowired
    protected ExpensesService expensesService;

    @Autowired
    protected TargetService targetService;

    @Mapping(target = "dateOfOperation", expression = "java(dateToString(grossBook))")
    @Mapping(target = "description", expression = "java(setDescription(grossBook))")
    @Mapping(target = "type", expression = "java(setType(grossBook))")
    public abstract GrossBookDTO toGrossBookDTO(GrossBook grossBook);

    public abstract List<GrossBookDTO> toListGrossBookDTO(List<GrossBook> listGrossBooks);

    @Mapping(target = "person_id", expression = "java(person.getId())")
    @Mapping(target = "dateOfOperation", expression = "java(stringToDate(grossBookDTO))")
    public abstract GrossBook toGrossBook(GrossBookDTO grossBookDTO);

    public abstract List<GrossBook> toListGrossBook(List<GrossBookDTO> listGrossBooksDTO);

    protected String dateToString(GrossBook grossBook) {
        return grossBook.getDateOfOperation().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    protected LocalDate stringToDate(GrossBookDTO grossBookDTO) {
        return LocalDate.parse(grossBookDTO.getDateOfOperation(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    protected String setDescription(GrossBook grossBook) {
        String result = "";

        if (grossBook.getIncome_id() > 0) {
            return incomeService.getIncomeById(grossBook.getIncome_id()).getName();
        }
        if (grossBook.getExpenses_id() > 0) {
            return expensesService.getExpensesById(grossBook.getExpenses_id()).getName();
        }
        if (grossBook.getTarget_id() > 0) {
            return targetService.getTargetById(grossBook.getTarget_id()).getName();
        }
        return result;
    }

    protected int setType(GrossBook grossBook) {
        if (grossBook.getIncome_id() > 0) {
            return 1;
        }
        if (grossBook.getExpenses_id() > 0) {
            return 2;
        }
        if (grossBook.getTarget_id() > 0) {
            return 3;
        }
        return 0;
    }
}
