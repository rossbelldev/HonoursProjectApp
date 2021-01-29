package com.honoursapp.classes.holders;

import java.util.ArrayList;

public class MethodHolder {

    public final ArrayList<Integer> method = new ArrayList<>();

    private MethodHolder(){}

    public static MethodHolder getInstance(){
        if(instance == null){
            instance = new MethodHolder();
        }
        return instance;
    }

    private static MethodHolder instance;

}
