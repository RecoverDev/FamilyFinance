package ru.student.familyfinance.Service.Implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Plan;
import ru.student.familyfinance.Repository.PlanRepository;
import ru.student.familyfinance.Service.PlanService;

@Service
@RequiredArgsConstructor
public class PlanServiceImplementation implements PlanService {
    private final PlanRepository repository;
    
    @Override
    public Plan addPlan(Plan plan) {
        if (plan == null) {
            return null;
        }
        LocalDate planDate = LocalDate.of(plan.getDateOfOperation().getYear(), plan.getDateOfOperation().getMonthValue(), 1);
        plan.setDateOfOperation(planDate);
        Plan result = repository.save(plan);
        return result;
    }

    @Override
    public boolean removePlan(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<Plan> getPlans(Person person, LocalDate month) {
        LocalDate planDate = LocalDate.of(month.getYear(), month.getMonthValue(), 1);
        return (List<Plan>)repository.findByPersonAndDateOfOperation(person, planDate);
    }

    @Override
    public double getIncomePlan(Person person, LocalDate month) {
        List<Plan> result = (List<Plan>)repository.findByPersonAndDateOfOperation(person, month);
        return result.stream().filter(p -> p.getIncome() != null).mapToDouble(i -> i.getSumm()).sum();
    }

    @Override
    public double getExpensesPlan(Person person, LocalDate month) {
        List<Plan> result = (List<Plan>)repository.findByPersonAndDateOfOperation(person, month);
        return result.stream().filter(p -> p.getExpenses() != null).mapToDouble(i -> i.getSumm()).sum();
    }

    @Override
    public double getTargetPlan(Person person, LocalDate month) {
        List<Plan> result = (List<Plan>)repository.findByPersonAndDateOfOperation(person, month);
        return result.stream().filter(p -> p.getTarget() != null).mapToDouble(i -> i.getSumm()).sum();
    }

    @Override
    public Plan editPlan(Plan plan) {
        if (plan == null) {
            return null;
        }
        Plan result = repository.save(plan);
        return result;
    }
}
