package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.BasketDTO;
import ru.student.familyfinance_desktop.Model.Basket;
import ru.student.familyfinance_desktop.Model.Person;

@Mapper(componentModel=SPRING)
public abstract class BasketMapper {

    @Autowired
    protected Person person;

    @Mapping(target="person_id", expression = "java(person.getId())")
    public abstract Basket toBasket(BasketDTO basketDTO);

    public abstract List<Basket> toListBasket(List<BasketDTO> basketsDTO);

}
