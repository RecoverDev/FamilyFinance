package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Service.PersonService;

@Controller
@FxmlView("SettingPage.fxml")
@RequiredArgsConstructor
public class SettingController implements Initializable{
    private final PersonService service;

    @Autowired
    private Person person;

    @FXML
    private TextField nameField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameField.setText(person.getUsername());
        fullNameField.setText(person.getFullName());
        emailField.setText(person.getEmail());

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nameField.getText().isEmpty()) {
                nameField.setStyle("-fx-text-fill: red");
                nameField.setTooltip(new Tooltip("Имя пользователя не может быть пустым"));
                errorLabel.setText("Имя пользователя не может быть пустым");
            } else {
                nameField.setStyle("-fx-text-fill: -fx-text-background-color");
                nameField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });

        fullNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (fullNameField.getText().isEmpty()) {
                fullNameField.setStyle("-fx-text-fill: red");
                fullNameField.setTooltip(new Tooltip("Полное имя не может быть пустым"));
                errorLabel.setText("Полное имя не может быть пустым");
            } else {
                fullNameField.setStyle("-fx-text-fill: -fx-text-background-color");
                fullNameField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean corresponds = emailField.getText().matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$");
            if (!corresponds) {
                emailField.setStyle("-fx-text-fill: red");
                emailField.setTooltip(new Tooltip("Такого почтового адреса не существует"));
                errorLabel.setText("Такого почтового адреса не существует");
            } else {
                emailField.setStyle("-fx-text-fill: -fx-text-background-color");
                emailField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }

        });
    }

    @FXML
    private void saveAction() {
        if (nameField.getText().isEmpty()) {
            errorLabel.setText("Имя пользователя не может быть пустым");
            return;
        }

        if (fullNameField.getText().isEmpty()) {
            errorLabel.setText("Полное имя не может быть пустым");
            return;
        }

        if (!emailField.getText().matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")) {
            emailField.setTooltip(new Tooltip("Такого почтового адреса не существует"));
            errorLabel.setText("Такого почтового адреса не существует");
            return;
        }

        person.setUsername(nameField.getText());
        person.setFullName(fullNameField.getText());
        person.setEmail(emailField.getText());


        if (service.editPerson(person)) {
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Ошибка обновления параметров пользователя");
        }
    }

    @FXML
    private void cancelAction() {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
    }

}
