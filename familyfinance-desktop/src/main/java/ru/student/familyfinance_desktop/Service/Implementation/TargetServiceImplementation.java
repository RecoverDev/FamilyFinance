package ru.student.familyfinance_desktop.Service.Implementation;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Repository.Repository;
import ru.student.familyfinance_desktop.RestController.TargetRestController;
import ru.student.familyfinance_desktop.Service.TargetService;

@Service
@RequiredArgsConstructor
public class TargetServiceImplementation implements TargetService {
    private final Repository<Target> repository;
    private final TargetRestController controller;

    @Override
    public void setTargets() {
        List<Target> response = controller.getTargets();
        repository.setCollection(response);
    }

    @Override
    public List<Target> getTargets() {
        return repository.getCollection();
    }

    @Override
    public Target getTargetById(long id) {
        return repository.getItemById(id);
    }

    @Override
    public boolean addTarget(Target target) {
        if (target == null) {
            return false;
        }
        Target response = controller.addTarget(target);
        if (response == null) {
            return false;
        }
        return repository.addItem(response);
    }

    @Override
    public boolean editTarget(Target target) {
        if (target == null) {
            return false;
        }
        Target response = controller.editTarget(target);
        if (response == null) {
            return false;
        }
        return repository.editItem(response);
    }

    @Override
    public boolean deleteTargetById(long id) {
        boolean result = controller.deleteIncomeById(id);
        if (result) {
            return repository.deleteItemById(id);
        }
        return false;
    }

    @Override
    public void setRepositoryListener(PropertyChangeListener listener) {
        repository.addListener(listener);
    }
}
