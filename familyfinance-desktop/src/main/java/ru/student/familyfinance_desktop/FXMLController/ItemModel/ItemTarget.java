package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.TargetDTO;
import ru.student.familyfinance_desktop.Mapper.TargetMapper;
import ru.student.familyfinance_desktop.Service.TargetService;

@Component
@RequiredArgsConstructor
public class ItemTarget {
    private final TargetMapper mapper;
    private final TargetService service;

    private ObservableList<TargetDTO> listTargetDTO = FXCollections.observableArrayList();

    public ObservableList<TargetDTO> getListTargetDTO() {
        return listTargetDTO;
    }

    public void setListTargetDTO() {
        this.listTargetDTO.setAll(mapper.toListTargetDTO(service.getTargets()));
    }


}
