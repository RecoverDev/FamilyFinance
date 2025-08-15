package ru.student.familyfinance_desktop.Service.Implementation;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.data.util.Pair;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.Basket;
import ru.student.familyfinance_desktop.Repository.Repository;
import ru.student.familyfinance_desktop.RestController.BasketRestController;
import ru.student.familyfinance_desktop.Service.BasketService;

@RequiredArgsConstructor
@Service
public class BasketServiceImplementation implements BasketService{
    private final Repository<Basket> repository;
    private final BasketRestController controller;

    @Override
    public void setBaskets() {
        List<Basket> result = controller.getBaskets();
        repository.setCollection(result);
    }

    @Override
    public List<Basket> getBaskets() {
        return repository.getCollection();
    }

    @Override
    public Basket getBasketById(long id) {
        return repository.getItemById(id);
    }

    @Override
    public List<Basket> getBasketsByShop(long idShop) {
        return repository.getCollection().stream().filter(b -> b.getShop_id() == idShop).toList();
    }

    @Override
    public Basket addBasket(Basket basket) {
        if (basket == null) {
            return null;
        }
        Basket result = controller.addBasket(basket);
        if (result == null) {
            return null;
        }
        if (repository.addItem(result)) {
            return result;
        }
        return null;
    }

    @Override
    public boolean removeBasketById(long id) {
        if (controller.deleteBasketById(id)) {
            return repository.deleteItemById(id);
        }
        return false;
    }

    @Override
    public Basket editBasket(Basket basket) {
        if (basket == null) {
            return null;
        }
        Basket result = controller.editBasket(basket);
        if (result == null) {
            return null;
        }
        if (repository.editItem(result)) {
            return result;
        }
        return null;
    }

    @Override
    public boolean makePurchase(List<Pair<Basket,Double>> baskets) {
        if (controller.postPurchase(baskets)) {
            for (Pair<Basket,Double> pair : baskets) {
                controller.deleteBasketById(pair.getFirst().getId());
            }
            repository.setCollection(controller.getBaskets());
            return true;
        }
        return false;
    }

    @Override
    public void setRepositoryListener(PropertyChangeListener listener) {
        repository.addListener(listener);
    }

}
