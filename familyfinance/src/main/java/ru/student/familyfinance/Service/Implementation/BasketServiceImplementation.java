package ru.student.familyfinance.Service.Implementation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.data.util.Pair;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Basket;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Repository.BasketRepository;
import ru.student.familyfinance.Repository.GrossBookRepository;
import ru.student.familyfinance.Service.BasketService;

@Service
@RequiredArgsConstructor
public class BasketServiceImplementation implements BasketService {
    private final BasketRepository repository;
    private final GrossBookRepository grossBookRepository;

    @Override
    public Basket addBasket(Basket basket) {
        return repository.save(basket);
    }

    @Override
    public boolean removeBasket(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<Basket> getBasket(Person person) {
        return repository.findByPerson(person);
    }

    @Override
    public Basket getBasketById(long id) {
        Optional<Basket> result =  repository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public Basket editBasket(Basket basket) {
        return repository.save(basket);
    }

    @Override
    public List<Basket> getBasketByShop(Person person, Shop shop) {
        return repository.findByPersonAndShop(person, shop);
    }

    @Override
    public boolean makePurchase(Person person, List<Pair<Basket, Double>> purchase) {

        Map<Expenses,Double> expenses = purchase.stream()
                                                .collect(Collectors.groupingBy(p -> p.getFirst().getProduct().getExpenses(),HashMap::new,Collectors.summingDouble(p -> p.getSecond())));

        for (Map.Entry<Expenses,Double> entry : expenses.entrySet()) {
            GrossBook grossBook = new GrossBook(0, LocalDate.now(), person, null, entry.getKey(), null, entry.getValue());
            grossBookRepository.save(grossBook);
        }
        return true;
    }

}
