package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.BasketDTO;
import ru.student.familyfinance_desktop.Mapper.BasketMapper;
import ru.student.familyfinance_desktop.Service.BasketService;

@Component
@RequiredArgsConstructor
public class ItemBasket {
    private final BasketMapper mapper;
    private final BasketService service;

    private ObservableList<BasketDTO> listBasketDTO = FXCollections.observableArrayList();

    public ObservableList<BasketDTO> getListBasketDTO() {
        return listBasketDTO;
    }

    public void setListBasketDTO() {
        listBasketDTO.setAll(mapper.toListBasketDTO(service.getBaskets()));
    }
}
