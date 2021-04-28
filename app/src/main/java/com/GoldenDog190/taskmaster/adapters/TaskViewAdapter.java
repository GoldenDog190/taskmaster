package com.GoldenDog190.taskmaster.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.activity.RecyclerViewTasks;
import com.GoldenDog190.taskmaster.models.TaskModel;

import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.TaskModelViewHolder> {
    static String TAG = "GoldenDog190.TaskViewAdapter";
    public TaskListener listener;
    List<TaskModel> taskModelList;

    public TaskViewAdapter(List<TaskModel> taskModelList, TaskListener listener){
        this.taskModelList = taskModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewAdapter.TaskModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        TaskViewAdapter.TaskModelViewHolder taskModelViewHolder = new TaskViewAdapter.TaskModelViewHolder(view);
        return taskModelViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TaskModelViewHolder holder, int position) {
        holder.position = position;
        ((TextView)holder.itemView.findViewById(R.id.textViewTaskItem))
                .setText(taskModelList.get(position).title + " "
                        + taskModelList.get(position).body + " "
                        + taskModelList.get(position).assigned);

        holder.itemView.setOnClickListener(v -> {
            Log.i(TAG, "clicked on fragment");
        });
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public interface TaskListener{
        void listener(TaskModel taskModel);
    }


    public class TaskModelViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public TaskModelViewHolder(@NonNull View itemView){
            super(itemView);
        }

    }

    public interface ClickOnTaskAble {
        public void handleClickOnTask(TaskModelViewHolder taskModelViewHolder);
    }

}
