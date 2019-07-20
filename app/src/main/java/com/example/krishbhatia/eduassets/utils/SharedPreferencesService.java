package com.example.krishbhatia.eduassets.utils;


public interface SharedPreferencesService {

    String get(String key);

    void save(String key, String value);

    void remove(String key);

    public Boolean contains(String key);
}
