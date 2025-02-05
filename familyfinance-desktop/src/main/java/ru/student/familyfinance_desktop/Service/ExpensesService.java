package ru.student.familyfinance_desktop.Service;

import java.util.List;

import ru.student.familyfinance_desktop.Model.Expenses;

public interface ExpensesService {

    void setExpenses();

    List<Expenses> getExpenses();

    Expenses getExpensesById(long id);

    boolean addExpenses(Expenses expenses);

    boolean editExpenses(Expenses expenses);

    boolean deleteExpensesById(long id);

}
