package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.FXMLController.Statistic.StatisticController;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Model.WorkPeriod;
import ru.student.familyfinance_desktop.Service.GrossBookService;
import ru.student.familyfinance_desktop.Service.PlanService;

@Component
@FxmlView("DesktopPage.fxml")
@RequiredArgsConstructor
public class DesktopController implements Initializable {
    private final Navigator navigator;
    private final GrossBookService grossBookService;
    private final PlanService planService;

    @Autowired
    private WorkPeriod currentPeriod;

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private TargetTableController targetTableController;

    @Autowired
    private PlanTableController planTableController;

    @Autowired
    private GrossBookTableController grossBookTableController;

    @Autowired
    private SettingController settingController;

    @Autowired
    private StatisticController statisticController;

    @Autowired
    private Person person;

    @FXML
    private ComboBox<WorkPeriod> comboPeriod;

    @FXML
    private Label userName;

    @FXML
    private TitledPane grossBook;

    @FXML
    private TitledPane plans;

    @FXML
    private TitledPane targets;

    @FXML
    private TitledPane dictionary;

    @FXML
    private TitledPane statistic;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userName.setText(person.getFullName());

        comboPeriod.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentPeriod.setCurrentPeriod(newValue.getCurrentPeriod());
            grossBookService.setGrossBooks(currentPeriod.getBeginPeriod(), currentPeriod.getEndPeriod());
            planService.setPlans(currentPeriod.getCurrentPeriod());
        });

        Parent dictionaryPage = navigator.loadFxml(dictionaryController);
        dictionary.setContent(dictionaryPage);

        Parent targetTablePage = navigator.loadFxml(targetTableController);
        targets.setContent(targetTablePage);

        Parent planPage = navigator.loadFxml(planTableController);
        plans.setContent(planPage);

        Parent grossBookPage = navigator.loadFxml(grossBookTableController);
        grossBook.setContent(grossBookPage);

        Parent statisticPage = navigator.loadFxml(statisticController);
        statistic.setContent(statisticPage); 

        setItemsComboPeriod();
    }

    @FXML
    private void settingAction(ActionEvent event) {
        navigator.showModal(settingController, "Свойства пользователя");
        userName.setText(person.getFullName());
    }

    private void setItemsComboPeriod() {
        ObservableList<WorkPeriod> listPeriod = FXCollections.observableArrayList();

        for (LocalDate i = currentPeriod.getCurrentPeriod().minusMonths(12); 
                       i.isBefore(currentPeriod.getCurrentPeriod().plusMonths(12)); 
                       i = i.plusMonths(1)) {
            listPeriod.add(new WorkPeriod(i));
        }

        comboPeriod.setItems(listPeriod);
        comboPeriod.setValue(currentPeriod);
    }
}
