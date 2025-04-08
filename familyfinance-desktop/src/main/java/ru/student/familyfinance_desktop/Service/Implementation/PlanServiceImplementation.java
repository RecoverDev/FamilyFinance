package ru.student.familyfinance_desktop.Service.Implementation;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Repository.Repository;
import ru.student.familyfinance_desktop.RestController.PlanRestController;
import ru.student.familyfinance_desktop.Service.PlanService;

@Service
@RequiredArgsConstructor
public class PlanServiceImplementation implements PlanService {
    private final Repository<Plan> repository;
    private final PlanRestController controller;

    @Override
    public void setPlans(LocalDate period) {
        List<Plan> response = controller.getPlans(period);
        repository.setCollection(response);
    }

    @Override
    public List<Plan> getPlans() {
        return repository.getCollection();
    }

    @Override
    public Plan getPlanById(long id) {
        return repository.getItemById(id);
    }

    @Override
    public boolean addPlan(Plan plan) {
        if (plan == null) {
            return false;
        }
        Plan response = controller.addPlan(plan);
        if (response == null) {
            return false;
        }
        return repository.addItem(response);
    }

    @Override
    public boolean editPlan(Plan plan) {
        if (plan == null) {
            return false;
        }
        Plan response = controller.editPlan(plan);
        if (response == null) {
            return false;
        }
        return repository.editItem(response);
    }

    @Override
    public boolean deletePlanById(long id) {
        if (controller.deletePlanById(id)) {
            return repository.deleteItemById(id);
        }
        return false;
    }

    @Override
    public void setRepositoryListener(PropertyChangeListener listener) {
        repository.addListener(listener);
    }
}
