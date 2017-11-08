package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Constantes;
import com.a000webhostapp.mymuseum.Controlador.ModuloImagen;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.InventoABM.EditarInventoActivity;

public class ArticuloInventoActivity extends AppCompatActivity implements IObserver {

    private Invento invento;

    private TextView nombreInvento, nombrePeriodo, añoInvencion, nombreInventor, descripcion;
    private ImageView imageView;
	private Imagen imagen;
	
	private ProgressDialog loading;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        invento = (Invento) getIntent().getSerializableExtra("Invento");
	
		buscarInfo();
        nombreInvento = (TextView) findViewById(R.id.articulo_invento_nombre_invento);
        nombrePeriodo = (TextView) findViewById(R.id.articulo_invento_nombre_periodo);
        añoInvencion = (TextView) findViewById(R.id.articulo_invento_año_invencion);
        nombreInventor = (TextView) findViewById(R.id.articulo_invento_nombre_inventor);
        descripcion = (TextView) findViewById(R.id.articulo_invento_descripcion);
	
		imageView = (ImageView) findViewById(R.id.imagenInvento_ArticuloInvento);

        nombreInvento.setText(invento.getNombre());
        nombrePeriodo.setText(invento.getPeriodo().getNombrePeriodo());
        if(invento.getAñoInvencion() < 0){
            String texto = String.valueOf(Math.abs(invento.getAñoInvencion())) + " A.C.";
            añoInvencion.setText(texto);
        }else{
            añoInvencion.setText(String.valueOf(invento.getAñoInvencion()));
        }
        nombreInventor.setText(invento.getInventor().getNombre());
        descripcion.setText(invento.getDescripcion());

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
		
		ModuloImagen.obtenerModulo().buscarImagen(invento.getNombre(), ControlDB.str_obj_Invento,this);
	}

    public boolean onCreateOptionsMenu(Menu menu) {
		if(Constantes.getADMIN()){
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.articulo_invento_menu, menu);
		}
        return true;
    }
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
