package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.regex.Pattern;
public class gg extends AppCompatActivity {
    private EditText age, height, weight;
    private RadioGroup gender;
    private RadioButton male, female;
    private TextView calories, required,textView1, textView2, textView3, textView4, textView5, textView6, text_dummy;
    private Button calculate, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gg);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        gender = findViewById(R.id.gender);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        calories = findViewById(R.id.calories);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        text_dummy = findViewById(R.id.text_dummy);
        calculate = findViewById(R.id.calculate);
        required =findViewById(R.id.required);
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(v -> {
            age.setText("");
            height.setText("");
            weight.setText("");
            gender.clearCheck();
            calories.setText("Calories");
            textView1.setText("");
            textView2.setText("");
            textView3.setText("");
            textView4.setText("");
            textView5.setText("");
            textView6.setText("");
            text_dummy.setVisibility(View.GONE);
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ageText = age.getText().toString();
                String heightText = height.getText().toString();
                String weightText = weight.getText().toString();
                Pattern pattern = Pattern.compile("[0-9]+");
                boolean ageCheck = false;
                boolean heightCheck = false;
                boolean weightCheck = false;
                if (ageText.isEmpty()) {
                    age.setError("Please enter your age");
                    age.requestFocus();
                    ageCheck = false;
                } else if (!pattern.matcher(ageText).matches()) {
                    age.setError("Please enter your age correctly");
                    age.requestFocus();
                    ageCheck = false;
                } else {
                    age.setError(null);
                    ageCheck = true;
                }
                if (heightText.isEmpty()) {
                    height.setError("Please enter your height");
                    height.requestFocus();
                    heightCheck = false;
                } else if (!pattern.matcher(ageText).matches()) {
                    age.setError("Please enter your age correctly");
                    age.requestFocus();
                    heightCheck = false;
                } else {
                    height.setError(null);
                    heightCheck = true;
                }
                if (weightText.isEmpty()) {
                    weight.setError("Please enter your weight");
                    weight.requestFocus();
                    weightCheck = false;
                } else if (!pattern.matcher(ageText).matches()) {
                    age.setError("Please enter your age correctly");
                    age.requestFocus();
                    weightCheck = false;
                } else {
                    weight.setError(null);
                    weightCheck = true;
                }
                if (gender.getCheckedRadioButtonId() == -1) {
                    required.setText("Please Select Gender");
                    required.setVisibility(View.VISIBLE);
                } else {
                    required.setText("");
                    required.setVisibility(View.GONE);
                    if (ageCheck && heightCheck && weightCheck) {
                        calculateCalorie();
                    }
                }
            }
        });
    }

    public void calculateCalorie() {
        int ageValue = Integer.parseInt(age.getText().toString());
        int heightValue = Integer.parseInt(height.getText().toString());
        int weightValue = Integer.parseInt(weight.getText().toString());
        double totalCalories = 0;

        if (gender.getCheckedRadioButtonId() == male.getId()) {
            totalCalories = (10 * weightValue) + (6.25 * heightValue) - (5 * ageValue + 5);
            text_dummy.setVisibility(View.VISIBLE);
        } else {
            totalCalories = (10 * weightValue) + (6.25 * heightValue) - (5 * ageValue - 161);
            calories.setText(String.format("%.2f", totalCalories) + "*");
            text_dummy.setVisibility(View.VISIBLE);
        }
        textView1.setText(String.format("%.2f", totalCalories) + "*");
        textView2.setText(String.format("%.2f", totalCalories * 1.149) + "*");
        textView3.setText(String.format("%.2f", totalCalories * 1.220) + "*");
        textView4.setText(String.format("%.2f", totalCalories * 1.292) + "*");
        textView5.setText(String.format("%.2f", totalCalories * 1.437) + "*");
        textView6.setText(String.format("%.2f", totalCalories * 1.583) + "*");
    }
}