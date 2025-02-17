package ru.student.familyfinance_desktop.DTO;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class GrossBookDTO {
    private SimpleLongProperty id;
    private SimpleStringProperty description;
    private SimpleLongProperty income_id;
    private SimpleLongProperty expenses_id;
    private SimpleLongProperty target_id;
    private SimpleStringProperty dateOfOperation;
    private SimpleIntegerProperty type;
    private SimpleDoubleProperty summ;

    public GrossBookDTO() {
        this.id = new SimpleLongProperty();
        this.description = new SimpleStringProperty();
        this.income_id = new SimpleLongProperty();
        this.expenses_id = new SimpleLongProperty();
        this.target_id = new SimpleLongProperty();
        this.dateOfOperation = new SimpleStringProperty();
        this.type = new SimpleIntegerProperty();
        this.summ = new SimpleDoubleProperty();

    }

    public GrossBookDTO(long id, String description, long income_id, long expenses_id, long target_id, String dateOfOperation, int type, double summ) {
        this.id = new SimpleLongProperty(id);
        this.description = new SimpleStringProperty(description);
        this.income_id = new SimpleLongProperty(income_id);
        this.expenses_id = new SimpleLongProperty(expenses_id);
        this.target_id = new SimpleLongProperty(target_id);
        this.dateOfOperation = new SimpleStringProperty(dateOfOperation);
        this.type = new SimpleIntegerProperty(type);
        this.summ = new SimpleDoubleProperty(summ);
    }
    public long getId() { return id.get(); }

    public void setId(long value) { id.set(value); }

    public String getDescription() { return description.get(); }

    public void setDescription(String value) { description.set(value); }

    public long getIncome_id() { return this.income_id.get(); }

    public void setIncome_id(long value) { this.income_id.set(value); }
    
    public long getExpenses_id() { return this.expenses_id.get(); }

    public void setExpenses_id(long value) { this.expenses_id.set(value); }

    public long getTarget_id() { return this.target_id.get(); }

    public void setTarget_id(long value) { this.target_id.set(value); }

    public String getDateOfOperation() { return dateOfOperation.get(); }

    public void setDateOfOperation(String value) { dateOfOperation.set(value); }

    public int getType() { return this.type.get(); }

    public void setType(int value) { this.type.set(value); }

    public double getSumm() { return summ.get(); }

    public void setSumm(double value) { summ.set(value); }

}
