package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buscaminas.R;

public class NivelesActivity extends AppCompatActivity {

    Character nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles);
    }

    public void onClickFacil(View v) {
        ImageView imagen = (ImageView) findViewById(R.id.imagenMapa);
        Button botonAceptar = (Button) findViewById(R.id.botonAceptar);
        TextView textViewDatos = (TextView) findViewById(R.id.textViewDatos);

        imagen.setImageResource(R.drawable.easy);
        botonAceptar.setVisibility(View.VISIBLE);
        textViewDatos.setText("8x8\n15 bombas");
        textViewDatos.setVisibility(View.VISIBLE);

        nivel = 'E';

    }

    public void onClickMedio(View v) {
        ImageView imagen = (ImageView) findViewById(R.id.imagenMapa);
        Button botonAceptar = (Button) findViewById(R.id.botonAceptar);
        TextView textViewDatos = (TextView) findViewById(R.id.textViewDatos);

        imagen.setImageResource(R.drawable.medium);
        botonAceptar.setVisibility(View.VISIBLE);
        textViewDatos.setText("12x12\n30 bombas");
        textViewDatos.setVisibility(View.VISIBLE);

        nivel = 'M';

    }

    public void onClickDificil(View v) {
        ImageView imagen = (ImageView) findViewById(R.id.imagenMapa);
        Button botonAceptar = (Button) findViewById(R.id.botonAceptar);
        TextView textViewDatos = (TextView) findViewById(R.id.textViewDatos);

        imagen.setImageResource(R.drawable.hard);
        botonAceptar.setVisibility(View.VISIBLE);
        textViewDatos.setText("16x16\n60 bombas");
        textViewDatos.setVisibility(View.VISIBLE);

        nivel = 'H';

    }

    public void onClickAceptar(View v) {
        Intent intent = new Intent(NivelesActivity.this, InicioActivity.class);
        intent.putExtra("nivel", nivel);
        startActivity(intent);

    }

}