package com.GoldenDog190.taskmaster.adapters;

import com.GoldenDog190.taskmaster.activity.RecyclerView;
import com.GoldenDog190.taskmaster.models.TaskModel;

import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter {
    List<TaskModel> taskModelList;

    public TaskViewAdapter(List<TaskModel> taskModelList){
        this.taskModelList = taskModelList;
    }


}
