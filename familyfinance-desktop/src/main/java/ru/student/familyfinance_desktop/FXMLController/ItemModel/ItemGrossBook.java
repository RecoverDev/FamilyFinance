package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.GrossBookDTO;
import ru.student.familyfinance_desktop.Mapper.GrossBookMapper;
import ru.student.familyfinance_desktop.Service.GrossBookService;

@Component
@RequiredArgsConstructor
public class ItemGrossBook {
    private final GrossBookMapper mapper;
    private final GrossBookService service;

    private ObservableList<GrossBookDTO> listGroosBookDTO = FXCollections.observableArrayList();;


    public ObservableList<GrossBookDTO> getListGrossBookDTO() {
        return listGroosBookDTO;
    }

    public void setListGrossBookDTO() {
        listGroosBookDTO.setAll(mapper.toListGrossBookDTO(service.getGrossBooks()));
    }

}
