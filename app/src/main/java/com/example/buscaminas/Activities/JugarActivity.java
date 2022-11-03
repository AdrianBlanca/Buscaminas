package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.buscaminas.R;

public class JugarActivity extends AppCompatActivity {

    Character nivel;
    GridView gridViewMapa;
    int[][] casillas;
    int nColumns;
    int nBombas;
    TextView textViewBombas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);
        nivel = 'E';
        nColumns = 8;
        nBombas = 15;
        textViewBombas = (TextView) findViewById(R.id.textViewBombas);
        textViewBombas.setText(String.valueOf(nBombas));
        Bundle parametros = this.getIntent().getExtras();

        if(parametros != null) {
            nivel = parametros.getChar("nivel");

            if(nivel.equals('E')) {
                nivel = 'E';
                nColumns = 8;
                nBombas = 15;
                textViewBombas.setText(String.valueOf(nBombas));
            }

            else if(nivel.equals('M')) {
                nivel = 'M';
                nColumns = 12;
                nBombas = 30;
                textViewBombas.setText(String.valueOf(nBombas));
            }

            else if(nivel.equals('H')) {
                nivel = 'H';
                nColumns = 16;
                nBombas = 60;
                textViewBombas.setText(String.valueOf(nBombas));
            }
        }

        casillas = new int[nColumns][nColumns];
        rellenarMapa(casillas, nColumns);
        colocarBombas(casillas, nColumns, nBombas);
        colocarNumeros(casillas, nColumns);

        gridViewMapa = findViewById(R.id.gridViewMapa);
        gridViewMapa.setNumColumns(nColumns);
        gridViewMapa.setAdapter(new GridAdapter(this, nColumns, casillas));



    }

    private void rellenarMapa(int[][] casillas, int nColumns) {
        for(int i = 0; i < nColumns; i++) {
            for(int j = 0; j < nColumns; j++)
                casillas[i][j] = R.drawable.unopened;

        }

    }

    private void colocarBombas(int[][] casillas, int nColumns, int nBombas) {
        int i = 0;

        while(i < nBombas) {
            int x = (int) (Math.random() * nColumns);
            int y = (int) (Math.random() * nColumns);

            if(casillas[x][y] != R.drawable.bomb) {
                casillas[x][y] = R.drawable.bomb;
                i++;

            }


        }

    }

    private void colocarNumeros(int[][] casillas, int nColumns) {
        int nBombas;

        for(int i = 0; i < nColumns; i++) {
            for(int j = 0; j < nColumns; j++) {
                if(casillas[i][j] != R.drawable.bomb) {
                    nBombas = comprobarBombas(casillas, i, j);

                    switch(nBombas) {
                        case 0:
                            casillas[i][j] = R.drawable.opened;
                            break;

                        case 1:
                            casillas[i][j] = R.drawable.one;
                            break;

                        case 2:
                            casillas[i][j] = R.drawable.two;
                            break;

                        case 3:
                            casillas[i][j] = R.drawable.three;
                            break;

                        case 4:
                            casillas[i][j] = R.drawable.four;
                            break;

                        case 5:
                            casillas[i][j] = R.drawable.five;
                            break;

                        case 6:
                            casillas[i][j] = R.drawable.six;
                            break;

                        case 7:
                            casillas[i][j] = R.drawable.seven;
                            break;

                        case 8:
                            casillas[i][j] = R.drawable.eight;
                            break;
                    }

                }

            }

        }

    }

    private int comprobarBombas(int[][] casillas, int x, int y) {
        int nBombas = 0;

        int auxX, auxY;
        int length = casillas.length - 1;

        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                auxX = x + i;
                auxY = y + j;

                if(auxX >= 0 && auxX <= length && auxY >= 0 && auxY <= length) {
                    if(casillas[auxX][auxY] == R.drawable.bomb)
                        nBombas++;

                }

            }

        }

        return nBombas;
    }

}