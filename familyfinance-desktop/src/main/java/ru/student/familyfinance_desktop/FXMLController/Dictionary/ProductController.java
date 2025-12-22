package ru.student.familyfinance_desktop.FXMLController.Dictionary;

import java.net.URL;
import java.util.List;
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
import ru.student.familyfinance_desktop.DTO.ExpensesDTO;
import ru.student.familyfinance_desktop.Mapper.ExpensesMapper;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Model.Product;
import ru.student.familyfinance_service.Service.ExpensesService;

@Getter
@Setter
@Component
@FxmlView("ProductPage.fxml")
public class ProductController implements Initializable {
    private Product product;
    private boolean okFlag;

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private ExpensesMapper expensesMapper;

    @Autowired
    private Person person;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<ExpensesDTO> typeExpenses;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameField.setText(product.getName());
        List<ExpensesDTO> list = expensesMapper.toListExpensesDTO(expensesService.getExpenses());
        ObservableList<ExpensesDTO> data = FXCollections.observableArrayList(list);
        typeExpenses.setItems(data);

        ExpensesDTO currentValue = expensesMapper.toExpensesDTO(expensesService.getExpensesById(0));

        if (product.getExpenses_id() > 0) {
            currentValue = expensesMapper.toExpensesDTO(expensesService.getExpensesById(product.getExpenses_id()));
        }

        typeExpenses.setValue(currentValue);
    }

    @FXML
    private void saveAction() {
        product.setName(nameField.getText());
        product.setExpenses_id(typeExpenses.getValue().getId());
        product.setPerson_id(person.getId());
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
