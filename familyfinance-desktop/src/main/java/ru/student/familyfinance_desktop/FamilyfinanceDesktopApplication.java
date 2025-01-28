package ru.student.familyfinance_desktop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ru.student.familyfinance_desktop.FXMLController.AutorizateController;
import ru.student.familyfinance_desktop.Configuration.Navigator;

@SpringBootApplication
public class FamilyfinanceDesktopApplication extends Application{
	
	@Autowired
	private Navigator navigator;

	@Autowired
	private AutorizateController autorizateController;

	public static void main(String[] args) {
		Application.launch();
	}

    @Override
    public void start(Stage stage) throws Exception {
        navigator.setStage(stage);
        //navigator.setApplicationContext(applicationContext);
        navigator.show(autorizateController,"Семейный бюджет.Авторизация");
    }

    @Override
    public void init() {
        SpringApplication.run(getClass())
            .getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() {
        Platform.exit();
    }


}
