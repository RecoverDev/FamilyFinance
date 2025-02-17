package ru.student.familyfinance_desktop.Service.Implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Repository.Repository;
import ru.student.familyfinance_desktop.RestController.GrossBookRestController;
import ru.student.familyfinance_desktop.Service.GrossBookService;

@Service
@RequiredArgsConstructor
public class GrossBookServiceImplenetation implements GrossBookService {
    private final Repository<GrossBook> repository;
    private final GrossBookRestController controller;


    @Override
    public void setGrossBooks(LocalDate begin, LocalDate end) {
        List<GrossBook> response = controller.getGrossBooks(begin, end);
        repository.setCollection(response);
    }

    @Override
    public List<GrossBook> getGrossBooks() {
        return repository.getCollection();
    }

    @Override
    public GrossBook getGrossBookById(long id) {
        return repository.getItemById(id);
    }

    @Override
    public boolean addGrossBook(GrossBook grossBook) {
        if (grossBook == null) {
            return false;
        }
        GrossBook response = controller.addGrossBook(grossBook);
        if (response == null) {
            return false;
        }
        return repository.addItem(response);
    }

    @Override
    public boolean editGrossBook(GrossBook grossBook) {
        if (grossBook == null) {
            return false;
        }
        GrossBook response = controller.editGrossBook(grossBook);
        if (response == null) {
            return false;
        }
        return repository.editItem(response);
    }

    @Override
    public boolean deleteGrossBookById(long id) {
        if (controller.deleteGrossBookById(id)) {
            return repository.deleteItemById(id);
        }
        return false;
    }

    @Override
    public List<GrossBook> getGrossBookByScroll(List<Target> targets) {
        return controller.getGrossBookByScroll(targets);
    }

}
