package fr.cefim.birthdayapp.models;

import fr.cefim.birthdayapp.entities.User;

public class JwtResponse {

    private String jwt;

    private User mUser;

    public JwtResponse(String jwt, User user) {
        this.jwt = jwt;
        mUser = user;
    }

}
