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
import ru.student.familyfinance_desktop.DTO.PlanDTO;
import ru.student.familyfinance_desktop.Mapper.PlanMapper;
import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Model.WorkPeriod;
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
    private WorkPeriod currentPeriod;

    @FXML
    private ComboBox<WorkPeriod> comboPeriod;

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

        planTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        setItemsComboPeriod();
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
            setItemsPlanTable();
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
            setItemsPlanTable();
        }
    }

    @FXML
    private void deleteRecordAction(ActionEvent event) {
        TableSelectionModel<PlanDTO> selectionModel = planTable.getSelectionModel();
        PlanDTO planDTO = selectionModel.getSelectedItem();
        if (planDTO == null) {
            return;
        }
        System.setProperty("java.awt.headless", "false");

        int option = JOptionPane.showConfirmDialog(null, "Удалить пункт плана \"" + selectionModel.getSelectedItem().getDescription() + "\"", "Удаление пункта плана", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            service.deletePlanById(selectionModel.getSelectedItem().getId());
            setItemsPlanTable();
        }
    }

    @FXML
    private void reloadPlan(ActionEvent event) {
        LocalDate date = comboPeriod.getValue().getCurrentPeriod();
        currentPeriod.setCurrentPeriod(date);
        service.setPlans(date);
        setItemsPlanTable();
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

    private void setItemsPlanTable() {
        List<Plan> listPlan = service.getPlans();
        ObservableList<PlanDTO> listPlanDTO = FXCollections.observableArrayList(mapper.toListPlanDTO(listPlan));

        namePlan.setCellValueFactory(new PropertyValueFactory<>("description"));
        summPlan.setCellValueFactory(new PropertyValueFactory<>("summ"));

        planTable.setItems(listPlanDTO);
        calculateSumm(listPlanDTO);
    }

    private void calculateSumm(List<PlanDTO> list) {
        Double incomeResult = list.stream().filter(l -> l.getType() == 1).mapToDouble(i -> i.getSumm()).sum();
        Double expensesResult = list.stream().filter(l -> l.getType() == 2).mapToDouble(i -> i.getSumm()).sum();
        Double targetResult = list.stream().filter(l -> l.getType() == 3).mapToDouble(i -> i.getSumm()).sum();

        summIncome.setText(Double.toString(incomeResult));
        summExpenses.setText(Double.toString(expensesResult));
        summTarget.setText(Double.toString(targetResult));
        summRemaider.setText(Double.toString(incomeResult - expensesResult - targetResult));
    }

}