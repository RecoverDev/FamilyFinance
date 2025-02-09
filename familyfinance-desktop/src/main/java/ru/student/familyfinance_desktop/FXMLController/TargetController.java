package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Model.Target;

@Controller
@Getter
@Setter
@FxmlView("TargetPage.fxml")
public class TargetController implements Initializable {
    private Target target;
    private boolean okFlag;

    @Autowired
    private Person person;

    @FXML
    private TextField nameField;

    @FXML
    private TextField summField;

    @FXML
    private DatePicker settingDatePicker;

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
                nameField.setTooltip(new Tooltip("Наименование цели не может быть пустым"));
                errorLabel.setText("Наименование цели не может быть пустым");
            } else {
                nameField.setStyle("-fx-text-fill: -fx-text-background-color");
                nameField.setTooltip(new Tooltip(""));
                errorLabel.setText("");
            }
        });
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

        nameField.setText(target.getName());
        summField.setText(Double.toString(target.getSumm()));
        settingDatePicker.setValue(target.getSettingDate());
    }

    @FXML
    private void saveAction(ActionEvent event) {
        if (nameField.getText().length() == 0) {
            errorLabel.setText("Наименование цели не может быть пустым");
            return;
        }
        if (!testToDouble(summField.getText())) {
            errorLabel.setText("Сумма - это число");
            return;
        }

        okFlag = true;
        target.setName(nameField.getText());
        target.setSumm(Double.parseDouble(summField.getText()));
        target.setSettingDate(settingDatePicker.getValue());
        target.setPerson_id(person.getId());
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        okFlag = false;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
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
