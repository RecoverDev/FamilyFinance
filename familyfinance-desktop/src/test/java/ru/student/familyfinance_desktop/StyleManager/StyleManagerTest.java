package ru.student.familyfinance_desktop.StyleManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
public class StyleManagerTest {
    private StyleManager styleManager;
    private List<Style> styles = List.of(new Style("Cupertino (темная)", "FXML/cupertino-dark.css", false),
                                         new Style("Cupertino (светлая)", "FXML/cupertino-light.css", true),
                                         new Style("Дракула", "FXML/dracula.css", false),
                                         new Style("Север (темная)", "FXML/nord-dark.css", false),
                                         new Style("Север (светлая)", "FXML/nord-light.css", false),
                                         new Style("Премьер (темная)", "FXML/primer-dark.css", false),
                                         new Style("Премьер (светлая)", "FXML/primer-light.css", false));

    private String json = """
    [
        {
            "description":"Cupertino (темная)",
            "fileName":"FXML/cupertino-dark.css",
            "defaultStyle":false
        },
        {
            "description":"Cupertino (светлая)",
            "fileName":"FXML/cupertino-light.css",
            "defaultStyle":true
        },
        {
            "description":"Дракула",
            "fileName":"FXML/dracula.css",
            "defaultStyle":false
        },
        {
            "description":"Север (темная)",
            "fileName":"FXML/nord-dark.css",
            "defaultStyle":false
        },
        {
            "description":"Север (светлая)",
            "fileName":"FXML/nord-light.css",
            "defaultStyle":false
        },
        {
            "description":"Премьер (темная)",
            "fileName":"FXML/primer-dark.css",
            "defaultStyle":false
        },
        {
            "description":"Премьер (светлая)",
            "fileName":"FXML/primer-light.css",
            "defaultStyle":false
        }
    ]""";
                                    
    @Value("${styles.file}")
    private String nameFile;

    @Mock
    private JsonConverter jsonConverterMock;

    @Mock
    private FileManager fileManager;

    @BeforeEach
    private void init() {
        styleManager = new StyleManager(jsonConverterMock, fileManager);
    }

    @Test
    @DisplayName("Открытие файла с описанием стилей и получение списка стилей")
    public void getListStylesTest() throws URISyntaxException {
        doReturn(json).when(fileManager).readFile(nameFile);
        doReturn(styles).when(jsonConverterMock).fromJson(json);

        List<Style> result = styleManager.getStyleList();
        assertThat(result).isEqualTo(styles);
    }

    @Test
    @DisplayName("Запись списка стилей в файл")
    public void setStyleListTest() {
        doReturn(json).when(jsonConverterMock).toJsonString(styles);
        doReturn(true).when(fileManager).writeToFile(nameFile, json);

        boolean result = styleManager.setStyleList(styles);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Получение имени файла выбранного стиля")
    public void getCurrentStyleTest() throws Exception {
        String style = "FXML/cupertino-light.css";
        doReturn(json).when(fileManager).readFile(nameFile);
        doReturn(styles).when(jsonConverterMock).fromJson(json);
        String result = styleManager.getCurrentStyle();

        assertThat(result).isEqualTo(style);
    }
}
