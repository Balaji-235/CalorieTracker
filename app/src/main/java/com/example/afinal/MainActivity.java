package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinal.Adapter.ToDoAdapter;
import com.example.afinal.Model.ToDoModel;
import com.example.afinal.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private TextView tv,tv1,tv2;
    private Button bt2,bt3;
    private FloatingActionButton fab;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    AlarmManager alarmManager;
    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.calp);
        tv1=findViewById(R.id.fats);
        tv2=findViewById(R.id.pros);
        bt2=findViewById(R.id.btn2);
        bt3=findViewById(R.id.btn3);

        db=new DatabaseHandler(this);
        db.openDatabase();

        taskList= new ArrayList<>();

        tasksRecyclerView =findViewById(R.id.trv);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter=new ToDoAdapter(db,this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        fab=findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            updateCalorie();

                        });
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }.start();

        updateCalorie();

        Intent intent = new Intent(this, Service.class);

        pi = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        final int time = 86400; //24 hours

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC, (time * 1000), time * 1000, pi);
        }

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PieChartActivity.class);
                int sum1= db.getSumOfColumn();
                int fat1=db.getFatSumOfColumn();
                int pro1=db.getProSumOfColumn();
                i.putExtra("cal", sum1);
                i.putExtra("fat", fat1);
                i.putExtra("pro", pro1);
                startActivity(i);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);

            }
        });


        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(), gg.class);
                startActivity(i2);
            }
        });

    }
    public void updateCalorie(){
        int sum=db.getSumOfColumn();
        int fat=db.getFatSumOfColumn();
        int pro=db.getProSumOfColumn();
        tv.setText(String.valueOf(sum));
        tv1.setText(String.valueOf(fat));
        tv2.setText(String.valueOf(pro));
    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}