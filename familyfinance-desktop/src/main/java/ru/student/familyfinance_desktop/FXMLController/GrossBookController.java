package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.DTO.ExpensesDTO;
import ru.student.familyfinance_desktop.DTO.IncomeDTO;
import ru.student.familyfinance_desktop.DTO.TargetDTO;
import ru.student.familyfinance_desktop.Mapper.ExpensesMapper;
import ru.student.familyfinance_desktop.Mapper.IncomeMapper;
import ru.student.familyfinance_desktop.Mapper.TargetMapper;

import ru.student.familyfinance_service.Model.GrossBook;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Model.WorkPeriod;
import ru.student.familyfinance_service.Service.ExpensesService;
import ru.student.familyfinance_service.Service.IncomeService;
import ru.student.familyfinance_service.Service.TargetService;

@Getter
@Setter
@Controller
@FxmlView("GrossBookPage.fxml")
@RequiredArgsConstructor
public class GrossBookController implements Initializable{
    private final IncomeService incomeService;
    private final ExpensesService expensesService;
    private final TargetService targetService;

    private final IncomeMapper incomeMapper;
    private final ExpensesMapper expensesMapper;
    private final TargetMapper targetMapper;

    private int action = this.INCOME;
    private GrossBook grossBook;
    private boolean okFlag;

    public final int INCOME = 1;
    public final int EXPENSES = 2;
    public final int TARGET = 3;

    @Autowired
    private Person person;

    @Autowired
    private WorkPeriod currentPeriod;

    @FXML
    private DatePicker operationDatePicker;

    @FXML
    private Label labelList;

    @FXML
    private ComboBox<IncomeDTO> listIncome;

    @FXML
    private ComboBox<ExpensesDTO> listExpenses;

    @FXML
    private ComboBox<TargetDTO> listTarget;

    @FXML
    private TextField summField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        switch (action) {
            case INCOME -> {
                listExpenses.setVisible(false);
                listTarget.setVisible(false);
                listIncome.setVisible(true);
                labelList.setText("Вид дохода: ");
                setItemsListIncome();
            }
            case EXPENSES -> {
                listExpenses.setVisible(true);
                listTarget.setVisible(false);
                listIncome.setVisible(false);
                labelList.setText("Вид расходов: ");
                setItemsListExpenses();
            }
            case TARGET -> {
                listExpenses.setVisible(false);
                listTarget.setVisible(true);
                listIncome.setVisible(false);
                labelList.setText("Цель: ");
                setItemsListTargets();
            }
        }
        summField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (summField.getText().isEmpty()) {
                summField.setStyle("-fx-text-fill: red");
                summField.setTooltip(new Tooltip("Сумма цели не может быть пустой"));
                errorLabel.setText("Сумма цели не может быть пустой");
            } else {
                summField.setStyle("-fx-text-fill: -fx-text-background-color");
                summField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
            if (testToDouble(summField.getText())) {
                summField.setStyle("-fx-text-fill: -fx-text-background-color");
                summField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            } else {
                summField.setStyle("-fx-text-fill: red");
                summField.setTooltip(new Tooltip("Сумма - это число"));
                errorLabel.setText("Сумма - это число");
            }
        });

        summField.setText(Double.toString(grossBook.getSumm()));
        operationDatePicker.setValue(grossBook.getDateOfOperation());
    }

    @FXML
    private void saveAction(ActionEvent event) {
        if (!testToDouble(summField.getText())) {
            errorLabel.setText("Сумма - это число");
            return;
        }

        switch (action) {
            case INCOME -> grossBook.setIncome_id(listIncome.getValue().getId());
            case EXPENSES -> grossBook.setExpenses_id(listExpenses.getValue().getId());
            case TARGET -> grossBook.setTarget_id(listTarget.getValue().getId());
        }
        grossBook.setDateOfOperation(currentPeriod.getCurrentPeriod());
        grossBook.setSumm(Double.parseDouble(summField.getText()));
        grossBook.setPerson_id(person.getId());
        grossBook.setDateOfOperation(operationDatePicker.getValue());

        okFlag = true;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        okFlag = false;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    private void setItemsListIncome() {
        ObservableList<IncomeDTO> list = FXCollections.observableArrayList(incomeMapper.toListIncomeDTO(incomeService.getIncomes()));
        listIncome.setItems(list);
        List<IncomeDTO> currentValue = list.stream().filter(i -> i.getId() == grossBook.getIncome_id()).toList();
        if (currentValue.isEmpty()) {
            listIncome.setValue(list.get(0));
        } else {
            listIncome.setValue(currentValue.getFirst());
        }
    }

    private void setItemsListExpenses() {
        ObservableList<ExpensesDTO> list = FXCollections.observableArrayList(expensesMapper.toListExpensesDTO(expensesService.getExpenses()));
        listExpenses.setItems(list);
        List<ExpensesDTO> currentValue = list.stream().filter(e -> e.getId() == grossBook.getExpenses_id()).toList();
        if (currentValue.isEmpty()) {
            listExpenses.setValue(list.get(0));
        } else {
            listExpenses.setValue(currentValue.getFirst());
        }
    }

    private void setItemsListTargets() {
        ObservableList<TargetDTO> list = FXCollections.observableArrayList(targetMapper.toListTargetDTO(targetService.getTargets()));
        listTarget.setItems(list);
        List<TargetDTO> currentValue = list.stream().filter(t -> t.getId() == grossBook.getTarget_id()).toList();
        if (currentValue.isEmpty()) {
            listTarget.setValue(list.get(0));
        } else {
            listTarget.setValue(currentValue.getFirst());
        }
    }

    private boolean testToDouble(String value) {
        boolean result = false;

        try{
            Double.parseDouble(value);
            result = true;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }
}
