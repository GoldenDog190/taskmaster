package com.GoldenDog190.taskmaster.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GoldenDog190.taskmaster.R;
import com.amplifyframework.datastore.generated.model.TeamModel;

import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.TaskModelViewHolder> {
    static String TAG = "GoldenDog190.TaskViewAdapter";
//    public TaskListener listener;
    public ClickOnTaskAble clickOnTaskAble;
    List<TeamModel> taskModelList;
//    List<TeamModel> taskModelList;

    public TaskViewAdapter(List<TeamModel> taskModelList, ClickOnTaskAble listener){
        this.taskModelList = taskModelList;
       this.clickOnTaskAble = listener;
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
        holder.taskModel = taskModelList.get(position);
        ((TextView)holder.itemView.findViewById(R.id.textViewTaskItem))
                .setText(
                        taskModelList.get(position).name + " " +
                                taskModelList.get(position).title + " "
                        + taskModelList.get(position).body + " "
                        + taskModelList.get(position).assigned
//                              + " "  + taskModelList.get(position).s3ImageKey
                                + " "  + taskModelList.get(position).location

                                );
//        Log.i( TAG,  taskModelList.get(position).name + " " +
//                taskModelList.get(position).title + " "
//                + taskModelList.get(position).body + " "
//                + taskModelList.get(position).assigned
//                + " "  + taskModelList.get(position).s3ImageKey);
        ImageView img = holder.itemView.findViewById(R.id.imageViewFragmentDetailPg);
        img.setImageBitmap(holder.taskModel.bitmap);
//        Log.i(TAG,holder.taskModel.bitmap + " ");

        TextView location = holder.itemView.findViewById(R.id.textViewLocationNow);
        location.setText(holder.taskModel.location);

        holder.itemView.setOnClickListener(v -> {
            clickOnTaskAble.handleClickOnTask(holder);
//            Log.i(TAG, "clicked on fragment");

        });
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

//    public interface TaskListener{
//        void listener(TaskModel taskModel);
//    }


    public class TaskModelViewHolder extends RecyclerView.ViewHolder {
//        public String design;
        public TeamModel taskModel;
//        public TeamModel taskModel;
        public int position;
        public TaskModelViewHolder(@NonNull View itemView){
            super(itemView);
        }

    }

    public interface ClickOnTaskAble {
        public void handleClickOnTask(TaskModelViewHolder taskModelViewHolder);
    }

}
