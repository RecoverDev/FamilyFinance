package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.ProductDTO;
import ru.student.familyfinance_desktop.Mapper.ProductMapper;
import ru.student.familyfinance_service.Service.ProductService;

@Component
@RequiredArgsConstructor
public class ItemProduct {
    private final ProductMapper mapper;
    private final ProductService service;

    private ObservableList<ProductDTO> listProductDTO = FXCollections.observableArrayList();

    public ObservableList<ProductDTO> getLiProductDTO() {
        return listProductDTO;
    }

    public void setListProductDTO() {
        listProductDTO.setAll(mapper.toListProductDTO(service.getProducts()));
    }


}
