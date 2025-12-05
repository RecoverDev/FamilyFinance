package ru.student.familyfinance_desktop.Model;

import org.apache.hc.client5.http.utils.Base64;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.student.familyfinance_desktop.Storage.Storage;

@Setter
@Getter
@RequiredArgsConstructor
@Component
public class AutorizateData {
    private final Storage storage;

    private String username = "Example";
    private String password = "EmptyPa$$w0rd";

    public String getBase64String() {
        String autorizationString = this.getUsername() + ":" + this.getPassword();
        byte[] base64Bytes = Base64.encodeBase64(autorizationString.getBytes());
        String base64String = new String(base64Bytes);
        return base64String;
    }

    public boolean saveParams() {
        return storage.saveCreditional(username, password);
    }

    public void loadParams() {
        this.username = storage.loadCreditional("login");
        this.password = storage.loadCreditional("password");
    }

    public void removeParams() {
        storage.removeCreditional("login");
        storage.removeCreditional("password");
    }

    @Override
    public String toString() {
        return String.format("UserName: %s Password: %s", this.getUsername(),this.getPassword());
    }

}
