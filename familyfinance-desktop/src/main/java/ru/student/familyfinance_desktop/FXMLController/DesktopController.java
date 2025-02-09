package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;

@Component
@FxmlView("DesktopPage.fxml")
@RequiredArgsConstructor
public class DesktopController implements Initializable {
    private final Navigator navigator;

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private TargetTableController targetTableController;

    @FXML
    private TitledPane grossBook;

    @FXML
    private TitledPane plans;

    @FXML
    private TitledPane targets;

    @FXML
    private TitledPane dictionary;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Parent dictionaryPage = navigator.loadFxml(dictionaryController);
        dictionary.setContent(dictionaryPage);

        Parent targetTablePage = navigator.loadFxml(targetTableController);
        targets.setContent(targetTablePage);

    }

}
