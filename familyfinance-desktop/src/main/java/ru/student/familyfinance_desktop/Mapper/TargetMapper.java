package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.student.familyfinance_desktop.DTO.TargetDTO;
import ru.student.familyfinance_service.Model.GrossBook;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Model.Target;
import ru.student.familyfinance_service.Service.GrossBookService;
import ru.student.familyfinance_service.Service.TargetService;

@Mapper(componentModel = SPRING)
public abstract class TargetMapper {
    
    @Autowired
    protected GrossBookService grossBookService;

    @Autowired
    protected TargetService targetService;

    @Autowired
    protected Person person;

    @Mapping(target = "accumulateSumm", expression = "java(getCalculateAccumulateSumm(target))")
    @Mapping(target = "settingDate", expression = "java(dateToString(target))")
    @Mapping(target = "percent", ignore = true)
    public abstract TargetDTO toTargetDTO(Target target);

    public abstract List<TargetDTO> toListTargetDTO(List<Target> targets);

    @Mapping(target = "settingDate", expression = "java(stringToDate(targetDTO))")
    @Mapping(target = "person_id", expression = "java(person.getId())")
    public abstract Target toTarget(TargetDTO targetDTO);

    public abstract List<Target> toListTarget(List<TargetDTO> targetsDTO);

    
    protected double getCalculateAccumulateSumm(Target target) {
        List<GrossBook> listGrossBooks = grossBookService.getGrossBookByScroll(targetService.getTargets());        
        if (listGrossBooks == null) {
            return 0.0;
        }
        return listGrossBooks.stream().filter(g -> g.getTarget_id() == target.getId()).mapToDouble(g -> g.getSumm()).sum();
    }

    protected String dateToString(Target target) {
        return target.getSettingDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    protected LocalDate stringToDate(TargetDTO targetDTO) {
        return LocalDate.parse(targetDTO.getSettingDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
