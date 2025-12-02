package ru.student.familyfinance_desktop.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;
import ru.student.familyfinance_desktop.StyleManager.StyleManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;

@Setter
@Component
public class Navigator {
    private Stage stage;
    private boolean result;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private StyleManager styleManager;


    public void show(Object controller, String title) {
        Scene scene = new Scene(loadFxml(controller));
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public void showModal(Object controller, String title) {
        Scene scene = new Scene(loadFxml(controller));
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setScene(scene);
        modalStage.setTitle(title);
        modalStage.showAndWait();
    }

    public Parent loadFxml(Object controller) {
        String nameStyle = styleManager.getCurrentStyle();
        FxWeaver loader = applicationContext.getBean(FxWeaver.class);
        Parent root = loader.loadView(controller.getClass());
        root.getStylesheets().add(getClass().getClassLoader().getResource(nameStyle).toExternalForm());

        return root;
    }

    public boolean setIcon(String name) {
        Image icon = new Image(getClass().getResourceAsStream(name));
        return this.stage.getIcons().add(icon);
    }

    public boolean getResult() { return result; }
}
