package com.logrequest.logrequest.backend.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

    @Id
    String id;

    String username;

    String email;

    String password;

    String roleId;

    String idToken;

    public User() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
