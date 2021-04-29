package com.GoldenDog190.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.GoldenDog190.taskmaster.doas.TaskModelDoa;
import com.GoldenDog190.taskmaster.models.TaskModel;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskModelDoa taskModelDoa();
}
