package ru.student.familyfinance_desktop.DTO;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class IncomeDTO {
    private SimpleLongProperty id;
    private SimpleStringProperty name;

    public IncomeDTO(long id, String name) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public long getId() { return id.get(); }

    public void setId(long value) { id.set(value); }

    public String getName() { return name.get(); }

    public void setName(String value) { name.set(value); }
}
