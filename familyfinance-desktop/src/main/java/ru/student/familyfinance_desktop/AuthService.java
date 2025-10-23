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
import ru.student.familyfinance_desktop.Storage.Storage;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final Storage storage;
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

        while (counter.remaining() > 0) {

            //попробуем найти сохраненные данные
            String login = storage.loadCreditional("login");
            String password = storage.loadCreditional("password");

            //если не нашли сохраненные данные, запрашиваем у пользователя
            if (login.isEmpty()) {
                navigator.show(autorizateController,"Семейный бюджет.Авторизация");
            }

            //пробуем авторизоваться
            loginData.setUsername(login);
            loginData.setPassword(password);
            if (service.autorizate()) {
                break;
            }
            counter.inc();
        }

        if (counter.remaining() > 0) {
            navigator.show(desktopController, "Семейный бюджет");
            return true;
        }
        return false;
    }

}
