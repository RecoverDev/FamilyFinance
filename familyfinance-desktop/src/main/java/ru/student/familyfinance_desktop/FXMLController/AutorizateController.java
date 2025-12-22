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
import javafx.stage.Stage;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_service.Model.AutorizateData;
import ru.student.familyfinance_desktop.SecurityManager.Counter;
import ru.student.familyfinance_service.Service.AutorizationService; 

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

    @Autowired
    private Counter counter;


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

        if (counter.remaining() == 0) {
            this.exitAction();
        }

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
        if (counter.remaining() == 0) {
            this.exitAction();
        }

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

        // navigator.setResult(true);
        boolean result = service.autorizate();

        if (!result) {
            counter.inc();
            errorLabel.setText("Осталось попыток " + counter.remaining());
            return;
        } else {
            //если есть на то указание - сохраним данные
            if (checkboxSave.isSelected()) {
                loginData.saveParams();
            }
            //вызвать основное окно
            navigator.show(desktopController, "Семейный бюджет");
        }

    }

    @FXML
    public void exitAction() {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void registrationAction() {
        navigator.showModal(registrationController, "Семейный бюджет.Регистрация");
    }

}
