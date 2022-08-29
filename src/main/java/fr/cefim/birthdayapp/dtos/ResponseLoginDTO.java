package fr.cefim.birthdayapp.dtos;

import fr.cefim.birthdayapp.security.MyPrincipalUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseLoginDTO implements Serializable {

    private String token;

    private MyPrincipalUser myPrincipalUser;

    public ResponseLoginDTO(String token, MyPrincipalUser myPrincipalUser) {
        this.token = token;
        this.myPrincipalUser = myPrincipalUser;
    }

}
