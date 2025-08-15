package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.BasketDTO;
import ru.student.familyfinance_desktop.Model.Basket;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Service.ProductService;
import ru.student.familyfinance_desktop.Service.ShopService;

@Mapper(componentModel=SPRING)
public abstract class BasketMapper {

    @Autowired
    protected Person person;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ShopService shopService;

    @Mapping(target="person_id", expression = "java(person.getId())")
    public abstract Basket toBasket(BasketDTO basketDTO);

    public abstract List<Basket> toListBasket(List<BasketDTO> basketsDTO);

    @Mapping(target = "productName", expression = "java(productService.getProductById(basket.getProduct_id()).getName())")
    @Mapping(target = "shopName", expression = "java(shopService.getShopById(basket.getShop_id()).getName())")
    @Mapping(target = "summ", expression = "java(\"0.0\")")
    @Mapping(target = "selectItem", expression = "java(true)")
    public abstract BasketDTO toBasketDTO(Basket basket);

    public abstract List<BasketDTO> toListBasketDTO(List<Basket> listBasket);
}
