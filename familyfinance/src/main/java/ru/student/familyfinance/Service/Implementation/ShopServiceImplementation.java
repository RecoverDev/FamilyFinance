package ru.student.familyfinance.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Repository.ShopRepository;
import ru.student.familyfinance.Service.ShopService;

@Service
@RequiredArgsConstructor
public class ShopServiceImplementation implements ShopService {
    private final ShopRepository repository;

    @Override
    public Shop addShop(Shop shop) {
        Shop result = repository.save(shop);
        return result;
    }

    @Override
    public boolean removeShop(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<Shop> getShops(Person person) {
        return (List<Shop>)repository.findByPerson(person);
    }

    @Override
    public Shop getShopById(Long id) {
        Optional<Shop> shop = repository.findById(id);
        return shop.isPresent() ? shop.get() : null;
    }

    @Override
    public Shop editShop(Shop shop) {
        if (shop == null) {
            return null;
        }
        Shop result = repository.save(shop);
        return result;
    }

}
