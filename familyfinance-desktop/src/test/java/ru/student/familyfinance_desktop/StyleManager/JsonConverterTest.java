package ru.student.familyfinance_desktop.StyleManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverterTest {
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

    private JsonConverter jsonConverter;

    @BeforeEach
    private void init() {
        jsonConverter = new JsonConverter(new ObjectMapper());
    }

    @Test
    @DisplayName("Преобразование списка стилей в JSON")
    public void toJsonStringTest() throws JSONException {
        String result = jsonConverter.toJsonString(styles);
        JSONAssert.assertEquals(result, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @DisplayName("Преобразование JSON в список стилей")
    public void fromJsonTest() {
        List<Style> result = jsonConverter.fromJson(json);
        assertThat(result).isEqualTo(styles);
    }


}
