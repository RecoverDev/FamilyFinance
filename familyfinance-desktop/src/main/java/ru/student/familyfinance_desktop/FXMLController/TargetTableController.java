package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.DTO.TargetDTO;
import ru.student.familyfinance_desktop.Mapper.TargetMapper;
import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Service.TargetService;

@Component
@FxmlView("TargetTablePage.fxml")
@RequiredArgsConstructor
public class TargetTableController implements Initializable{
    private final TargetService service;
    private final TargetMapper mapper;

    @Autowired
    private TargetController targetController;

    @Autowired
    private Navigator navigator;

    @FXML
    private TableView<TargetDTO> targetTable;

    @FXML
    private TableColumn<TargetDTO, String> nameTarget;

    @FXML
    private TableColumn<TargetDTO, String> dateTarget;

    @FXML
    private TableColumn<TargetDTO, Double> summTarget;

    @FXML
    private TableColumn<TargetDTO, Double> accumTarget;

    @FXML
    private Button addTargetButton;

    @FXML
    private Button editTargetButton;

    @FXML
    private Button deleteTargetButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        targetTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        setItemsToTargetTable();
    }

    @FXML
    private void addTargetAction(ActionEvent event) {
        Target target = new Target();

        targetController.setTarget(target);
        navigator.showModal(targetController, "Семейный бюджет. Новая цель");
        if (targetController.isOkFlag()) {
            service.addTarget(targetController.getTarget());
            setItemsToTargetTable();
        }
    }

    @FXML
    private void editTargetAction(ActionEvent event) {
        TableSelectionModel<TargetDTO> selectionModel = targetTable.getSelectionModel();
        Target target = mapper.toTarget(selectionModel.getSelectedItem());
        if (target == null) {
            return;
        }

        targetController.setTarget(target);
        navigator.showModal(targetController, "Семейный бюджет. редактирование цели: " + target.getName());
        if (targetController.isOkFlag()) {
            service.editTarget(targetController.getTarget());
            setItemsToTargetTable();
        }
    }

    @FXML
    private void deleteTargetAction(ActionEvent event) {
        TableSelectionModel<TargetDTO> selectionModel = targetTable.getSelectionModel();
        if (selectionModel == null) {
            return;
        }

        System.setProperty("java.awt.headless", "false");

        int option = JOptionPane.showConfirmDialog(null, "Удалить цель \"" + selectionModel.getSelectedItem().getName() + "\"", "Удаление цели пользователя", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (service.deleteTargetById(selectionModel.getSelectedItem().getId())) {
                setItemsToTargetTable();
            }
        }
    }

    private void setItemsToTargetTable() {
        ObservableList<TargetDTO> data = FXCollections.observableArrayList(mapper.toListTargetDTO(service.getTargets()));

        nameTarget.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateTarget.setCellValueFactory(new PropertyValueFactory<>("settingDate"));
        summTarget.setCellValueFactory(new PropertyValueFactory<>("summ"));
        accumTarget.setCellValueFactory(new PropertyValueFactory<>("accumulateSumm"));

        targetTable.setItems(data);
    }

}
