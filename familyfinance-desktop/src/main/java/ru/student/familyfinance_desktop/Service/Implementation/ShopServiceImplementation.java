package ru.student.familyfinance_desktop.Service.Implementation;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.Shop;
import ru.student.familyfinance_desktop.Repository.Repository;
import ru.student.familyfinance_desktop.RestController.ShopRestController;
import ru.student.familyfinance_desktop.Service.ShopService;

@RequiredArgsConstructor
@Service
public class ShopServiceImplementation implements ShopService{
    private final Repository<Shop> repository;
    private final ShopRestController controller;

    @Override
    public void setShops() {
        List<Shop> list = controller.getShops();
        repository.setCollection(list);
    }

    @Override
    public List<Shop> getShops() {
        return repository.getCollection();
    }

    @Override
    public Shop getShopById(long id) {
        return repository.getItemById(id);
    }

    @Override
    public boolean addShop(Shop shop) {
        Shop result = controller.addShop(shop);
        if (result != null) {
            return repository.addItem(result);
        }
        return false;
    }

    @Override
    public boolean editShop(Shop shop) {
        if (shop == null) {
            return false;
        }
        Shop result = controller.editShop(shop);
        if (result == null) {
            return false;
        }
        return repository.editItem(result);
    }

    @Override
    public boolean deleteShopById(long id) {
        if (controller.deleteShopById(id)) {
            return repository.deleteItemById(id);
        }
        return false;
    }

    @Override
    public void setRepositoryListener(PropertyChangeListener listener) {
        repository.addListener(listener);
    }

}
