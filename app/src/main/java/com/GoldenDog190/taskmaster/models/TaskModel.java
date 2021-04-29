package com.GoldenDog190.taskmaster.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String title;
    public String body;
    public String assigned;

    public TaskModel(String title, String body, String assigned){
        this.title = title;
        this.body = body;
        this.assigned = assigned;
    }
}
