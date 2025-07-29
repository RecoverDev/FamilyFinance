package ru.student.familyfinance_desktop.FXMLController.Purchases;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.DTO.BasketDTO;
import ru.student.familyfinance_desktop.DTO.ShopDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemBasket;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemShop;
import ru.student.familyfinance_desktop.Mapper.BasketMapper;
import ru.student.familyfinance_desktop.Service.BasketService;

@Getter
@Setter
@Component
@FxmlView("PurchasesFilterPage.fxml")
public class PurchasesFilterController  implements Initializable {
    private ObservableList<BasketDTO> basketsItems = FXCollections.observableArrayList();

    @Autowired
    private ItemBasket itemBasket;

    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketMapper basketMapper;

    @Autowired
    private ItemShop itemShop;

    @FXML
    private ListView<ShopDTO> ListShops;

    @FXML
    private TableView<BasketDTO> TableBaskets;

    @FXML
    private TableColumn<BasketDTO, Boolean> checkBasket;

    @FXML
    private TableColumn<BasketDTO, String> nameBasket;

    @FXML
    private TableColumn<BasketDTO, String> summBasket;

    @FXML
    private Button showAllButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableBaskets.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        basketService.setBaskets();

        TableBaskets.itemsProperty()
                    .bind(new SimpleObjectProperty<ObservableList<BasketDTO>>(basketsItems));
        basketsItems.setAll(itemBasket.getListBasketDTO());

        checkBasket.setCellValueFactory(new PropertyValueFactory<>("selectItem"));
        checkBasket.setCellFactory(CheckBoxTableCell.forTableColumn(checkBasket));
        nameBasket.setCellValueFactory(new PropertyValueFactory<>("productName"));
        summBasket.setCellValueFactory(new PropertyValueFactory<>("summ"));
        summBasket.setCellFactory(TextFieldTableCell.forTableColumn());
        TableBaskets.setEditable(true);

        ListShops.setItems(itemShop.getListShopDTO());

        ListShops.getSelectionModel()
                 .selectedItemProperty()
                 .addListener((observable, oldValue, newValue) -> {
                    basketsItems.setAll(FXCollections.observableArrayList(basketMapper.toListBasketDTO(basketService.getBasketsByShop(newValue.getId()))));
                
                });
    }

    @FXML
    private void showAllAction(ActionEvent event) {
        basketsItems.setAll(itemBasket.getListBasketDTO());
    }

}
