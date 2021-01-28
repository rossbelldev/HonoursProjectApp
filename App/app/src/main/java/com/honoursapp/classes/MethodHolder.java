package com.honoursapp.classes;

public class MethodHolder {

    public final int method = 0;

    private MethodHolder(){}

    public static MethodHolder getInstance(){
        if(instance == null){
            instance = new MethodHolder();
        }
        return instance;
    }

    private static MethodHolder instance;

}
