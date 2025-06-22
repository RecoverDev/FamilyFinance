package ru.student.familyfinance_desktop.FXMLController.Purchases;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Model.Basket;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.Model.Product;
import ru.student.familyfinance_desktop.Model.Shop;
import ru.student.familyfinance_desktop.Service.ProductService;
import ru.student.familyfinance_desktop.Service.ShopService;

@Getter
@Setter
@Component
@FxmlView("PurchasesPage.fxml")
public class PurchasesController implements Initializable{
    private Basket basket;
    private boolean okFlag;

    @Autowired
    private Person person;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    @FXML
    private ComboBox<Product> comboBasket;

    @FXML
    private ComboBox<Shop> comboShop;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<Product> products = FXCollections.observableArrayList(productService.getProducts());
        comboBasket.setItems(products);
        if (basket.getProduct_id() > 0) {
            comboBasket.setValue(productService.getProductById(basket.getProduct_id()));
        }

        ObservableList<Shop> shops = FXCollections.observableArrayList(shopService.getShops());
        comboShop.setItems(shops);
        if (basket.getShop_id() > 0) {
            comboShop.setValue(shopService.getShopById(basket.getShop_id()));
        }
    }

    @FXML
    private void saveAction(ActionEvent event) {
        basket.setPerson_id(person.getId());
        basket.setProduct_id(comboBasket.getValue().getId());
        basket.setShop_id(comboShop.getValue().getId());
        
        okFlag = true;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        okFlag = false;
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();;
    }

}
