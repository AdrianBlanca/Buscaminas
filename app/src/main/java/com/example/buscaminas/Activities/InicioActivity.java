package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.buscaminas.R;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void onClickBotonNiveles(View v) {
        Intent intent = new Intent(InicioActivity.this, NivelesActivity.class);
        startActivity(intent);

    }

}