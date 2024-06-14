package com.example.afinal;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.afinal.Model.ToDoModel;
import com.example.afinal.Utils.DatabaseHandler;
import com.example.afinal.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG="ActionBottomDialog";
    private EditText newFood, newCalo, newFat, newPro;
    private TextView tv,tv1,tv2;
    private Button newFoodSaveButton;
    private DatabaseHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newFood = getView().findViewById(R.id.name);
        newCalo = getView().findViewById(R.id.cal);
        newFat = getView().findViewById(R.id.fat);
        newPro = getView().findViewById(R.id.pro);
        tv=getView().findViewById(R.id.calp);
        tv1=getView().findViewById(R.id.fats);
        tv2=getView().findViewById(R.id.pros);
        newFoodSaveButton = getView().findViewById(R.id.button);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newFood.setText(task);
            int calo = bundle.getInt("cal");
            newCalo.setText(calo);
            int fat = bundle.getInt("fat");
            newFat.setText(fat);
            int pro = bundle.getInt("pro");
            newPro.setText(pro);
            assert task != null;
            if(task.length()>0)
                newFoodSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.moon));
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();
        MainActivity m=new MainActivity();

        newFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newFoodSaveButton.setEnabled(false);
                    newFoodSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newFoodSaveButton.setEnabled(true);
                    newFoodSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.moon));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        newCalo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newFoodSaveButton.setEnabled(false);
                    newFoodSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newFoodSaveButton.setEnabled(true);
                    newFoodSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.moon));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        newFat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newFoodSaveButton.setEnabled(false);
                    newFoodSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newFoodSaveButton.setEnabled(true);
                    newFoodSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.moon));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        newPro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newFoodSaveButton.setEnabled(false);
                    newFoodSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newFoodSaveButton.setEnabled(true);
                    newFoodSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.moon));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newFoodSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newFood.getText().toString();
                String cal =newCalo.getText().toString();
                String fat =newFat.getText().toString();
                String pro =newPro.getText().toString();
                if(finalIsUpdate){
                    db.updateFoodName(bundle.getInt("id"), text);
                    db.updateColorie(bundle.getInt("id"), cal);
                    db.updateFat(bundle.getInt("id"), fat);
                    db.updatePro(bundle.getInt("id"), pro);
                }
                else {
                    ToDoModel task=new ToDoModel();
                    task.setName(text);
                    task.setCalo(cal);
                    task.setFat(fat);
                    task.setPro(pro);
                    db.insertTask(task);
                }
             
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
