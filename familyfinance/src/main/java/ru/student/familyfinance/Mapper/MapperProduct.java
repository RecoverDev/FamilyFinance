package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance.DTO.ProductDTO;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Service.ExpensesService;
import ru.student.familyfinance.Service.PersonService;

@Mapper(componentModel = "spring")
public abstract class MapperProduct {

    @Autowired
    protected PersonService personService;

    @Autowired
    protected ExpensesService expensesService;

    @Mapping(target = "person_id", expression = "java(product.getPerson().getId())")
    @Mapping(target = "expenses_id", expression = "java(product.getExpenses().getId())")
    public abstract ProductDTO toProductDTO(Product product);

    public abstract List<ProductDTO> toListProductDTO(List<Product> products);

    @Mapping(target = "person", expression = "java(getPerson(productDTO.getPerson_id()))")
    @Mapping(target = "expenses", expression = "java(getExpenses(productDTO.getExpenses_id()))")
    public abstract Product toProduct(ProductDTO productDTO);

    public abstract List<Product> toListProduct(List<ProductDTO> productsDTO);

    protected Person getPerson(long id) {
        return personService.getPersonById(id);
    }

    protected Expenses getExpenses(long id) {
        return expensesService.getExpensesById(id);
    }
}
