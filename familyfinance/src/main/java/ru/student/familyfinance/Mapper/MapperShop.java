package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance.DTO.ShopDTO;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Service.PersonService;

@Mapper(componentModel = "spring")
public abstract class MapperShop {

    @Autowired
    protected PersonService personService;

    @Mapping(target = "person_id", expression = "java(shop.getPerson().getId())")
    public abstract ShopDTO toShopDTO(Shop shop);

    public abstract List<ShopDTO> toListShopDTO(List<Shop> shops);

    @Mapping(target = "person", expression = "java(getPerson(shopDTO.getPerson_id()))")
    public abstract Shop toShop(ShopDTO shopDTO);

    public abstract List<Shop> toListShop(List<ShopDTO> listShopDTO);

    protected Person getPerson(long id) {
        return personService.getPersonById(id);
    }

}
