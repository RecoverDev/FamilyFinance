package ru.student.familyfinance_desktop.StyleManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    public String readFile(String nameFile) throws URISyntaxException {
        String result = "";
        URI uri = null;
        try {
            uri = getClass().getClassLoader().getResource(nameFile).toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Ошибка получения файла со списком стилей: ", e);
        }
        Path path = Paths.get(uri);
        try {
            result = Files.readString(path);
        } catch (IOException e) {
            LOGGER.error("Ошибка чтение файла со списком стилей: ", e);
        }
        return result;
    }

    public boolean writeToFile(String nameFile, String data) {
        URI uri = null;
        try {
            uri = getClass().getClassLoader().getResource(nameFile).toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Ошибка получения пути к файлу " + nameFile, e);
        }
        Path path = Paths.get(uri);
        try {
            path = Files.write(path,data.getBytes());
        } catch (IOException e) {
            LOGGER.error("Ошибка записи в файл " + nameFile, e);
        }
        return Path.of(uri).compareTo(path) == 0;
    }

}
