package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Constantes;
import com.a000webhostapp.mymuseum.Controlador.ModuloImagen;
import com.a000webhostapp.mymuseum.Controlador.ModuloInformacion;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.InventoABM.EditarInventoActivity;
import com.a000webhostapp.mymuseum.Vista.PinturaABM.EditarPinturaActivity;

/**
 * Created by Alexis on 21/11/2017.
 */

public class ArticuloObjetoActivity extends AppCompatActivity implements IObserverEntidad {
	
	private Pintura pintura;
	private Invento invento;
	private String tipoObjeto;
	
	private Imagen imagen;
	
	private TextView nombreObjetoView, nombrePeriodoView, añoView, nombrePersonaView, descripcionView;
	private TextView nombrePeriodo, año, nombrePersona, descripcion;
	private ImageView imageView;
	private ProgressBar carga;
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articulo_objeto);
		
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		tipoObjeto = getIntent().getStringExtra("TipoObjeto");
		
		int idObjeto = -1;
		switch (tipoObjeto){
			case ControlDB.str_obj_Invento:
				invento = (Invento) getIntent().getSerializableExtra(tipoObjeto);
				idObjeto = invento.getID();
				break;
			case ControlDB.str_obj_Pintura:
				pintura = (Pintura) getIntent().getSerializableExtra(tipoObjeto);
				idObjeto = pintura.getID();
				break;
		}
		ModuloInformacion.obtenerModulo().sumar1Busqueda(idObjeto);
		
		
		buscarInfo();
		
		nombreObjetoView = (TextView) findViewById(R.id.articulo_objeto_nombre);
		
		
		nombrePeriodoView = (TextView) findViewById(R.id.articulo_objeto_periodo_view);
		nombrePeriodo = (TextView) findViewById(R.id.articulo_objeto_periodo_text);
		
		añoView = (TextView) findViewById(R.id.articulo_objeto_año_view);
		año = (TextView) findViewById(R.id.articulo_objeto_año_text);
		
		nombrePersonaView = (TextView) findViewById(R.id.articulo_objeto_persona_view);
		nombrePersona = (TextView) findViewById(R.id.articulo_objeto_persona_text);
		
		descripcionView = (TextView) findViewById(R.id.articulo_objeto_descri_view);
		descripcion = (TextView) findViewById(R.id.articulo_objeto_descri_text);
		
		//LayoutInflater.from(this).inflate(R.layout.cargar_view, ((LinearLayout)findViewById(R.id.layout_prueba)), true);
		imageView = (ImageView) findViewById(R.id.articulo_objeto_imagen);
		imageView.setVisibility(View.GONE);
		
		carga = (ProgressBar) findViewById(R.id.articulo_objeto_carga);
		
		
		initCampos();
	}
	private void initCampos(){
		switch (tipoObjeto){
			case ControlDB.str_obj_Invento:
				nombreObjetoView.setText(invento.getNombre());
				nombrePeriodoView.setText("Período científico");
				nombrePeriodo.setText(invento.getPeriodo().getNombrePeriodo());
				añoView.setText("Año de invención");
				if(invento.getAñoInvencion() < 0){
					String texto = String.valueOf(Math.abs(invento.getAñoInvencion())) + " A.C.";
					año.setText(texto);
				}else{
					año.setText(String.valueOf(invento.getAñoInvencion()));
				}
				nombrePersonaView.setText("Inventor");
				nombrePersona.setText(invento.getInventor().getNombre());
				descripcionView.setText("Descripción");
				descripcion.setText(invento.getDescripcion());
				break;
			case ControlDB.str_obj_Pintura:
				nombreObjetoView.setText(pintura.getNombre());
				nombrePeriodoView.setText("Período");
				nombrePeriodo.setText(pintura.getPeriodo().getNombrePeriodo());
				añoView.setText("Año de creación");
				if(pintura.getAñoInvencion() < 0){
					String texto = String.valueOf(Math.abs(pintura.getAñoInvencion())) + " A.C.";
					año.setText(texto);
				}else{
					año.setText(String.valueOf(pintura.getAñoInvencion()));
				}
				nombrePersonaView.setText("Pintor");
				nombrePersona.setText(pintura.getPintor().getNombre());
				descripcionView.setText("Descripción");
				descripcion.setText(pintura.getDescripcion());
				break;
		}
	}
	private void buscarInfo(){
		switch (tipoObjeto){
			case ControlDB.str_obj_Invento:
				ModuloImagen.obtenerModulo().buscarImagen(invento.getNombre(), ControlDB.str_obj_Invento,this);
				break;
			case ControlDB.str_obj_Pintura:
				ModuloImagen.obtenerModulo().buscarImagen(pintura.getNombre(), ControlDB.str_obj_Pintura,this);
				break;
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Constantes.getADMIN()){
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.articulo_invento_menu, menu);
		}
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.articulo_invento_edit_button){
			switch (tipoObjeto){
				case ControlDB.str_obj_Invento:
					Intent intentInve = new Intent(this, EditarInventoActivity.class);
					intentInve.putExtra(ControlDB.str_obj_Invento, this.invento);
					startActivity(intentInve);
					break;
				case ControlDB.str_obj_Pintura:
					Intent intentPintu = new Intent(this, EditarPinturaActivity.class);
					intentPintu.putExtra(ControlDB.str_obj_Pintura, this.pintura);
					startActivity(intentPintu);
					break;
			}
		}
		return super.onOptionsItemSelected(item);
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	
	public void update(Guardable[] g, int request, String respuesta) {
		if(carga.getVisibility() == View.VISIBLE){
			switch (respuesta) {
				case ControlDB.res_exito:
					if(g != null) {
						switch (request) {
							case ModuloImagen.RQS_BUSQUEDA_IMAGEN_UNICA:
								if(g[0] instanceof Imagen){
									imageView.setVisibility(View.VISIBLE);
									imagen = (Imagen)g[0];
									imageView.setImageBitmap(imagen.getBitmap());
									imageView.setAdjustViewBounds(true);
								}
								carga.setVisibility(View.GONE);
								break;
						}
					}
					break;
			}
		}
	}
}
