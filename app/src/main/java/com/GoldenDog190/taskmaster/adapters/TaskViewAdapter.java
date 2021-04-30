package com.GoldenDog190.taskmaster.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.activity.MainActivity;
import com.GoldenDog190.taskmaster.activity.RecyclerViewTasks;
import com.GoldenDog190.taskmaster.fragment.TaskFragment;
import com.GoldenDog190.taskmaster.models.TaskModel;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

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
//        Intent intent = new Intent( MainActivity.this, MainActivity.class);
//        startActivity(intent);
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
//        public String design;
        public int position;
        public TaskModelViewHolder(@NonNull View itemView){
            super(itemView);
        }

    }

    public interface ClickOnTaskAble {
        public void handleClickOnTask(TaskModelViewHolder taskModelViewHolder);
    }

}
