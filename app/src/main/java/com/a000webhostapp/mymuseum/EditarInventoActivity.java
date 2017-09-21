package com.a000webhostapp.mymuseum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Entidades.Invento;

public class EditarInventoActivity extends AppCompatActivity implements IObserver{
    private Invento invento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        invento = (Invento) getIntent().getSerializableExtra("Invento");

        TextView nombreInvento = (TextView) findViewById(R.id.editar_invento_nombre_invento);
        TextView descripcion = (TextView) findViewById(R.id.editar_invento_descripcion);
        Spinner nombrePeriodoSpinner = (Spinner) findViewById(R.id.editar_invento_periodo_spinner);
        TextView añoInvencion = (TextView) findViewById(R.id.editar_invento_año_invencion);
        Spinner nombreInventorSpinner = (Spinner) findViewById(R.id.editar_invento_nombre_inventor_spinner);

        nombreInvento.setText(invento.getNombre());
        añoInvencion.setText(String.valueOf(invento.getAñoInvencion()));
        descripcion.setText(invento.getDescripcion());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void update(Guardable[] g, int id) {

    }
}
