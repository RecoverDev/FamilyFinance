package ru.student.familyfinance_desktop.FXMLController.Dictionary;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.DTO.ProductDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemProduct;
import ru.student.familyfinance_desktop.Mapper.ProductMapper;
import ru.student.familyfinance_desktop.Model.Product;
import ru.student.familyfinance_desktop.Service.ProductService;

@Component
@FxmlView("ProductTabPage.fxml")
@RequiredArgsConstructor
public class ProductTabController implements Initializable {
    private final ProductMapper mapper;
    private final ProductService service;
    private final ProductController productController;

    private final Navigator navigator;

    @Autowired
    private ItemProduct itemProduct;

    @FXML
    private TableView<ProductDTO> productTable;

    @FXML
    private TableColumn<ProductDTO, String> nameProduct;

    @FXML
    private TableColumn<ProductDTO, String> typeExpenses;

    @FXML
    private Button addProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button editProductButton;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        service.setRepositoryListener((event) -> itemProduct.setListExpensesDTO());
        productTable.itemsProperty().bind(new SimpleObjectProperty<ObservableList<ProductDTO>>(itemProduct.getLiExpensesDTO()));
        service.setProducts();

        setItemsToProductTable();
    }

    @FXML
    private void addProductAction(ActionEvent event) {
        Product product = new Product();
        productController.setProduct(product);
        navigator.showModal(productController, "Семейный бюджет. Добавление нового товара");
        if (productController.isOkFlag()) {
            service.addProduct(product);
        }
    }

    @FXML
    private void editProductAction(ActionEvent event) {
        TableSelectionModel<ProductDTO> selectionModel = productTable.getSelectionModel();
        Product product = mapper.toProduct(selectionModel.getSelectedItem());
        if (product == null) {
            return;
        }
        productController.setProduct(product);
        navigator.showModal(productController, "Семейный бюджет. Редактирование товара " + product.getName());
        if (productController.isOkFlag()) {
            service.editProduct(product);
        }
    }

    @FXML
    private void deleteProductAction(ActionEvent event) {
        TableSelectionModel<ProductDTO> selectionModel = productTable.getSelectionModel();
        Product product = mapper.toProduct(selectionModel.getSelectedItem());
        if (product == null) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Удаление товара");
        alert.setContentText("Удалить товар\"" + product.getName() + "\"");
        alert.setTitle("Внимание");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType response = result.get();

        if (response == ButtonType.OK) {
            service.deleteProductById(product.getId());
        }

    }


    private void setItemsToProductTable() {
        nameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeExpenses.setCellValueFactory(new PropertyValueFactory<>("expensesName"));
    }
}
