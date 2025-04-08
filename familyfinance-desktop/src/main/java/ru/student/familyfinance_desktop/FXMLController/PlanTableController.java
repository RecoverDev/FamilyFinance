package ru.student.familyfinance_desktop.FXMLController;

import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.DTO.PlanDTO;
import ru.student.familyfinance_desktop.FXMLController.ItemModel.ItemPlan;
import ru.student.familyfinance_desktop.Mapper.PlanMapper;
import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Service.PlanService;

@Controller
@FxmlView("PlanTablePage.fxml")
@RequiredArgsConstructor
public class PlanTableController implements Initializable {
    private final PlanService service;
    private final PlanMapper mapper;
    private final PlanController planController;
    private final Navigator navigator;

    @Autowired
    private ItemPlan itemPlan;

    @FXML
    private TableView<PlanDTO> planTable;

    @FXML
    private TableColumn<PlanDTO, String> namePlan;

    @FXML
    private TableColumn<PlanDTO, Double> summPlan;

    @FXML
    private Label summIncome;

    @FXML
    private Label summExpenses;

    @FXML
    private Label summTarget;

    @FXML
    private Label summRemaider;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        service.setRepositoryListener((event) -> itemPlan.setListPlanDTO());

        planTable.itemsProperty()
                 .bind(new SimpleObjectProperty<ObservableList<PlanDTO>>(itemPlan.getListPlanDTO()));

        planTable.setRowFactory(planTable -> new TableRow<>() {
            @Override
            protected void updateItem(PlanDTO item, boolean empty) {
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

        itemPlan.getListPlanDTO().addListener(new ListChangeListener<PlanDTO>() {
            @Override
            public void onChanged(Change<? extends PlanDTO> c) {
                double calcIncome = c.getList().stream().filter(l -> l.getType() == 1).mapToDouble(i -> i.getSumm()).sum();
                summIncome.setText(String.format("%.2f р", calcIncome));
                double calcExpenses = c.getList().stream().filter(l -> l.getType() == 2).mapToDouble(i -> i.getSumm()).sum();
                summExpenses.setText(String.format("%.2f р", calcExpenses));
                double calcTarget = c.getList().stream().filter(l -> l.getType() == 3).mapToDouble(i -> i.getSumm()).sum();
                summTarget.setText(String.format("%.2f р", calcTarget));
                summRemaider.setText(String.format("%.2f р", calcIncome - calcExpenses - calcTarget));
            }
        });

        planTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        setItemsPlanTable();
    }

    @FXML
    private void addIncomeAction(ActionEvent event) {
        addPlan(planController.INCOME);
    }

    @FXML
    private void addTargetAction(ActionEvent event) {
        addPlan(planController.TARGET);
    }

    @FXML
    private void addExpensesAction(ActionEvent event) {
        addPlan(planController.EXPENSES);
    }

    private void addPlan(int mode) {
        Plan plan = new Plan();
        planController.setAction(mode);
        planController.setPlan(plan);
        navigator.showModal(planController, "Семейный бюджет. Планируем месяц");

        if (planController.isOkFlag()) {
            service.addPlan(planController.getPlan());
        }
    }


    @FXML
    private void editRecordAction(ActionEvent event) {
        TableSelectionModel<PlanDTO> selectionModel = planTable.getSelectionModel();
        PlanDTO planDTO = selectionModel.getSelectedItem();
        if (planDTO == null) {
            return;
        }
        Plan plan = mapper.toPlan(planDTO);
        planController.setAction(planDTO.getType());
        planController.setPlan(plan);
        navigator.showModal(planController, "Семейный бюджет. Редактируем план " + planDTO.getDescription());

        if (planController.isOkFlag()) {
            service.editPlan(planController.getPlan());
        }
    }

    @FXML
    private void deleteRecordAction(ActionEvent event) {
        TableSelectionModel<PlanDTO> selectionModel = planTable.getSelectionModel();
        PlanDTO planDTO = selectionModel.getSelectedItem();
        if (planDTO == null) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Удаление пункта плана");
        alert.setContentText("Удалить пункт плана \"" + selectionModel.getSelectedItem().getDescription() + "\"");
        alert.setTitle("Внимание");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType response = result.get();

        if (response == ButtonType.OK){
            service.deletePlanById(selectionModel.getSelectedItem().getId());
        }
    }

    private void setItemsPlanTable() {

        namePlan.setCellValueFactory(new PropertyValueFactory<>("description"));
        summPlan.setCellValueFactory(new PropertyValueFactory<>("summ"));
    }
}