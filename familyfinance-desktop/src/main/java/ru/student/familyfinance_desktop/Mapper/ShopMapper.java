package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.ShopDTO;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Model.Shop;

@Mapper(componentModel = SPRING)
public abstract class ShopMapper {

    @Autowired
    protected Person person;

    public abstract ShopDTO toShopDTO(Shop shop);

    public abstract List<ShopDTO> toListShopDTO(List<Shop> shops);

    @Mapping(target = "person_id", expression = "java(person.getId())")
    public abstract Shop toShop(ShopDTO shopDTO);

    public abstract List<Shop> toListShop(List<ShopDTO> shopsDTO);
}
