package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.DTO.IncomeDTO;
import ru.student.familyfinance_desktop.Mapper.IncomeMapper;
import ru.student.familyfinance_desktop.Model.Income;
import ru.student.familyfinance_desktop.Service.IncomeService;

@Component
@FxmlView("DictionaryPage.fxml")
@RequiredArgsConstructor
public class DictionaryController implements Initializable {
    private final IncomeService service;
    private final IncomeMapper mapper;
    private final IncomeController incomeController;
    private final Navigator navigator;


    @FXML
    private TableView<IncomeDTO> incomeTable;


    @FXML
    private TableColumn<IncomeDTO, String> nameIncome;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setItemsToIncomeTable();
    }

    @FXML
    private void addIncomeAction() {
        Income income = new Income();
        incomeController.setIncome(income);
        navigator.showModal(incomeController, "Семейный бюджет. Новый вид доходов");
        if (incomeController.isOkFlag()) {
            service.addIncome(income);
        }
        setItemsToIncomeTable();
    }

    @FXML
    private void deleteIncomeAction() {
        TableSelectionModel<IncomeDTO> selectionModel = incomeTable.getSelectionModel();
        Income income = mapper.toIncome(selectionModel.getSelectedItem());
        if (income == null) {
            return;
        }

        System.setProperty("java.awt.headless", "false");

        int option = JOptionPane.showConfirmDialog(null, "Удалить вид доходов\"" + income.getName() + "\"", "Удаление вида доходов", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (service.deleteIncomeById(income.getId())) {
                setItemsToIncomeTable();
            }
        }

    }

    @FXML
    private void editIncomeAction() {
        TableSelectionModel<IncomeDTO> selectionModel = incomeTable.getSelectionModel();
        Income income = mapper.toIncome(selectionModel.getSelectedItem());
        if (income == null) {
            return;
        }

        incomeController.setIncome(income);
        navigator.showModal(incomeController, "Семейный бюджет. Редактирование " + income.getName());
        if (incomeController.isOkFlag()) {
            service.editIncome(income);
        }
        setItemsToIncomeTable();

    }

    private void setItemsToIncomeTable() {
        ObservableList<IncomeDTO> data = FXCollections.observableArrayList(mapper.toListIncomeDTO(service.getIncomes()));

        nameIncome.setCellValueFactory(new PropertyValueFactory<>("name"));
        incomeTable.setItems(data);
    }


}
