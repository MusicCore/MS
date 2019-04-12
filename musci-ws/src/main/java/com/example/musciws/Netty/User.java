package com.example.musciws.Netty;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String pwd;

    public User(String id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public User() {
    }

}
