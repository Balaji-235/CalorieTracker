package com.example.afinal.Model;

import com.example.afinal.Utils.DatabaseHandler;

public class ToDoModel {
    private int id;
    private String name, calo, fat,pro;

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getCalo() {
        return calo;
    }

    public void setCalo(String calo) {
        this.calo = calo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
