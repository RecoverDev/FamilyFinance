package ru.student.familyfinance_desktop.FXMLController.Purchases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ru.student.familyfinance_desktop.Configuration.Navigator;

@Component
@FxmlView("PurchasesPage.fxml")
@RequiredArgsConstructor
public class PurchasesController {
    private final Navigator navigator;

}
