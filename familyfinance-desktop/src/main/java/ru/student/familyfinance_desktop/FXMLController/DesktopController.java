package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("DesktopPage.fxml")
public class DesktopController implements Initializable{

    @FXML
    private TitledPane grossBook;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // grossbook.setContent(grossbook);
    }

}
