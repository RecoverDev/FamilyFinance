package ru.student.familyfinance_desktop.FXMLController.Dictionary;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.Model.Shop;

@Setter
@Getter
@Component
@FxmlView("ShopPage.fxml")
public class ShopController implements Initializable {
    private Shop shop;
    private boolean okFlag;

    @Autowired
    private Person person;

    @FXML
    private TextField nameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void saveAction() {
        if (nameField.getText().isEmpty()) {
            errorLabel.setText("Наименование магазина не может быть пустым");
            return;
        }

        okFlag = true;
        shop.setName(nameField.getText());
        shop.setPerson_id(person.getId());
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelAction() {
        okFlag = false;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nameField.getText().isEmpty()) {
                nameField.setStyle("-fx-text-fill: red");
                nameField.setTooltip(new Tooltip("Наименование магазина не может быть пустым"));
                errorLabel.setText("Наименование магазина не может быть пустым");
            } else {
                nameField.setStyle("-fx-text-fill: -fx-text-background-color");
                nameField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });
        nameField.setText(shop.getName());
    }
}
