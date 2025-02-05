package ru.student.familyfinance_desktop.DTO;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ExpensesDTO {
    private SimpleLongProperty id;
    private SimpleStringProperty name;
    private SimpleLongProperty expensesType_id;
    private SimpleStringProperty expensesType_name;

    public ExpensesDTO(long id, String name, long expensesType_id) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.expensesType_id = new SimpleLongProperty(expensesType_id);
        this.expensesType_name = new SimpleStringProperty();
    }

    public long getId() { return this.id.get(); }
    public void setId(long value) { this.id.set(value); }

    public String getName() { return this.name.get(); }
    public void setName(String value) { this.name.set(value); }

    public long getExpensesType_id() { return this.expensesType_id.get(); }
    public void setExpensesType_id(long value) { this.expensesType_id.set(value); }

    public String getExpensesType_name() { return this.expensesType_name.get(); }
    public void setExpensesType_name(String value) { this.expensesType_name.set(value); }

}
