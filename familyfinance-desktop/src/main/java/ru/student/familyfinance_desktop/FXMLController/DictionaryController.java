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
import ru.student.familyfinance_desktop.DTO.ExpensesDTO;
import ru.student.familyfinance_desktop.DTO.IncomeDTO;
import ru.student.familyfinance_desktop.Mapper.ExpensesMapper;
import ru.student.familyfinance_desktop.Mapper.IncomeMapper;
import ru.student.familyfinance_desktop.Model.Expenses;
import ru.student.familyfinance_desktop.Model.Income;
import ru.student.familyfinance_desktop.Service.ExpensesService;
import ru.student.familyfinance_desktop.Service.IncomeService;

@Component
@FxmlView("DictionaryPage.fxml")
@RequiredArgsConstructor
public class DictionaryController implements Initializable {
    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;
    private final IncomeController incomeController;

    private final ExpensesMapper expensesMapper;
    private final ExpensesService expensesService;
    private final ExpensesController expensesController;

    private final Navigator navigator;

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
        setItemsToIncomeTable();
    }

    @FXML
    private void deleteIncomeAction() {
        TableSelectionModel<IncomeDTO> selectionModel = incomeTable.getSelectionModel();
        Income income = incomeMapper.toIncome(selectionModel.getSelectedItem());
        if (income == null) {
            return;
        }

        System.setProperty("java.awt.headless", "false");

        int option = JOptionPane.showConfirmDialog(null, "Удалить вид доходов\"" + income.getName() + "\"", "Удаление вида доходов", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (incomeService.deleteIncomeById(income.getId())) {
                setItemsToIncomeTable();
            }
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
        setItemsToIncomeTable();
    }

    @FXML
    private void addExpensesAction() {
        Expenses expenses = new Expenses();
        expensesController.setExpenses(expenses);
        navigator.showModal(expensesController, "Семейный бюджет. Добавление нового вида расходов");
        if (expensesController.isOkFlag()) {
            expensesService.addExpenses(expenses);
            setItemsToExpensesTable();
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
            setItemsToExpensesTable();
        }
    }

    @FXML
    private void deleteExpensesAction() {
        TableSelectionModel<ExpensesDTO> selectionModel = expensesTable.getSelectionModel();
        Expenses expenses = expensesMapper.toExpenses(selectionModel.getSelectedItem());
        if (expenses == null) {
            return;
        }

        System.setProperty("java.awt.headless", "false");

        int option = JOptionPane.showConfirmDialog(null, "Удалить вид расходов\"" + expenses.getName() + "\"", "Удаление вида доходов", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (expensesService.deleteExpensesById(expenses.getId())) {
                setItemsToExpensesTable();
            }
        }
    }

    private void setItemsToIncomeTable() {
        ObservableList<IncomeDTO> data = FXCollections.observableArrayList(incomeMapper.toListIncomeDTO(incomeService.getIncomes()));

        nameIncome.setCellValueFactory(new PropertyValueFactory<>("name"));
        incomeTable.setItems(data);
    }

    private void setItemsToExpensesTable() {
        ObservableList<ExpensesDTO> data = FXCollections.observableArrayList(expensesMapper.toListExpensesDTO(expensesService.getExpenses()));

        nameExpenses.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeExpenses.setCellValueFactory(new PropertyValueFactory<>("expensesType_name"));

        expensesTable.setItems(data);
    }

}