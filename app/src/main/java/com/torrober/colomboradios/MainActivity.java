package com.torrober.colomboradios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listaRegiones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaRegiones = findViewById(R.id.listaRegiones);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Regiones, android.R.layout.simple_list_item_1);
        listaRegiones.setAdapter(adapter);
        listaRegiones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private AdapterView<?> adapterView;
            private View view;
            private int index;
            private long l;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                this.adapterView = adapterView;
                this.view = view;
                this.index = index;
                this.l = l;
                Intent intent = new Intent(view.getContext(), EstacionesPorRegion.class);
                intent.putExtra("pos", Integer.toString(this.index));
                intent.putExtra("cityname", this.adapterView.getItemAtPosition(this.index).toString());
                startActivity(intent);
            }
        });
    }
}