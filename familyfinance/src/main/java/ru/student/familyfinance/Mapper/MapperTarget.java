package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.student.familyfinance.DTO.TargetDTO;
import ru.student.familyfinance.Model.Target;

@Mapper(componentModel = "spring")
public interface MapperTarget {

    @Mapping(target = "person_id", expression = "java(target.getPerson().getId())")
    TargetDTO toTargetDTO(Target target);

    List<TargetDTO> toListTargetDTO(List<Target> targets);

    @Mapping(target="person", expression = "java(null)")
    Target toTarget(TargetDTO targetDTO);

    List<Target> toListTargets(List<TargetDTO> targetsDTO);

}
