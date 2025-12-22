package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.ExpensesDTO;
import ru.student.familyfinance_desktop.Mapper.ExpensesMapper;
import ru.student.familyfinance_service.Service.ExpensesService;

@Component
@RequiredArgsConstructor
public class ItemExpenses {
    private final ExpensesMapper mapper;
    private final ExpensesService service;

    private ObservableList<ExpensesDTO> listExpensesDTO = FXCollections.observableArrayList();

    public ObservableList<ExpensesDTO> getLiExpensesDTO() {
        return listExpensesDTO;
    }

    public void setListExpensesDTO() {
        listExpensesDTO.setAll(mapper.toListExpensesDTO(service.getExpenses()));
    }
}
