package ru.student.familyfinance.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.ExpensesRepository;
import ru.student.familyfinance.Repository.IncomeRepository;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.PersonService;

@Service
@RequiredArgsConstructor
public class PersonServiceImplementation implements PersonService {
    private final PersonRepository repository;
    private final IncomeRepository incomeRepository;
    private final ExpensesRepository expensesRepository;

    @Override
    public boolean addPerson(Person person) {
        if (person == null) {
            return false;
        }
        if (repository.findByUsername(person.getUsername()) != null) {
            return false;
        }
        Person result = repository.save(person);
        addReferences(person);
        return person.equals(result);
    }

    @Override
    public boolean removePerson(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<Person> getPersons() {
        return (List<Person>)repository.findAll();
    }

    @Override
    public Person getPersonById(long id) {
        Optional<Person> person = repository.findById(id);
        return person.isPresent() ? person.get() : null;
    }

    @Override
    public Person getPersonByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean editPerson(Person person) {
        if (person == null) {
            return false;
        }
        Person result = repository.save(person);
        return person.equals(result);
    }

    private void addReferences(Person person) {
        Person commonPerson = repository.findByUsername("common");

        List<Income> incomes = incomeRepository.findByPerson(commonPerson);
        List<Income> personIncomes = incomes.stream().map(i -> new Income(0, person, i.getName())).toList();
        incomeRepository.saveAll(personIncomes);

        List<Expenses> expenses = expensesRepository.findByPerson(commonPerson);
        List<Expenses> personExpenses = expenses.stream().map(e -> new Expenses(0, person, e.getName(), e.getExpensesType())).toList();
        expensesRepository.saveAll(personExpenses);
        
    }

}
