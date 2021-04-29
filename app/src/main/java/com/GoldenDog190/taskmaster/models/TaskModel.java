package com.GoldenDog190.taskmaster.models;

public class TaskModel {
    public String title;
    public String body;
    public String assigned;

    public TaskModel(String title, String body, String assigned){
        this.title = title;
        this.body = body;
        this.assigned = assigned;
    }
}
