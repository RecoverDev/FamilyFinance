package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.IncomeDTO;
import ru.student.familyfinance_desktop.Mapper.IncomeMapper;
import ru.student.familyfinance_service.Service.IncomeService;

@Component
@RequiredArgsConstructor
public class ItemIncome {
    private final IncomeMapper mapper;
    private final IncomeService service;

    private ObservableList<IncomeDTO> listIncomeDTO = FXCollections.observableArrayList();

    public ObservableList<IncomeDTO> getListIncome() {
        return listIncomeDTO;
    }

    public void setListIncome() {
        listIncomeDTO.setAll(mapper.toListIncomeDTO(service.getIncomes()));
    }

}
