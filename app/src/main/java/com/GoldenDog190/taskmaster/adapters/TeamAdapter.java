package com.GoldenDog190.taskmaster.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GoldenDog190.taskmaster.R;
import com.amplifyframework.datastore.generated.model.TeamModel;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder>{
    static String TAG = "GoldenDog190.TeamAdapter";

    List<TeamModel> teamModelList;

    public TeamAdapter(List<TeamModel> teamModelList){
        this.teamModelList = teamModelList;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fragment = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);

        TeamViewHolder teamViewHolder = new TeamViewHolder(fragment);
        return teamViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
//        holder.teamModel = teamModelList.get(position);
//        ((TextView)holder.itemView.findViewById(R.id.textViewTaskItem))
//                .setText( teamModelList.get(position).name + " "
//                        + teamModelList.get(position).title + " "
//                        + teamModelList.get(position).body + " "
//                        + teamModelList.get(position).assigned);

//        holder.itemView.setOnClickListener(v -> {
//            clickOnTaskAble.handleClickOnTask(holder);
//            Log.i(TAG, "clicked on fragment");

       // }
//        );
    }


    static public class TeamViewHolder extends RecyclerView.ViewHolder {
        public TeamModel teamModel;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return teamModelList.size();
    }
}
