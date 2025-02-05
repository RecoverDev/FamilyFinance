package ru.student.familyfinance_desktop.Model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Expenses {

    long id;

    long person_id;

    String name;
    
    long expensesType_id;

}
