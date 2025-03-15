package ru.student.familyfinance_desktop.FXMLController;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class TargetPercentCell extends HBox {
    private final Label label = new Label();
    private final ProgressBar progressBar = new ProgressBar();

    private final DoubleProperty doubleProperty = new SimpleDoubleProperty();

    public TargetPercentCell() {
        getChildren().add(label);
        getChildren().add(progressBar);

        doubleProperty.addListener( (observable, oldValue, newValue) -> {
            label.setText(String.format("%.2f %%", newValue));
            progressBar.progressProperty().bind(observable);
        });
    }

    public void setPercent(Double percent) {
        doubleProperty.set(percent);
    }


}
