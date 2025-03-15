package ru.student.familyfinance_desktop.FXMLController.ItemModel;

import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.DTO.PlanDTO;
import ru.student.familyfinance_desktop.Mapper.PlanMapper;
import ru.student.familyfinance_desktop.Service.PlanService;

@Component
@RequiredArgsConstructor
public class ItemPlan {
    private final PlanMapper mapper;
    private final PlanService service;

    private ObservableList<PlanDTO> listPlanDTO = FXCollections.observableArrayList();

    public ObservableList<PlanDTO> getListPlanDTO() {
        return listPlanDTO;
    }

    public void setListPlanDTO() {
        listPlanDTO.setAll(mapper.toListPlanDTO(service.getPlans()));
    }
}
