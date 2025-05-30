package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.ProductDTO;
import ru.student.familyfinance_desktop.Mapper.ProductMapper;
import ru.student.familyfinance_desktop.Service.ProductService;

@Component
@RequiredArgsConstructor
public class ItemProduct {
    private final ProductMapper mapper;
    private final ProductService service;

    private ObservableList<ProductDTO> listProductDTO = FXCollections.observableArrayList();

    public ObservableList<ProductDTO> getLiExpensesDTO() {
        return listProductDTO;
    }

    public void setListExpensesDTO() {
        listProductDTO.setAll(mapper.toListProductDTO(service.getProducts()));
    }


}
