package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.Service.AutorizationService;

@Component
@Setter
@FxmlView("LoginPage.fxml")
public class AutorizateController implements Initializable{

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private DesktopController desktopController;

    @Autowired
    private AutorizationService service;

    @Autowired
    private Navigator navigator;

    @Autowired
    private AutorizateData loginData;


    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private CheckBox checkboxSave;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nameField.getText().isEmpty()) {
                nameField.setStyle("-fx-text-fill: red");
                nameField.setTooltip(new Tooltip("Имя пользователя не может быть пустым"));
                errorLabel.setText("Имя пользователя не может быть пустым");
            } else {
                nameField.setStyle("-fx-text-fill: -color-fg-default");
                nameField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passwordField.getText().isEmpty()) {
                passwordField.setStyle("-fx-text-fill: red");
                passwordField.setTooltip(new Tooltip("Пароль не может быть пустым"));
                errorLabel.setText("Пароль не может быть пустым");
            } else {
                passwordField.setStyle("-fx-text-fill: -color-fg-default");
                passwordField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });
    }

    @FXML
    public void loginAction() {
        if (nameField.getText().isEmpty()) {
            errorLabel.setText("Имя пользователя не определено");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("Поле с паролем пользователя не может быть пустым");
            return;
        }
        loginData.setUsername(nameField.getText());
        loginData.setPassword(passwordField.getText());
        boolean result = service.autorizate();

        if (!result) {
            errorLabel.setText("Ошибка авторизации пользователя");
            return;
        }

        errorLabel.setText("");
        //вызвать основное окно
        navigator.show(desktopController, "Семейный бюджет");
    }

    @FXML
    public void exitAction() {
        System.exit(0);
    }

    @FXML
    public void registrationAction() {
        navigator.showModal(registrationController, "Семейный бюджет.Регистрация");
    }

}
