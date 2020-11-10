package com.example.infinityjobportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Filter extends AppCompatActivity {
    Spinner jobCategorySpinner, provinceSpinner;
    CheckBox mEnglishCheckBox, mFrenchCheckBox;

    SeekBar seekBar;
    NumberPicker dollarPicker;
    NumberPicker centsPicker;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_filter);
    jobCategorySpinner = findViewById(R.id.jobCategorySpinner);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        mEnglishCheckBox = findViewById(R.id.radioEnglish);
        mFrenchCheckBox = findViewById(R.id.radioFrench);
        back = findViewById(R.id.back);
       // dollarPicker=findViewById(R.id.dollar);
        //centsPicker=findViewById(R.id.cents);



       // dollarPicker.setMinValue(13);
        //dollarPicker.setMaxValue(99);

        //centsPicker.setMinValue(14);
        //centsPicker.setMaxValue(100);
        //centsPicker.setValue(100);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void applyFilter(View view) {


        GlobalStorage.language="";
        GlobalStorage.jobCatogory = jobCategorySpinner.getSelectedItem().toString();
        GlobalStorage.province = provinceSpinner.getSelectedItem().toString();

        if (mEnglishCheckBox.isChecked()  && !mFrenchCheckBox.isChecked()) {
            GlobalStorage.language = "English";
        } else if (mFrenchCheckBox.isChecked()  && !mEnglishCheckBox.isChecked()) {
            GlobalStorage.language = "French";
        } else if (mFrenchCheckBox.isChecked() && mEnglishCheckBox.isChecked()) {
            GlobalStorage.language = "English & French";
        }else{
            GlobalStorage.language = "";
        }

//       GlobalStorage.minSalary = Integer.valueOf(dollarPicker.getValue());
  //      GlobalStorage.maxSalary = Integer.valueOf(centsPicker.getValue());







        startActivity(new Intent(getApplicationContext(),Jobs_search.class));
    }
}