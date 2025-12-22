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
import ru.student.familyfinance_desktop.DTO.ShopDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemShop;
import ru.student.familyfinance_desktop.Mapper.ShopMapper;
import ru.student.familyfinance_service.Model.Shop;
import ru.student.familyfinance_service.Service.ShopService;

@Component
@FxmlView("ShopTabPage.fxml")
@RequiredArgsConstructor
public class ShopTabController implements Initializable {
    private final ShopMapper mapper;
    private final ShopService service;
    private final ShopController shopController;

    private final Navigator navigator;

    @Autowired
    private ItemShop itemShop;

    @FXML
    private TableView<ShopDTO> ShopTable;

    @FXML
    private TableColumn<ShopDTO, String> nameShop;

    @FXML
    private Button addShopButton;

    @FXML
    private Button deleteShopButton;

    @FXML
    private Button editShopButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ShopTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        service.setRepositoryListener((event) -> itemShop.setListShopDTO());
        ShopTable.itemsProperty().bind(new SimpleObjectProperty<ObservableList<ShopDTO>>(itemShop.getListShopDTO()));
        service.setShops();

        setItemsToShopTable();
    }

    @FXML
    private void addShopAction(ActionEvent event) {
        Shop shop = new Shop();
        shopController.setShop(shop);
        navigator.showModal(shopController, "Семейный бюджет. Добавление нового магазина");
        if (shopController.isOkFlag()) {
            service.addShop(shop);
        }
    }

    @FXML
    private void editShopAction(ActionEvent event) {
        TableSelectionModel<ShopDTO> selectionModel = ShopTable.getSelectionModel();
        Shop shop = mapper.toShop(selectionModel.getSelectedItem());
        if (shop == null) {
            return;
        }
        shopController.setShop(shop);
        navigator.showModal(shopController, "Семейный бюджет. Редактирование магазина " + shop.getName());
        if (shopController.isOkFlag()) {
            service.editShop(shop);
        }
    }

    @FXML
    private void deleteShopAction(ActionEvent event) {
        TableSelectionModel<ShopDTO> selectionModel = ShopTable.getSelectionModel();
        Shop shop = mapper.toShop(selectionModel.getSelectedItem());
        if (shop == null) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Удаление магазина");
        alert.setContentText("Удалить магазин \"" + shop.getName() + "\"");
        alert.setTitle("Внимание");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType response = result.get();

        if (response == ButtonType.OK) {
            service.deleteShopById(shop.getId());
        }
    }

    private void setItemsToShopTable() {
        nameShop.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
