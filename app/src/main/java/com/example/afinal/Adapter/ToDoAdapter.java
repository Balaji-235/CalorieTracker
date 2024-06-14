package com.example.afinal.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.AddNewTask;
import com.example.afinal.MainActivity;
import com.example.afinal.Model.ToDoModel;
import com.example.afinal.R;
import com.example.afinal.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db,MainActivity activity){

        this.db=db;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item= toDoList.get(position);
        holder.name.setText(item.getName());
        holder.calo.setText(item.getCalo());
        holder.fat.setText(item.getFat());
        holder.pro.setText(item.getPro());
    }

    public int getItemCount(){
        return toDoList.size();
    }

    public void setTasks(List<ToDoModel> toDoList){
        this.toDoList=toDoList;
        notifyDataSetChanged();
    }


    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item=toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);

    }

    public void editItem(int position){
        ToDoModel item=toDoList.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("name",item.getName());
        bundle.putInt("cal", Integer.parseInt(item.getCalo()));
        bundle.putInt("fat", Integer.parseInt(item.getFat()));
        bundle.putInt("pro", Integer.parseInt(item.getPro()));
        AddNewTask fragment= new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView calo,fat,pro;
        ViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.name);
            calo = view.findViewById(R.id.cal);
            fat = view.findViewById(R.id.fat);
            pro = view.findViewById(R.id.pro);
        }
    }
}
