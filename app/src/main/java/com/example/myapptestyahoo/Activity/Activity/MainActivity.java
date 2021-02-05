package com.example.myapptestyahoo.Activity.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapptestyahoo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_test_tip);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.dialog_test_tip_btnCancel).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.dialog_test_tip_btnStartTest).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, QuestionActivity.class)));
        dialog.show();
    }
}