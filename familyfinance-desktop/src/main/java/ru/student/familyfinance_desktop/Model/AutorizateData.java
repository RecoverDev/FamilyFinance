package ru.student.familyfinance_desktop.Model;

import org.apache.hc.client5.http.utils.Base64;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AutorizateData {

    private String username = "Example";
    private String password = "EmptyPa$$w0rd";

    public String getBase64String() {
        String autorizationString = this.getUsername() + ":" + this.getPassword();
        byte[] base64Bytes = Base64.encodeBase64(autorizationString.getBytes());
        String base64String = new String(base64Bytes);
        return base64String;
    }

    @Override
    public String toString() {
        return String.format("UserName: %s Password: %s", this.getUsername(),this.getPassword());
    }

}
