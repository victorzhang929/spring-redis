package com.vz.redis.user.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -6011241820070393952L;

    private String id;
    private String name;
    private String password;

    public User(){}
    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

}
