package ru.student.familyfinance_desktop.FXMLController.Statistic;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.data.util.Pair;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.GraphicExpensesCell;
import ru.student.familyfinance_service.Model.WorkPeriod;
import ru.student.familyfinance_service.Service.StatisticService;

@Controller
@RequiredArgsConstructor
@FxmlView("GraphicExpensesPage.fxml")
public class GraphicExpensesController implements Initializable {
    private final StatisticService service;

    @Autowired
    private WorkPeriod currentPeriod;

    @FXML
    private GridPane container;

    @FXML
    private CheckBox separator;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        separator.selectedProperty().addListener((observe, oldValue, newValue) -> {
            showGraphics(newValue);
        });
    }

    private void showGraphics(boolean flag) {
        Map<String,Map<LocalDate, Pair<Double, Double>>> result = service.getExpensesShedule(currentPeriod.getCurrentPeriod(), flag);

        container.getChildren().removeAll(container.getChildren());
        container.setHgap(2);
        container.setVgap(result.size()/2 + 1);

        int i = 0;
        for (Entry<String,Map<LocalDate, Pair<Double, Double>>> graphic : result.entrySet()) {
            VBox vbox = new VBox();
            GridPane.setRowIndex(vbox, i%2 + i/2);
            GridPane.setColumnIndex(vbox, Boolean.valueOf(i%2 == 0).compareTo(false));
            vbox.getChildren().add(new Label(graphic.getKey()));
            for (Entry<LocalDate, Pair<Double, Double>> week : graphic.getValue().entrySet()) {
                GraphicExpensesCell weekGraphic = new GraphicExpensesCell(week.getKey(), week.getValue().getSecond());
                weekGraphic.setValue(week.getValue().getFirst());
                vbox.getChildren().add(weekGraphic);
            }
            ++i;
            container.getChildren().add(vbox);
        }
    }
}
