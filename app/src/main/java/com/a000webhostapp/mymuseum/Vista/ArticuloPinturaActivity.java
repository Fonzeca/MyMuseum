package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.InventoABM.EditarInventoActivity;
import com.a000webhostapp.mymuseum.Vista.PinturaABM.EditarPinturaActivity;

public class ArticuloPinturaActivity extends AppCompatActivity {

    private Pintura pintura;

    private TextView nombrePintura, nombrePeriodo, añoCreacion, nombrePintor, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_pintura);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pintura = (Pintura) getIntent().getSerializableExtra("Pintura");

        nombrePintura = (TextView) findViewById(R.id.articulo_pintura_nombre_pintura);
        nombrePeriodo = (TextView) findViewById(R.id.articulo_pintura_nombre_periodo);
        añoCreacion = (TextView) findViewById(R.id.articulo_pintura_año_creacion);
        nombrePintor = (TextView) findViewById(R.id.articulo_pintura_nombre_pintor);
        descripcion = (TextView) findViewById(R.id.articulo_pintura_descripcion);

        nombrePintura.setText(pintura.getNombre());
        nombrePeriodo.setText(pintura.getPeriodo().getNombrePeriodo());
        if(pintura.getAñoInvencion() < 0){
            String texto = String.valueOf(Math.abs(pintura.getAñoInvencion())) + " A.C.";
            añoCreacion.setText(texto);
        }else{
            añoCreacion.setText(String.valueOf(pintura.getAñoInvencion()));
        }
        nombrePintor.setText(pintura.getPintor().getNombre());
        descripcion.setText(pintura.getDescripcion());

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
                Intent intent = new Intent(this, EditarPinturaActivity.class);
                intent.putExtra("Pintura", this.pintura);
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
