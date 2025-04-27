package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance.DTO.BasketDTO;
import ru.student.familyfinance.Model.Basket;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Service.PersonService;
import ru.student.familyfinance.Service.ProductService;
import ru.student.familyfinance.Service.ShopService;

@Mapper(componentModel = "spring")
public abstract class MapperBasket {

    @Autowired
    protected PersonService personService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ShopService shopService;

    @Mapping(target = "person_id", expression = "java(basket.getPerson().getId())")
    @Mapping(target = "product_id", expression = "java(basket.getProduct().getId())")
    @Mapping(target = "shop_id", expression = "java(basket.getShop().getId())")
    public abstract BasketDTO toBasketDTO(Basket basket);

    public abstract List<BasketDTO> toListBasketDTO(List<Basket> baskets);

    @Mapping(target = "person", expression = "java(getPerson(basketDTO.getPerson_id()))")
    @Mapping(target = "product", expression = "java(getProduct(basketDTO.getProduct_id()))")
    @Mapping(target = "shop", expression = "java(getShop(basketDTO.getShop_id()))")
    public abstract Basket toBasket(BasketDTO basketDTO);

    public abstract List<Basket> toListBasket(List<BasketDTO> basketsDTO);

    protected Person getPerson(long id) {
        return personService.getPersonById(id);
    }

    protected Product getProduct(long id) {
        return productService.getProductById(id);
    }

    protected Shop getShop(long id) {
        return shopService.getShopById(id);
    }
}
