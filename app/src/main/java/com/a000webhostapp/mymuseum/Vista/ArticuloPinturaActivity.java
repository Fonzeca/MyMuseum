package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Controlador.ModuloImagen;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.InventoABM.EditarInventoActivity;
import com.a000webhostapp.mymuseum.Vista.PinturaABM.EditarPinturaActivity;

public class ArticuloPinturaActivity extends AppCompatActivity implements IObserver{

    private Pintura pintura;

    private TextView nombrePintura, nombrePeriodo, añoCreacion, nombrePintor, descripcion;
    private ImageView imageView;
    private Imagen imagen;
	
	private ProgressDialog loading;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_pintura);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pintura = (Pintura) getIntent().getSerializableExtra("Pintura");

        buscarInfo();
        nombrePintura = (TextView) findViewById(R.id.articulo_pintura_nombre_pintura);
        nombrePeriodo = (TextView) findViewById(R.id.articulo_pintura_nombre_periodo);
        añoCreacion = (TextView) findViewById(R.id.articulo_pintura_año_creacion);
        nombrePintor = (TextView) findViewById(R.id.articulo_pintura_nombre_pintor);
        descripcion = (TextView) findViewById(R.id.articulo_pintura_descripcion);
        
        imageView = (ImageView) findViewById(R.id.imagenPintura_ArticuloPintura);

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
	
	private void buscarInfo(){
		loading = new ProgressDialog(this){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setMessage("Espere un momento...");
		loading.setCancelable(false);
		loading.show();
		
		ModuloImagen.obtenerModulo().buscarImagen(pintura.getNombre(), ControlDB.str_obj_Pintura,this);
	}
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.articulo_invento_menu, menu);
        return true;
    }
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
	
    
	public void update(Guardable[] g, int request, String respuesta) {
		if(loading.isShowing()){
			switch (respuesta) {
				case ControlDB.res_exito:
					if(g != null) {
						switch (request) {
							case ModuloImagen.RQS_BUSQUEDA_IMAGEN_UNICA:
								if(g[0] instanceof Imagen){
									imagen = (Imagen)g[0];
									imageView.setImageBitmap(imagen.getBitmap());
									imageView.setAdjustViewBounds(true);
								}
								loading.dismiss();
								break;
						}
					}
					break;
			}
		}
	}
}
