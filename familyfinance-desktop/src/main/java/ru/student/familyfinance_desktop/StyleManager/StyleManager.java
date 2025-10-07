package ru.student.familyfinance_desktop.StyleManager;

import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StyleManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(StyleManager.class);

    @Autowired
    private JsonConverter converter;

    @Autowired
    private FileManager fileManager;

    @Value("${styles.file}")
    private String nameStyleFile;

    public List<Style> getStyleList() {
        String json = null;
        try {
            json = fileManager.readFile(nameStyleFile);
        } catch (URISyntaxException e) {
            LOGGER.error("Ошибка чтения файла с описанием стилей: ", e);
            e.printStackTrace();
        }
        List<Style> result = converter.fromJson(json);
        return result;
    }

    public boolean setStyleList(List<Style> styles) {
        String json = converter.toJsonString(styles);
        return fileManager.writeToFile(nameStyleFile, json);
    }

    public String getCurrentStyle() {
        List<Style> result = this.getStyleList().stream().filter(s -> s.isDefaultStyle()).toList();
        if (result.size() == 0) {
            return "";
        } else {
            return result.getFirst().getFileName();
        }
    }

    public int getPositionCurrentStyle() {
        List<Style> list = this.getStyleList();
        List<Style> result = list.stream().filter(s -> s.isDefaultStyle()).toList();
        return result.size() == 0 ? -1 : list.indexOf(result.getFirst());
    }
}
