package ru.student.familyfinance_desktop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.FXMLController.AutorizateController;
import ru.student.familyfinance_desktop.FXMLController.DesktopController;
import ru.student.familyfinance_desktop.Model.AutorizateData;
import ru.student.familyfinance_desktop.SecurityManager.Counter;
import ru.student.familyfinance_desktop.Service.AutorizationService;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final Counter counter;
    private final Navigator navigator;
    private final AutorizationService service;

    @Autowired
    private AutorizateData loginData;

	@Autowired
	private AutorizateController autorizateController;

    @Autowired
    private DesktopController desktopController;


    public boolean startAuthProcess() {

        //таймаут еще не истек, работать не будем
        if (counter.isBlocked()) {
            return false;
        }


        //пробуем авторизоваться
        loginData.loadParams();
        if (service.autorizate()) {
            navigator.show(desktopController, "Семейный бюджет");
        } else {
            navigator.show(autorizateController,"Семейный бюджет.Авторизация");
        }
        return true;
    }

}
