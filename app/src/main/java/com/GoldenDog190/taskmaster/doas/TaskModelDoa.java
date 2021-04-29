package com.GoldenDog190.taskmaster.doas;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.GoldenDog190.taskmaster.models.TaskModel;

import java.util.List;

@Dao
public interface TaskModelDoa {
    @Insert
    public void insert(TaskModel taskModel);

    @Query("SELECT * FROM TaskModel")
    public List<TaskModel> findAll();

    @Query("SELECT * FROM TaskModel")
    public List<TaskModel> findAllReversed();
}
