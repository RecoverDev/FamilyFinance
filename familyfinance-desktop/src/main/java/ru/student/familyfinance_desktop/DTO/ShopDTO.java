package ru.student.familyfinance_desktop.DTO;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShopDTO {
    private SimpleLongProperty id;
    private SimpleStringProperty name;

    public ShopDTO(long id, String name) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public long getId() { return this.id.get(); }
    public void setId(long value) { this.id.set(value); }

    public String getName() { return this.name.get(); }
    public void setName(String value) { this.name.set(value); }

    @Override
    public String toString() {
        return this.getName();
    }

}
