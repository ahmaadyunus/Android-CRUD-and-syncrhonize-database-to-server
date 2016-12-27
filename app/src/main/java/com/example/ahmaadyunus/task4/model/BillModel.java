package com.example.ahmaadyunus.task4.model;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public class BillModel {
    private int id;
    private String description;
    private String type;
    private int amount;


    public BillModel(int id, String type, String description, int amount){
        this.id=id;
        this.type=type;
        this.description=description;
        this.amount=amount;

    }
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
