package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.R;

public class ArticuloInventoActivity extends AppCompatActivity {

    private Invento invento;

    private TextView nombreInvento, nombrePeriodo, añoInvencion, nombreInventor, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        invento = (Invento) getIntent().getSerializableExtra("Invento");

        nombreInvento = (TextView) findViewById(R.id.articulo_invento_nombre_invento);
        nombrePeriodo = (TextView) findViewById(R.id.articulo_invento_nombre_periodo);
        añoInvencion = (TextView) findViewById(R.id.articulo_invento_año_invencion);
        nombreInventor = (TextView) findViewById(R.id.articulo_invento_nombre_inventor);
        descripcion = (TextView) findViewById(R.id.articulo_invento_descripcion);

        nombreInvento.setText(invento.getNombre());
        nombrePeriodo.setText(invento.getPeriodo().getNombrePeriodo());
        if(invento.getAñoInvencion() < 0){
            String texto = String.valueOf(Math.abs(invento.getAñoInvencion())) + " A.C.";
            añoInvencion.setText(texto);
        }else{
            añoInvencion.setText(String.valueOf(invento.getAñoInvencion()));
        }
        nombreInventor.setText(invento.getInventor().getNombreCompleto());
        descripcion.setText(invento.getDescripcion());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.articulo_invento_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.articulo_invento_edit_button:
                Intent intent = new Intent(this, EditarInventoActivity.class);
                intent.putExtra("Invento", this.invento);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
