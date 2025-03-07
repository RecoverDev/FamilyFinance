package ru.student.familyfinance_desktop.FXMLController.Statistic;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Model.WorkPeriod;
import ru.student.familyfinance_desktop.Service.StatisticService;

@Controller
@FxmlView("ExpensesChartPage.fxml")
@RequiredArgsConstructor
public class ExpensesChartController implements Initializable {
    private final StatisticService service;

    @Autowired
    private WorkPeriod currentPeriod;

    @FXML
    private DatePicker beginDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private CheckBox separateCheckBox;

    @FXML
    private LineChart<String, Double> expensesChart;

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        beginDatePicker.setValue(currentPeriod.getCurrentPeriod().minusMonths(3));
        endDatePicker.setValue(currentPeriod.getCurrentPeriod().plusDays(currentPeriod.getCurrentPeriod().lengthOfMonth() - 1));
        fillChart();
    }

    @FXML
    private void actionFillButton(ActionEvent event) {
        fillChart();
    }

    private void fillChart() {
        Map<String, Map<String, Double>> kits = service.getExpensesStatistic(beginDatePicker.getValue(), endDatePicker.getValue(), separateCheckBox.isSelected());

        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();

        for (Entry<String, Map<String, Double>> kit : kits.entrySet()) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(kit.getKey());
            for (Entry<String, Double> k : kit.getValue().entrySet()) {
                series.getData().add(new XYChart.Data<String,Double>(k.getKey(), k.getValue()));
            }
            data.add(series);
        }

        expensesChart.getData().removeAll(expensesChart.getData());
        expensesChart.getData().addAll(data);
    }



}
