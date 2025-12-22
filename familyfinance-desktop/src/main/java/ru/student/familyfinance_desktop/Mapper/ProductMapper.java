package ru.student.familyfinance_desktop.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance_desktop.DTO.ProductDTO;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Model.Product;
import ru.student.familyfinance_service.Service.ExpensesService;

@Mapper(componentModel = SPRING)
public abstract class ProductMapper {

    @Autowired
    protected Person person;

    @Autowired
    protected ExpensesService expensesService;

    @Mapping(target = "expensesName", expression = "java(expensesService.getExpensesById(product.getExpenses_id()).getName())")
    abstract public ProductDTO toProductDTO(Product product);

    abstract public List<ProductDTO> toListProductDTO(List<Product> products);

    @Mapping(target = "person_id", expression = "java(person.getId())")
    abstract public Product toProduct(ProductDTO productDTO);

    abstract public List<Product> toListProduct(List<ProductDTO> productsDTO);

}
