package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buscaminas.R;

public class JugarActivity extends AppCompatActivity {

    Character nivel;
    GridView gridViewMapa;
    int[][] casillas;
    int[][] numeros;
    boolean[] accedido;
    int nColumns;
    int nBombas;
    TextView textViewBombas;
    boolean marcar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);
        nivel = 'E';
        nColumns = 8;
        nBombas = 15;
        marcar = true;
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
        numeros = new int[nColumns][nColumns];
        accedido = new boolean[nColumns * nColumns];
        rellenarMapa(casillas, accedido, nColumns);
        colocarBombas(numeros, nColumns, nBombas);
        colocarNumeros(numeros, nColumns);

        gridViewMapa = findViewById(R.id.gridViewMapa);
        gridViewMapa.setNumColumns(nColumns);
        gridViewMapa.setAdapter(new GridAdapter(this, nColumns, casillas));

        gridViewMapa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView imageView = (ImageView) view;
                TextView textView = (TextView) findViewById(R.id.textViewBombas);

                if(casillas[i / 8][i % 8] == R.drawable.marked) {
                    imageView.setImageResource(R.drawable.unopened);
                    casillas[i / 8][i % 8] = R.drawable.unopened;
                    textView.setText(String.valueOf(Integer.parseInt(String.valueOf(textView.getText())) + 1));

                }

                else if(casillas[i / 8][i % 8] == R.drawable.unopened && Integer.parseInt(String.valueOf(textView.getText())) > 0){
                    imageView.setImageResource(R.drawable.marked);
                    casillas[i / 8][i % 8] = R.drawable.marked;
                    textView.setText(String.valueOf(Integer.parseInt(String.valueOf(textView.getText())) - 1));

                    if(Integer.parseInt(String.valueOf(textView.getText())) == 0) {
                        if(comprobarVictoria(numeros, casillas)) {
                            LinearLayout linearLayoutFinPartida = (LinearLayout) findViewById(R.id.linearLayoutFinPartida);
                            linearLayoutFinPartida.setVisibility(View.VISIBLE);

                            TextView textViewFinPartida = (TextView) findViewById(R.id.textViewFinPartida);
                            textViewFinPartida.setText("Has marcado todas las bombas. Enhorabuena!");

                        }

                    }

                }

                return true;
            }
        });

        gridViewMapa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView imageView = (ImageView) view;

                if(marcar) {
                    for(int x = 0; x < nColumns; x++) {
                        for(int y = 0; y < nColumns; y++) {
                            if(numeros[x][y] == R.drawable.opened) {
                                revelar(x * nColumns + y);
                                marcar = false;

                            }

                        }

                    }

                }

                switch(numeros[i / 8][i % 8]) {
                    case R.drawable.opened:
                        revelar(i);
                        break;

                    case R.drawable.one:
                        imageView.setImageResource(R.drawable.one);
                        accedido[i] = true;
                        break;

                    case R.drawable.two:
                        imageView.setImageResource(R.drawable.two);
                        accedido[i] = true;
                        break;

                    case R.drawable.three:
                        imageView.setImageResource(R.drawable.three);
                        accedido[i] = true;
                        break;

                    case R.drawable.four:
                        imageView.setImageResource(R.drawable.four);
                        accedido[i] = true;
                        break;

                    case R.drawable.five:
                        imageView.setImageResource(R.drawable.five);
                        accedido[i] = true;
                        break;

                    case R.drawable.six:
                        imageView.setImageResource(R.drawable.six);
                        accedido[i] = true;
                        break;

                    case R.drawable.seven:
                        imageView.setImageResource(R.drawable.seven);
                        accedido[i] = true;
                        break;

                    case R.drawable.eight:
                        imageView.setImageResource(R.drawable.eight);
                        accedido[i] = true;
                        break;

                    case R.drawable.bomb:
                        imageView.setImageResource(R.drawable.bomb);
                        accedido[i] = true;
                        gridViewMapa.setOnItemClickListener(null);

                        LinearLayout linearLayoutFinPartida = (LinearLayout) findViewById(R.id.linearLayoutFinPartida);
                        linearLayoutFinPartida.setVisibility(View.VISIBLE);

                        TextView textViewFinPartida = (TextView) findViewById(R.id.textViewFinPartida);
                        textViewFinPartida.setText("Has detonado una bomba. Fin de la partida");
                        textViewFinPartida.setTextColor(getResources().getColor(R.color.red));

                        break;

                }

            }
        });

    }

    public void onClickNuevaPartida(View v) {
        Intent intent = new Intent(JugarActivity.this, JugarActivity.class);
        intent.putExtra("nivel", nivel);
        startActivity(intent);

    }

    public void onClickMenu(View v) {
        Intent intent = new Intent(JugarActivity.this, InicioActivity.class);
        intent.putExtra("nivel", nivel);
        startActivity(intent);

    }

    private void revelar(int position) {
        if((position >= 0 && position < nColumns * nColumns) && !accedido[position]) {
            ImageView im = (ImageView) gridViewMapa.getChildAt(position);

            int x, y;

            x = position / nColumns;
            y = position % nColumns;

            im.setImageResource(numeros[x][y]);
            casillas[x][y] = numeros[x][y];
            accedido[position] = true;

            if(numeros[x][y] == R.drawable.opened) {
                if(position % nColumns != 0) {
                    revelar(position - nColumns - 1);
                    revelar(position - 1);
                    revelar(position + nColumns - 1);

                }

                revelar(position - nColumns);
                revelar(position + nColumns);

                if(position % nColumns != nColumns - 1) {
                    revelar(position - nColumns + 1);
                    revelar(position + 1);
                    revelar(position + nColumns + 1);

                }

            }

        }

    }

    private void rellenarMapa(int[][] casillas, boolean[] accedido, int nColumns) {
        for(int i = 0; i < nColumns; i++) {
            for(int j = 0; j < nColumns; j++) {
                casillas[i][j] = R.drawable.unopened;
                accedido[i + j] = false;

            }

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

    private boolean comprobarVictoria(int[][] numeros, int[][] casillas) {
        boolean victoria = true;

        for(int i = 0; i < nColumns; i++) {
            for(int j = 0; j < nColumns; j++) {
                if(!(numeros[i][j] == R.drawable.bomb && casillas[i][j] == R.drawable.marked))
                    victoria = false;

            }

        }

        return victoria;
    }

}