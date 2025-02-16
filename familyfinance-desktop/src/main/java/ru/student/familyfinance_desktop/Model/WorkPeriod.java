package ru.student.familyfinance_desktop.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPeriod {
    private LocalDate currentPeriod;

    @Override
    public String toString() {
        return currentPeriod.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
    }
}
