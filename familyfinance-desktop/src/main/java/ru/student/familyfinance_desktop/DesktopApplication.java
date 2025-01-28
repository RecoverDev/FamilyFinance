package ru.student.familyfinance_desktop;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.student.familyfinance_desktop.Configuration.Navigator;
import ru.student.familyfinance_desktop.FXMLController.AutorizateController;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class DesktopApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private Navigator navigator;
    private AutorizateController autorizateController;


    @Override
    public void start(Stage stage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("./FXML/MainForm.fxml"));
        // loader.setControllerFactory(param -> mainController);
        // Parent mainPane = loader.load();
        // Scene scene = new Scene(mainPane);
        // stage.setScene(scene);
        // stage.show();
        navigator.setStage(stage);
        navigator.setApplicationContext(applicationContext);
        navigator.show(autorizateController,"Семейный бюджет.Авторизация");
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(FamilyfinanceDesktopApplication.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }



    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
