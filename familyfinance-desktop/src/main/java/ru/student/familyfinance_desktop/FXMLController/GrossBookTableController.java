package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.DTO.GrossBookDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemGrossBook;
import ru.student.familyfinance_desktop.Mapper.GrossBookMapper;
import ru.student.familyfinance_service.Model.GrossBook;
import ru.student.familyfinance_service.Service.GrossBookService;

@Controller
@FxmlView("GrossBookTablePage.fxml")
@RequiredArgsConstructor
public class GrossBookTableController implements Initializable{
    private final GrossBookService service;
    private final GrossBookMapper mapper;
    private final GrossBookController grossBookController;
    private final Navigator navigator;

    @Autowired
    private ItemGrossBook itemGrossBook;

    @FXML
    private TableView<GrossBookDTO> grossBookTable;

    @FXML
    private TableColumn<GrossBookDTO,LocalDate> dateGrossBook;

    @FXML
    private TableColumn<GrossBookDTO, String> nameGrossBook;

    @FXML
    private TableColumn<GrossBookDTO,Double> summGrossBook;

    @FXML
    private Label summIncome;

    @FXML
    private Label summExpenses;

    @FXML
    private Label summTarget;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        service.setRepositoryListener((event) -> itemGrossBook.setListGrossBookDTO());

        grossBookTable.itemsProperty()
                      .bindBidirectional(new SimpleObjectProperty<ObservableList<GrossBookDTO>>(itemGrossBook.getListGrossBookDTO()));

        grossBookTable.setRowFactory(planTable -> new TableRow<>() {
            @Override
            protected void updateItem(GrossBookDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    switch (item.getType()) {
                        case 1 -> setStyle("-color-cell-fg:#158927");
                        case 2 -> setStyle("-color-cell-fg:#d93229");
                        case 3 -> setStyle("-color-cell-fg:#0068d9");
                    }
                }
            }
        });

        itemGrossBook.getListGrossBookDTO().addListener(new ListChangeListener<GrossBookDTO>() {
            @Override
            public void onChanged(Change<? extends GrossBookDTO> c) {
                double calcIncome = c.getList().stream().filter(l -> l.getType() == 1).mapToDouble(i -> i.getSumm()).sum();
                summIncome.setText(String.format("%.2f р", calcIncome));
                double calcExpenses = c.getList().stream().filter(l -> l.getType() == 2).mapToDouble(i -> i.getSumm()).sum();
                summExpenses.setText(String.format("%.2f р", calcExpenses));
                double calcTarget = c.getList().stream().filter(l -> l.getType() == 3).mapToDouble(i -> i.getSumm()).sum();
                summTarget.setText(String.format("%.2f р", calcTarget));
            }
        });

        grossBookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        setItemsgrossBookTable();
    }

    @FXML
    private void addIncomeAction(ActionEvent event) {
        addGrossBook(grossBookController.INCOME);
    }

    @FXML
    private void addExpensesAction(ActionEvent event) {
        addGrossBook(grossBookController.EXPENSES);
    }

    @FXML
    private void addTargetAction(ActionEvent event) {
        addGrossBook(grossBookController.TARGET);
    }

    @FXML
    private void editRecordAction(ActionEvent event) {
        TableSelectionModel<GrossBookDTO> selectionModel = grossBookTable.getSelectionModel();
        GrossBookDTO grossBookDTO = selectionModel.getSelectedItem();
        if (grossBookDTO == null) {
            return;
        }

        GrossBook grossBook = mapper.toGrossBook(grossBookDTO);
        grossBookController.setAction(grossBookDTO.getType());
        grossBookController.setGrossBook(grossBook);
        navigator.showModal(grossBookController, "Семейный бюджет. Редактируем запись");

        if (grossBookController.isOkFlag()) {
            service.editGrossBook(grossBookController.getGrossBook());
        }
    }

    @FXML
    private void deleteRecordAction(ActionEvent event) {
        TableSelectionModel<GrossBookDTO> selectionModel = grossBookTable.getSelectionModel();
        GrossBookDTO grossBookDTO = selectionModel.getSelectedItem();
        if (grossBookDTO == null) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Удаление записи");
        alert.setContentText("Удалить запись \"" + selectionModel.getSelectedItem().getDescription() + "\"");
        alert.setTitle("Внимание");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType response = result.get();

        if (response == ButtonType.OK){
            service.deleteGrossBookById(selectionModel.getSelectedItem().getId());
        }
    }

    private void addGrossBook(int mode) {
        GrossBook grossBook = new GrossBook();
        grossBookController.setAction(mode);
        grossBookController.setGrossBook(grossBook);
        navigator.showModal(grossBookController, "Семейный бюджет. Новая запись");

        if (grossBookController.isOkFlag()) {
            service.addGrossBook(grossBookController.getGrossBook());
        }
    }

    private void setItemsgrossBookTable() {

        dateGrossBook.setCellValueFactory(new PropertyValueFactory<>("dateOfOperation"));
        nameGrossBook.setCellValueFactory(new PropertyValueFactory<>("description"));
        summGrossBook.setCellValueFactory(new PropertyValueFactory<>("summ"));
    }

}
