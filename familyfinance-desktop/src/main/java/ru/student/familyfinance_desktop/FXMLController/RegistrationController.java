package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Model.RegistrationPerson;
import ru.student.familyfinance_desktop.Service.RegistrationService;

@Component
@RequiredArgsConstructor
@FxmlView("RegistrationPage.fxml")
public class RegistrationController implements Initializable{
    private final RegistrationService service;

    @FXML
    private TextField nameField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

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

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passwordField.getText().isEmpty()) {
                passwordField.setStyle("-fx-text-fill: red");
                passwordField.setTooltip(new Tooltip("Пароль не может быть пустым"));
                errorLabel.setText("Пароль не может быть пустым");
            } else {
                passwordField.setStyle("-fx-text-fill: -fx-text-background-color");
                passwordField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });

        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(passwordField.getText())) {
                confirmPasswordField.setStyle("-fx-text-fill: red");
                confirmPasswordField.setTooltip(new Tooltip("Пароли не совпадают"));
                errorLabel.setText("Пароли не совпадают");
             }  else {
                confirmPasswordField.setStyle("-fx-text-fill: -fx-text-background-color");
                confirmPasswordField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });
    }



    @FXML
    public void saveAction() {

        if (nameField.getText().isEmpty()) {
            errorLabel.setText("Имя пользователя не может быть пустым");
            return;
        }

        if (!emailField.getText().matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")) {
            emailField.setTooltip(new Tooltip("Такого почтового адреса не существует"));
            return;
        }

        if (passwordField.getText().isEmpty()) {
            passwordField.setTooltip(new Tooltip("Пароль не может быть пустым"));
            return;
        }

        if (!confirmPasswordField.getText().equals(passwordField.getText())) {
            errorLabel.setText("Пароли не совпадают");
            return;
         } 

         RegistrationPerson person = new RegistrationPerson(nameField.getText(), 
                                                            fullNameField.getText(), 
                                                            emailField.getText(), 
                                                            passwordField.getText());

        boolean result = service.registration(person);

        if (result) {
            Stage stage = (Stage)okButton.getScene().getWindow();
            stage.close();;
        } else {
            errorLabel.setText("Ошибка регистрации пользователя");
        }
    }

    @FXML
    public void cancelAction() {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
    }



}
