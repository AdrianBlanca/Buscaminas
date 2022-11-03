package com.example.buscaminas.Activities;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.buscaminas.R;

public class GridAdapter extends BaseAdapter {
    Context context;
    int[] botones;
    int[][] casillas;
    int nColumns;

    public GridAdapter(Context context, int nColumns, int[][] casillas) {
        this.context = context;
        this.nColumns = nColumns;
        botones = new int[nColumns * nColumns];
        this.casillas = casillas;

        for(int i = 0; i < nColumns * nColumns; i++) {
            botones[i] = casillas[i / nColumns][i % nColumns];

        }

    }

    @Override
    public int getCount() {
        return botones.length;
    }

    @Override
    public Object getItem(int i) {
        return botones[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        imageView.setImageResource(botones[i]);

        return imageView;
    }
}
