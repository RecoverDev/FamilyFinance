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

    public LocalDate getBeginPeriod() {
        return LocalDate.of(currentPeriod.getYear(),currentPeriod.getMonthValue(),1);
    }

    public LocalDate getEndPeriod() {
        return LocalDate.of(currentPeriod.getYear(), currentPeriod.getMonthValue(), currentPeriod.lengthOfMonth());
    }

    public boolean isInclude(LocalDate date) {
        return (date.compareTo(getBeginPeriod()) >=0 & date.compareTo(getEndPeriod()) <= 0);
    }

    @Override
    public String toString() {
        return currentPeriod.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
    }
}
