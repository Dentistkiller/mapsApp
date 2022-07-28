package com.mapApp.lasttest;

public class UserD {
    String Username;
    String Transport;
    String System;
    String Key;

    public UserD() {
    }

    public UserD(String username, String transport, String system, String key) {
        Username = username;
        Transport = transport;
        System = system;
        Key = key;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getTransport() {
        return Transport;
    }

    public void setTransport(String transport) {
        Transport = transport;
    }

    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }


}
