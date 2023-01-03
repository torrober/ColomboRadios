package com.torrober.colomboradios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Estacion> est;

    public CustomAdapter(Context context, List<Estacion> est) {
        this.context = context;
        this.est = est;
    }

    @Override
    public int getCount() {
        return est.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView LogoEstacion;
        TextView NombreEstacion;
        Estacion est = this.est.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listaradio, null);
        }
        LogoEstacion = view.findViewById(R.id.fotoRadio);
        NombreEstacion = view.findViewById(R.id.nombreRadio);
        Picasso
                .with(context)
                .load(est.img)
                .resize(200,200)
                .centerCrop()
                .into(LogoEstacion);
        NombreEstacion.setText(est.nombre);
        return view;
    }
}
