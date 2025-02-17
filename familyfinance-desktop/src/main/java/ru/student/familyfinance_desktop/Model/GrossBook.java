package ru.student.familyfinance_desktop.Model;

import java.time.LocalDate;

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
public class GrossBook {

    long id;

    LocalDate dateOfOperation = LocalDate.now();

    long person_id;

    long target_id;

    long expenses_id;

    long income_id;

    double summ;
}
