package ru.student.familyfinance_desktop.Service.Implementation;

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
    public List<GrossBook> getGrossBooks() {
        return repository.getCollection();
    }

    @Override
    public List<GrossBook> getGrossBookByScroll(List<Target> targets) {
        return controller.getGrossBookByScroll(targets);
    }



}
