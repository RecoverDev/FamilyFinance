package ru.student.familyfinance_desktop.Storage;

/**
 * Класс описывает хранилище для хранения данных пользователя
 */
public interface Storage {

    /**
     * Сохранение пользовательских данных
     * @param name - наименование параметра
     * @param value - значение параметра
     * @return - результат удаления успех/неудача
     */
    public boolean saveCreditional(String name, String value);

    /**
     * Чтение из хранилища пользовательских данных
     * @param - наименование парамтра
     * @return - значение параметра String
     */
    public String loadCreditional(String name);

    /**
     * Удаление параметра из хранилища
     * @param name - наименование парамтра
     */
    public void removeCreditional(String name);
}
