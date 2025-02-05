package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Model.Expenses;
import ru.student.familyfinance_desktop.Model.ExpensesType;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Service.ExpensesTypeService;

@Getter
@Setter
@Component
@FxmlView("ExpensesPage.fxml")
public class ExpensesController implements Initializable {
    private Expenses expenses;
    private boolean okFlag;


    @Autowired
    private ExpensesTypeService service;

    @Autowired
    private Person person;


    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<ExpensesType> typeExpenses;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameField.setText(expenses.getName());
        ObservableList<ExpensesType> data = FXCollections.observableArrayList(service.getExpensesTypes());
        typeExpenses.setItems(data);
        if (expenses.getExpensesType_id() > 0) {
            typeExpenses.setValue(service.getExpensesTypeById(expenses.getExpensesType_id()));
        } else {
            typeExpenses.setValue(service.getExpensesTypes().get(0));
        }
    }

    @FXML
    private void saveAction() {
        expenses.setName(nameField.getText());
        expenses.setExpensesType_id(typeExpenses.getValue().getId());
        expenses.setPerson_id(person.getId());
        okFlag = true;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
    }

    @FXML
    private void cancelAction() {
        okFlag = false;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
    }

}
