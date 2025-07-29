package ru.student.familyfinance_desktop.DTO;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductDTO {
    private SimpleLongProperty id;
    private SimpleLongProperty expenses_id;
    private SimpleStringProperty name;
    private SimpleStringProperty expensesName;

    public ProductDTO(long id, long expenses_id, String name, String expensesName) {
        this.id = new SimpleLongProperty(id);
        this.expenses_id = new SimpleLongProperty(expenses_id);
        this.name = new SimpleStringProperty(name);
        this.expensesName = new SimpleStringProperty(expensesName);
    }

    public long getId() { return this.id.get(); }
    public void setId(long value) { this.id.set(value); }
    public long getExpenses_id() { return this.expenses_id.get(); }
    public void setExpenses_id(long value) { this.expenses_id.set(value); }
    public String getName() { return this.name.get(); }
    public void setName(String value) { this.name.set(value); }
    public String getExpensesName() { return this.expensesName.get(); }
    public void setExpensesName(String value) { this.expensesName.set(value); }

    @Override
    public String toString() {
        return this.getName();
    }

}
