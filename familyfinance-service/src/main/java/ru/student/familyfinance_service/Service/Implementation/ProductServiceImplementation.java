package ru.student.familyfinance_service.Service.Implementation;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_service.Model.Product;
import ru.student.familyfinance_service.Repository.Repository;
import ru.student.familyfinance_service.RestController.ProductRestController;
import ru.student.familyfinance_service.Service.ProductService;

@RequiredArgsConstructor
@Service
public class ProductServiceImplementation implements ProductService {
    private final Repository<Product> repository;
    private final ProductRestController controller;

    @Override
    public void setProducts() {
        List<Product> result = controller.getProducts();
        repository.setCollection(result);
    }

    @Override
    public List<Product> getProducts() {
        return repository.getCollection();
    }

    @Override
    public Product getProductById(long id) {
        return repository.getItemById(id);
    }

    @Override
    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }
        Product result = controller.addProduct(product);
        if (result != null) {
            return repository.addItem(result);
        }
        return false;
    }

    @Override
    public boolean editProduct(Product product) {
        if (product == null) {
            return false;
        }
        Product result = controller.editProduct(product);
        if (result != null) {
            return repository.editItem(result);
        }
        return false;
    }

    @Override
    public boolean deleteProductById(long id) {
        if (controller.deleteProductById(id)) {
            return repository.deleteItemById(id);
        }
        return false;
    }

    @Override
    public void setRepositoryListener(PropertyChangeListener listener) {
        repository.addListener(listener);
    }
}
