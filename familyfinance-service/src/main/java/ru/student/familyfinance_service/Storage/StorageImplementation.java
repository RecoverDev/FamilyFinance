package ru.student.familyfinance_service.Storage;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.prefs.Preferences;

@Component
public class StorageImplementation implements Storage{
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageImplementation.class);
    private static final String SECRET_KEY = "your-256-bit-secret-key-length32"; // Замените на свой ключ!
    private static final String ALGORITHM = "AES";

    @Override
    public boolean saveCreditional(String name, String value) {
        boolean result = true;
        Preferences prefs = Preferences.userNodeForPackage(StorageImplementation.class);
        try {
            prefs.put(name, encrypt(value));
        } catch (Exception e) {
            LOGGER.error("Ошибка сохранения параметра " + name, e);
            result = false;
        } 
        return result;       
    }

    @Override
    public String loadCreditional(String name) {
        Preferences prefs = Preferences.userNodeForPackage(StorageImplementation.class);
        String value = decrypt(prefs.get(name, ""));
        return value;    
    }

    @Override
    public void removeCreditional(String name) {
        Preferences prefs = Preferences.userNodeForPackage(StorageImplementation.class);
        prefs.remove(name);
    }    

    private static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM));
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    private static String decrypt(String encryptedData) {
        if (encryptedData.isEmpty()) return "";
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM));
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
        } catch (Exception e) {
            return "";
        }
    }
}
