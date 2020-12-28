package com.example.records_of_consumption.databases;

import java.io.Serializable;
import java.util.Comparator;

public class Cost_Record implements Serializable {
    private double cost;
    private String cost_class;
    private String cost_use;
    private String time;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCost_class() {
        return cost_class;
    }

    public void setCost_class(String cost_class) {
        this.cost_class = cost_class;
    }

    public String getCost_use() {
        return cost_use;
    }

    public void setCost_use(String cost_use) {
        this.cost_use = cost_use;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
