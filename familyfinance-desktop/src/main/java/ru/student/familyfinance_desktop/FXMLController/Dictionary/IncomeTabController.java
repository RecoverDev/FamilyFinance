package ru.student.familyfinance_desktop.FXMLController.Dictionary;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.DTO.IncomeDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemIncome;
import ru.student.familyfinance_desktop.Mapper.IncomeMapper;
import ru.student.familyfinance_desktop.Model.Income;
import ru.student.familyfinance_desktop.Service.IncomeService;

@Component
@FxmlView("IncomeTabPage.fxml")
@RequiredArgsConstructor
public class IncomeTabController implements Initializable {
    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;
    private final IncomeController incomeController;

    private final Navigator navigator;

    @Autowired
    private ItemIncome itemIncome;

    @FXML
    private TableView<IncomeDTO> incomeTable;

    @FXML
    private TableColumn<IncomeDTO, String> nameIncome;

    @FXML
    private Button addIncomeButton;

    @FXML
    private Button deleteIncomeButton;

    @FXML
    private Button editIncomeButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        incomeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        incomeService.setRepositoryListener((event) -> itemIncome.setListIncome());
        incomeTable.itemsProperty().bind(new SimpleObjectProperty<ObservableList<IncomeDTO>>(itemIncome.getListIncome()));
        incomeService.setIncomes();
    
        setItemsToIncomeTable();
    }
    @FXML
    private void addIncomeAction() {
        Income income = new Income();
        incomeController.setIncome(income);
        navigator.showModal(incomeController, "Семейный бюджет. Новый вид доходов");
        if (incomeController.isOkFlag()) {
            incomeService.addIncome(income);
        }
    }

    @FXML
    private void deleteIncomeAction() {
        TableSelectionModel<IncomeDTO> selectionModel = incomeTable.getSelectionModel();
        Income income = incomeMapper.toIncome(selectionModel.getSelectedItem());
        if (income == null) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Удаление вида доходов");
        alert.setContentText("Удалить вид доходов\"" + income.getName() + "\"");
        alert.setTitle("Внимание");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType response = result.get();

        if (response == ButtonType.OK){
            incomeService.deleteIncomeById(income.getId());
        }
    }

    @FXML
    private void editIncomeAction() {
        TableSelectionModel<IncomeDTO> selectionModel = incomeTable.getSelectionModel();
        Income income = incomeMapper.toIncome(selectionModel.getSelectedItem());
        if (income == null) {
            return;
        }

        incomeController.setIncome(income);
        navigator.showModal(incomeController, "Семейный бюджет. Редактирование " + income.getName());
        if (incomeController.isOkFlag()) {
            incomeService.editIncome(income);
        }
    }

    private void setItemsToIncomeTable() {
        nameIncome.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
