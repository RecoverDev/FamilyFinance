package ru.student.familyfinance_desktop.FXMLController.Statistic;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_service.Model.WorkPeriod;
import ru.student.familyfinance_service.Service.StatisticService;

@Controller
@FxmlView("ExecutionPlanPage.fxml")
@RequiredArgsConstructor
public class ExecutionPlanController implements Initializable {
    private final StatisticService service;

    @Autowired
    private WorkPeriod currentPeriod;

    @FXML
    private ComboBox<WorkPeriod> comboPeriod;

    @FXML
    private BarChart<String, Double> executionPlanChart;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setItemsComboPeriod();
        fillChart();
    }

    @FXML
    private void actionFillButton(ActionEvent event) {
        fillChart();
    }

    private void fillChart() {
        Map<String, Map<String, Double>> kits = service.getBudget(comboPeriod.getValue().getBeginPeriod());

        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();

        for (Entry<String, Map<String, Double>> kit : kits.entrySet()) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(kit.getKey());
            for (Entry<String, Double> k : kit.getValue().entrySet()) {
                series.getData().add(new XYChart.Data<String,Double>(k.getKey(), k.getValue()));
            }
            data.add(series);
        }

        executionPlanChart.getData().removeAll(executionPlanChart.getData());
        executionPlanChart.getData().addAll(data);
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
