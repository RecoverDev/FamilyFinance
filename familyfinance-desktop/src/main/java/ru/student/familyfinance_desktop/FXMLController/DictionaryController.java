package ru.student.familyfinance_desktop.FXMLController;

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
import ru.student.familyfinance_desktop.DTO.ExpensesDTO;
import ru.student.familyfinance_desktop.DTO.IncomeDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemExpenses;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemIncome;
import ru.student.familyfinance_desktop.Mapper.ExpensesMapper;
import ru.student.familyfinance_desktop.Mapper.IncomeMapper;
import ru.student.familyfinance_desktop.Model.Expenses;
import ru.student.familyfinance_desktop.Model.Income;
import ru.student.familyfinance_desktop.Service.ExpensesService;
import ru.student.familyfinance_desktop.Service.ExpensesTypeService;
import ru.student.familyfinance_desktop.Service.IncomeService;

@Component
@FxmlView("DictionaryPage.fxml")
@RequiredArgsConstructor
public class DictionaryController implements Initializable {
    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;
    private final IncomeController incomeController;

    private final ExpensesTypeService expensesTypeService;
    private final ExpensesMapper expensesMapper;
    private final ExpensesService expensesService;
    private final ExpensesController expensesController;

    private final Navigator navigator;

    @Autowired
    private ItemIncome itemIncome;

    @Autowired
    private ItemExpenses itemExpenses;

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

    @FXML
    private TableView<ExpensesDTO> expensesTable;

    @FXML
    private TableColumn<ExpensesDTO, String> nameExpenses;

    @FXML
    private TableColumn<ExpensesDTO, String> typeExpenses;

    @FXML
    private Button addExpensesButton;

    @FXML
    private Button deleteExpensesButton;

    @FXML
    private Button editExpensesButton;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        incomeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        expensesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        incomeService.setRepositoryListener((event) -> itemIncome.setListIncome());
        incomeTable.itemsProperty().bind(new SimpleObjectProperty<ObservableList<IncomeDTO>>(itemIncome.getListIncome()));
        incomeService.setIncomes();

        expensesTypeService.setExpensesTypes();
        expensesService.setRepositoryListener((event) -> itemExpenses.setListExpensesDTO());
        expensesTable.itemsProperty().bind(new SimpleObjectProperty<ObservableList<ExpensesDTO>>(itemExpenses.getLiExpensesDTO()));
        expensesService.setExpenses();

        setItemsToIncomeTable();
        setItemsToExpensesTable();
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

    @FXML
    private void addExpensesAction() {
        Expenses expenses = new Expenses();
        expensesController.setExpenses(expenses);
        navigator.showModal(expensesController, "Семейный бюджет. Добавление нового вида расходов");
        if (expensesController.isOkFlag()) {
            expensesService.addExpenses(expenses);
        }
    }

    @FXML
    private void editExpensesAction() {
        TableSelectionModel<ExpensesDTO> selectionModel = expensesTable.getSelectionModel();
        Expenses expenses = expensesMapper.toExpenses(selectionModel.getSelectedItem());
        if (expenses == null) {
            return;
        }

        expensesController.setExpenses(expenses);
        navigator.showModal(expensesController, "Семейный бюджет. Редактирование " + expenses.getName());
        if (expensesController.isOkFlag()) {
            expensesService.editExpenses(expenses);
        }
    }

    @FXML
    private void deleteExpensesAction() {
        TableSelectionModel<ExpensesDTO> selectionModel = expensesTable.getSelectionModel();
        Expenses expenses = expensesMapper.toExpenses(selectionModel.getSelectedItem());
        if (expenses == null) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Удаление вида доходов");
        alert.setContentText("Удалить вид расходов\"" + expenses.getName() + "\"");
        alert.setTitle("Внимание");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType response = result.get();

        if (response == ButtonType.OK){
            expensesService.deleteExpensesById(expenses.getId());
        }
    }

    private void setItemsToIncomeTable() {
        nameIncome.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void setItemsToExpensesTable() {
        nameExpenses.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeExpenses.setCellValueFactory(new PropertyValueFactory<>("expensesType_name"));
    }
}