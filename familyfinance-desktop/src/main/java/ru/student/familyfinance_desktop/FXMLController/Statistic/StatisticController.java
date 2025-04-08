package ru.student.familyfinance_desktop.FXMLController.Statistic;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;

@Controller
@FxmlView("StatisticPage.fxml")
public class StatisticController implements Initializable {

    @Autowired
    private Navigator navigator;

    @Autowired
    private IncomeChartController incomeChartController;

    @Autowired
    private ExpensesChartController expensesChartController;

    @Autowired
    private ExecutionPlanController executionPlanController;

    @Autowired
    private GraphicExpensesController graphicExpensesController;

    @FXML
    private ListView<String> listChart;

    @FXML
    private VBox container;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        MultipleSelectionModel<String> selectionModel = listChart.getSelectionModel();

        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            switch (newValue) {
                case "Статистика доходов" -> {
                    Parent stIncome = navigator.loadFxml(incomeChartController);
                    container.getChildren().removeAll(container.getChildren());
                    container.getChildren().add(stIncome);
                }
                case "Статистика расходов" -> {
                    Parent stExpenses = navigator.loadFxml(expensesChartController);
                    container.getChildren().removeAll(container.getChildren());
                    container.getChildren().add(stExpenses);
                }
                case "Исполнение бюджета" -> {
                    Parent stExecutionPlan = navigator.loadFxml(executionPlanController);
                    container.getChildren().removeAll(container.getChildren());
                    container.getChildren().add(stExecutionPlan);
                }
                case "График расходов" -> {
                    Parent stGraphicExpenses = navigator.loadFxml(graphicExpensesController);
                    container.getChildren().removeAll(container.getChildren());
                    container.getChildren().add(stGraphicExpenses);
                }
            }
        });
    }
}
