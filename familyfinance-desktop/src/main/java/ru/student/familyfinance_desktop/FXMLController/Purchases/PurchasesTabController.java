package ru.student.familyfinance_desktop.FXMLController.Purchases;

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
import ru.student.familyfinance_desktop.DTO.BasketDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemBasket;
import ru.student.familyfinance_desktop.Mapper.BasketMapper;
import ru.student.familyfinance_service.Model.Basket;
import ru.student.familyfinance_service.Service.BasketService;

@Component
@FxmlView("PurchasesTabPage.fxml")
@RequiredArgsConstructor
public class PurchasesTabController implements Initializable{
    private final Navigator navigator;
    private final BasketMapper mapper;
    private final BasketService service;

    @Autowired
    private ItemBasket itemBasket;

    @Autowired
    private PurchasesController purchasesController;

    @Autowired
    private PurchasesFilterController purchasesFilterController;


    @FXML
    private TableView<BasketDTO> basketTable;

    @FXML
    private TableColumn<BasketDTO, String> nameBasket;

    @FXML
    private TableColumn<BasketDTO, String> nameShop;

    @FXML
    private Button addBasketButton;

    @FXML
    private Button editBasketButton;

    @FXML
    private Button deleteBasketButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        basketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        service.setRepositoryListener((event) -> itemBasket.setListBasketDTO());
        basketTable.itemsProperty()
                   .bind(new SimpleObjectProperty<ObservableList<BasketDTO>>(itemBasket.getListBasketDTO()));
        service.setBaskets();

        setItemToBasketTable();
    }

    @FXML
    private void addBasketAction(ActionEvent event) {
        Basket basket = new Basket();
        purchasesController.setBasket(basket);
        navigator.showModal(purchasesController, "Семейный бюджет. Добавление нового товара в план покупок");
        if (purchasesController.isOkFlag()) {
            service.addBasket(purchasesController.getBasket());
        }
    }

    @FXML
    private void editBasketAction(ActionEvent event) {
        TableSelectionModel<BasketDTO> selectionModel = basketTable.getSelectionModel();
        Basket basket = mapper.toBasket(selectionModel.getSelectedItem());
        if (basket == null) {
            return;
        }
        purchasesController.setBasket(basket);
        navigator.showModal(purchasesController, "Семейный бюджет. Редактирование товара" + selectionModel.getSelectedItem().getProductName());
        if (purchasesController.isOkFlag()) {
            service.editBasket(purchasesController.getBasket());
        }
    }

    @FXML
    private void deleteBasketAction(ActionEvent event) {
        TableSelectionModel<BasketDTO> selectionModel = basketTable.getSelectionModel();
        Basket basket = mapper.toBasket(selectionModel.getSelectedItem());
        if (basket == null) {
            return;
        }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Удаление товара");
        alert.setContentText("Удалить товар\"" + selectionModel.getSelectedItem().getProductName() + "\"");
        alert.setTitle("Внимание");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType response = result.get();
        if (response ==ButtonType.OK) {
            service.removeBasketById(basket.getId());
        }
    }

    @FXML
    private void basketFilterAction(ActionEvent event) {
        navigator.showModal(purchasesFilterController, "Покупки");
    }
    
    private void setItemToBasketTable() {
        nameBasket.setCellValueFactory(new PropertyValueFactory<>("productName"));
        nameShop.setCellValueFactory(new PropertyValueFactory<>("shopName"));
    }

}
