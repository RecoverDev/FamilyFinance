package ru.student.familyfinance_desktop.StyleManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class FileManagerTest {
    private FileManager fileManager;

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

    private String nameFile = "styles.json";
     
    @BeforeEach
    private void init() {
        fileManager = new FileManager();
    }

    @Test
    @DisplayName("Сохранение списка в формате JSON в файл")
    public void writeToFileTest() throws URISyntaxException {
        boolean result = fileManager.writeToFile(nameFile, json);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Чтение из файла списка в формате JSON")
    public void readFileTest() throws JSONException, URISyntaxException {
        String result = fileManager.readFile(nameFile);
        JSONAssert.assertEquals(result, json, JSONCompareMode.NON_EXTENSIBLE);
    }
}
