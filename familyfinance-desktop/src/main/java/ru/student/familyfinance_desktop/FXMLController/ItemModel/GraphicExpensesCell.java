package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class GraphicExpensesCell extends HBox {
    private Label weeklyDay = new Label();
    private ProgressBar progressBar = new ProgressBar();
    private Label summ = new Label();
    private double maxValue;

    private final DoubleProperty doubleProperty = new SimpleDoubleProperty();

    public GraphicExpensesCell(LocalDate date, double max) {
        this.paddingProperty().set(new Insets(5));
        this.setSpacing(5.0);
        getChildren().add(weeklyDay);
        getChildren().add(progressBar);
        getChildren().add(summ);

        maxValue = max;
        weeklyDay.setText(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        summ.setText(String.format("%.2f р. %.2f р.", 0.0, maxValue));

        doubleProperty.addListener((observable, oldValue, newValue) -> {
            summ.setText(String.format("%.2f р./ %.2f р.", newValue, maxValue));
            progressBar.setProgress((double)newValue/maxValue);
        });
    }

    public void setValue(double value) {
        doubleProperty.set(value);
    }

}
