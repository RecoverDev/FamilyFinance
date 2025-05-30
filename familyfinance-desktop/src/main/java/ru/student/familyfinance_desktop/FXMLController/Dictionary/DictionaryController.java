package ru.student.familyfinance_desktop.FXMLController.Dictionary;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;

@Component
@FxmlView("DictionaryPage.fxml")
@RequiredArgsConstructor
public class DictionaryController implements Initializable {

    private final Navigator navigator;

    @Autowired
    private IncomeTabController incomeTabController;

    @Autowired
    private ExpensesTabController expensesTabController;

    @Autowired
    private ProductTabController productTabController;

    @FXML
    private Tab IncomeTab;

    @FXML
    private Tab ExpensesTab;

    @FXML
    private Tab ProductTab;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        Parent incomePage = navigator.loadFxml(incomeTabController);
        IncomeTab.setContent(incomePage);

        Parent expensesPage = navigator.loadFxml(expensesTabController);
        ExpensesTab.setContent(expensesPage);

        Parent productPage = navigator.loadFxml(productTabController);
        ProductTab.setContent(productPage);
    }
}