package ru.student.familyfinance.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Repository.ProductRepository;
import ru.student.familyfinance.Service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService{
    private final ProductRepository repository;

    @Override
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public boolean removeProduct(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public Product editProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> getProduct(Person person) {
        return repository.findByPerson(person);
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> result = repository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

}
