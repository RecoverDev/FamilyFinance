package ru.student.familyfinance.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.TargetRepository;
import ru.student.familyfinance.Service.TargetService;

@Service
@RequiredArgsConstructor
public class TargetServiceImplementation implements TargetService {
    private final TargetRepository repository;
    
    @Override
    public Target addTarget(Target target) {
        if (target == null) {
            return null;
        }
        Target result = repository.save(target);
        return result;
    }

    @Override
    public boolean removeTarget(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<Target> getTarget(Person person) {
        return repository.findByPerson(person);
    }

    @Override
    public Target getTargetById(long id) {
        Optional<Target> result = repository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public Target editTarget(Target target) {
        if (target == null) {
            return null;
        }
        Target result = repository.save(target);
        return result;
    }

}
