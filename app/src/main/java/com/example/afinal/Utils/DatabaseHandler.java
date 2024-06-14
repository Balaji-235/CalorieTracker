package com.example.afinal.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afinal.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String NAME="toDoListDatabase";
    private static final String TODO_TABLE="todo";
    private static final String ID="id";
    private static final String foodNAME="foodname";
    private static final String CALO="calo";
    private static final String FATS="fats";
    private static final String PRO="pro";
    private static final String CREATE_TODO_TABLE="CREATE TABLE "+TODO_TABLE+"("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +foodNAME+" TEXT, "
            + CALO+" INTEGER,"
            + FATS+" INTEGER,"
            + PRO+" INTEGER)";
    private SQLiteDatabase db;
    public DatabaseHandler(Context context){
        super(context, NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TODO_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }
    public void openDatabase()
    {
        db=this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){
        ContentValues cv= new ContentValues();
        cv.put(foodNAME, task.getName());
        cv.put(CALO, task.getCalo());
        cv.put(FATS, task.getFat());
        cv.put(PRO, task.getPro());
        db.insert(TODO_TABLE, null,cv);
    }

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList= new ArrayList<>();
        Cursor cur=null;
        db.beginTransaction();
        try{
            cur=db.query(true,TODO_TABLE, null,null,null,null, null, null,null,null);
            if (cur != null){
                if (cur.moveToFirst()){
                    do{
                        ToDoModel task= new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setName(cur.getString(cur.getColumnIndexOrThrow(foodNAME)));
                        task.setCalo(String.valueOf(cur.getInt(cur.getColumnIndexOrThrow(CALO))));
                        task.setFat(String.valueOf(cur.getInt(cur.getColumnIndexOrThrow(FATS))));
                        task.setPro(String.valueOf(cur.getInt(cur.getColumnIndexOrThrow(PRO))));
                        taskList.add(task);
                    }while (cur.moveToNext());
                }
            }

        }finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }
    public void updateFoodName(int id, String food){
        ContentValues cv= new ContentValues();
        cv.put(foodNAME,food);
        db.update(TODO_TABLE,cv,ID +"+?", new String[]{String.valueOf(id)});
    }

    public void updateColorie(int id, String cal){
        ContentValues cv= new ContentValues();
        cv.put(CALO,cal);
        db.update(TODO_TABLE,cv,ID +"+?", new String[]{String.valueOf(id)});
    }
    public void updateFat(int id, String fat){
        ContentValues cv= new ContentValues();
        cv.put(FATS,fat);
        db.update(TODO_TABLE,cv,ID +"+?", new String[]{String.valueOf(id)});
    }
    public void updatePro(int id, String Pro){
        ContentValues cv= new ContentValues();
        cv.put(PRO,Pro);
        db.update(TODO_TABLE,cv,ID +"+?", new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID +"=?" ,new String[]{String.valueOf(id)});
    }
    public int getSumOfColumn() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + CALO + ") FROM " + TODO_TABLE, null);
        int sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        return sum;
    }
    public int getFatSumOfColumn() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + FATS + ") FROM " + TODO_TABLE, null);
        int sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        return sum;
    }
    public int getProSumOfColumn() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + PRO + ") FROM " + TODO_TABLE, null);
        int sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        return sum;
    }


}
