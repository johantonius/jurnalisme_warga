package com.project.jw.login;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "isi username")
    private String username;

    @NotBlank(message = "password ga boleh kosong")
    private String password;

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
