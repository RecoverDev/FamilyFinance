package ru.student.familyfinance.Service.Implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.GrossBookRepository;
import ru.student.familyfinance.Service.GrossBookService;

@Service
@RequiredArgsConstructor
public class GrossBookServiceImplementation implements GrossBookService {
    private final GrossBookRepository repository;
    
    @Override
    public boolean addGrossBook(GrossBook grossBook) {
        if (grossBook == null) {
            return false;
        }
        GrossBook result = repository.save(grossBook);
        return grossBook.equals(result);
    }

    @Override
    public boolean removeGrossBook(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<GrossBook> getGrossBooks(LocalDate begin, LocalDate end, Person person) {
        List<GrossBook> result =  (List<GrossBook>)repository.findByPersonAndDateOfOperationBetween(person, begin, end);
        return result;
    }

    @Override
    public double getIncomeByPeriod(LocalDate begin, LocalDate end, Person person) {
        List<GrossBook> result = (List<GrossBook>)repository.findByPersonAndDateOfOperationBetween(person, begin, end);
        return result.stream().filter(g -> g.getIncome() != null).mapToDouble(i -> i.getSumm()).sum();
    }

    @Override
    public List<GrossBook> getListIncomesByPeriod(LocalDate begin, LocalDate end, Person person) {
        List<GrossBook> result = (List<GrossBook>)repository.findByPersonAndDateOfOperationBetween(person, begin, end);
        return result.stream().filter(g -> g.getIncome() != null).toList();
    }

    @Override
    public double getExpensesByPeriod(LocalDate begin, LocalDate end, Person person) {
        List<GrossBook> result = (List<GrossBook>)repository.findByPersonAndDateOfOperationBetween(person, begin, end);
        return result.stream().filter(g -> g.getExpenses() != null).mapToDouble(i -> i.getSumm()).sum();
    }

    @Override
    public List<GrossBook> getListExpensesByPeriod(LocalDate begin, LocalDate end, Person person) {
        List<GrossBook> result = (List<GrossBook>)repository.findByPersonAndDateOfOperationBetween(person, begin, end);
        return result.stream().filter(g -> g.getExpenses() != null).toList();
    }

    @Override
    public double getTargetByPeriod(LocalDate begin, LocalDate end, Person person) {
        List<GrossBook> result = (List<GrossBook>)repository.findByPersonAndDateOfOperationBetween(person, begin, end);
        return result.stream().filter(g -> g.getTarget() != null).mapToDouble(i -> i.getSumm()).sum();
    }

    @Override
    public List<GrossBook> getListTargetByPeriod(LocalDate begin, LocalDate end, Person person) {
        List<GrossBook> result = (List<GrossBook>)repository.findByPersonAndDateOfOperationBetween(person, begin, end);
        return result.stream().filter(g -> g.getTarget() != null).toList();
    }

    @Override
    public List<GrossBook> getListTargetByScroll(List<Target> targets, Person person) {
        return repository.findByPersonAndTargetIsIn(person, targets);
    }


    @Override
    public boolean editGrossBook(GrossBook grossBook) {
        if (grossBook == null) {
            return false;
        }
        GrossBook result = repository.save(grossBook);
        return grossBook.equals(result);
    }

}
