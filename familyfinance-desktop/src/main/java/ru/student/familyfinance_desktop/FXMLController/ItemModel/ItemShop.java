package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.ShopDTO;
import ru.student.familyfinance_desktop.Mapper.ShopMapper;
import ru.student.familyfinance_desktop.Service.ShopService;

@Component
@RequiredArgsConstructor
public class ItemShop {
    private final ShopMapper mapper;
    private final ShopService service;

    private ObservableList<ShopDTO> listShopDTO = FXCollections.observableArrayList();

    public ObservableList<ShopDTO> getListShopDTO() {
        return listShopDTO;
    }

    public void setListShopDTO() {
        listShopDTO.setAll(mapper.toListShopDTO(service.getShops()));
    }
}
