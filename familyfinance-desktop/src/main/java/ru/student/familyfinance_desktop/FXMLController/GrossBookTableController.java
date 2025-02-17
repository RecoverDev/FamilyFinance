package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.DTO.GrossBookDTO;
import ru.student.familyfinance_desktop.Mapper.GrossBookMapper;
import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Model.WorkPeriod;
import ru.student.familyfinance_desktop.Service.GrossBookService;

@Controller
@FxmlView("GrossBookTablePage.fxml")
@RequiredArgsConstructor
public class GrossBookTableController implements Initializable{
    private final GrossBookService service;
    private final GrossBookMapper mapper;
    private final GrossBookController grossBookController;
    private final Navigator navigator;

    @Autowired
    private WorkPeriod currentPeriod;

    @FXML
    private ComboBox<WorkPeriod> comboPeriod;

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

        grossBookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        setItemsComboPeriod();
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
            setItemsgrossBookTable();
        }
    }

    @FXML
    private void deleteRecordAction(ActionEvent event) {
        TableSelectionModel<GrossBookDTO> selectionModel = grossBookTable.getSelectionModel();
        GrossBookDTO grossBookDTO = selectionModel.getSelectedItem();
        if (grossBookDTO == null) {
            return;
        }
        System.setProperty("java.awt.headless", "false");

        int option = JOptionPane.showConfirmDialog(null, "Удалить запись \"" + selectionModel.getSelectedItem().getDescription() + "\"", "Удаление записи", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            service.deleteGrossBookById(selectionModel.getSelectedItem().getId());
            setItemsgrossBookTable();
        }
    }

    private void addGrossBook(int mode) {
        GrossBook grossBook = new GrossBook();
        grossBookController.setAction(mode);
        grossBookController.setGrossBook(grossBook);
        navigator.showModal(grossBookController, "Семейный бюджет. Новая запись");

        if (grossBookController.isOkFlag()) {
            service.addGrossBook(grossBookController.getGrossBook());
            setItemsgrossBookTable();
        }
    }

    @FXML
    private void reloadGrossBook(ActionEvent event) {
        LocalDate date = comboPeriod.getValue().getCurrentPeriod();
        currentPeriod.setCurrentPeriod(date);
        LocalDate end = date.plusDays(date.lengthOfMonth() - 1);
        service.setGrossBooks(date, end);
        setItemsgrossBookTable();
    }


    private void setItemsComboPeriod() {
        ObservableList<WorkPeriod> listPeriod = FXCollections.observableArrayList();

        for (LocalDate i = currentPeriod.getCurrentPeriod().minusMonths(12); 
                       i.isBefore(currentPeriod.getCurrentPeriod().plusMonths(12)); 
                       i = i.plusMonths(1)) {
            listPeriod.add(new WorkPeriod(i));
        }

        comboPeriod.setItems(listPeriod);
        comboPeriod.setValue(currentPeriod);
    }

    private void setItemsgrossBookTable() {
        List<GrossBook> listGroosBook = service.getGrossBooks();
        ObservableList<GrossBookDTO> listGroosBookDTO = FXCollections.observableArrayList(mapper.toListGrossBookDTO(listGroosBook));

        dateGrossBook.setCellValueFactory(new PropertyValueFactory<>("dateOfOperation"));
        nameGrossBook.setCellValueFactory(new PropertyValueFactory<>("description"));
        summGrossBook.setCellValueFactory(new PropertyValueFactory<>("summ"));

        grossBookTable.setItems(listGroosBookDTO);
        calculateSumm(listGroosBookDTO);
    }

    private void calculateSumm(List<GrossBookDTO> list) {
        Double incomeResult = list.stream().filter(l -> l.getType() == 1).mapToDouble(i -> i.getSumm()).sum();
        Double expensesResult = list.stream().filter(l -> l.getType() == 2).mapToDouble(i -> i.getSumm()).sum();
        Double targetResult = list.stream().filter(l -> l.getType() == 3).mapToDouble(i -> i.getSumm()).sum();

        summIncome.setText(Double.toString(incomeResult));
        summExpenses.setText(Double.toString(expensesResult));
        summTarget.setText(Double.toString(targetResult));
    }
}
