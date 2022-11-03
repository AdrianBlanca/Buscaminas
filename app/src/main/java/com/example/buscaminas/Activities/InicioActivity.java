package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.buscaminas.R;

public class InicioActivity extends AppCompatActivity {

    Character nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Bundle parametros = this.getIntent().getExtras();

        if(parametros != null)
            nivel = parametros.getChar("nivel");

    }

    public void onClickBotonNiveles(View v) {
        Intent intent = new Intent(InicioActivity.this, NivelesActivity.class);
        startActivity(intent);

    }

    public void onClickJugar(View v) {
        Intent intent = new Intent(InicioActivity.this, JugarActivity.class);
        intent.putExtra("nivel", nivel);
        startActivity(intent);

    }

}