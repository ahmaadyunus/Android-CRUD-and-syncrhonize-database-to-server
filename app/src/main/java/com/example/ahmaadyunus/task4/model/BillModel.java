package com.example.ahmaadyunus.task4.model;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public class BillModel {
    private int id;
    private String description;
    private String type;
    private String date_time;
    private String month;
    private String year;
    private int amount;
    private String message;

    public BillModel(){

    }
    public BillModel(int id, String type, String description, String date_time, String month, String year, int amount){
        this.id=id;
        this.type=type;
        this.description=description;
        this.date_time=date_time;
        this.month=month;
        this.year=year;
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
    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = message;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
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
