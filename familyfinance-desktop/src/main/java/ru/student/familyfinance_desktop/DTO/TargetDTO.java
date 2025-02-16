package ru.student.familyfinance_desktop.DTO;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TargetDTO {
    private SimpleLongProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty summ;
    private SimpleDoubleProperty accumulateSumm;
    private SimpleStringProperty settingDate;

    public TargetDTO() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.summ = new SimpleDoubleProperty();
        this.accumulateSumm = new SimpleDoubleProperty();
        this.settingDate = new SimpleStringProperty();
    }

    public TargetDTO(long id, String name, Double summ, Double accumulateSumm, String settingDate) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.summ = new SimpleDoubleProperty(summ);
        this.accumulateSumm = new SimpleDoubleProperty(accumulateSumm);
        this.settingDate = new SimpleStringProperty(settingDate);
    }

    public long getId() { return id.get(); }

    public void setId(long value) { id.set(value); }

    public String getName() { return name.get(); }

    public void setName(String value) { name.set(value); }

    public double getSumm() { return summ.get(); }

    public void setSumm(double value) { summ.set(value); }

    public double getAccumulateSumm() { return accumulateSumm.get(); }

    public void setAccumulateSumm(double value) { accumulateSumm.set(value); }

    public String getSettingDate() { return settingDate.get(); }

    public void setSettingDate(String value) { settingDate.set(value); }

    @Override
    public String toString() {
        return this.getName();
    }
}
