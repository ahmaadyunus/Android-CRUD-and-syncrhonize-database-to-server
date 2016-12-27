package com.example.ahmaadyunus.task4.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public class Bill extends RealmObject{
    @PrimaryKey
    private int id;
    private String type;
    private String description;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}