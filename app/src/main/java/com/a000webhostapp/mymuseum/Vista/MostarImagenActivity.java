package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Controlador.ModuloImagen;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;

/**
 * Created by Alexis on 6/11/2017.
 */

public class MostarImagenActivity extends AppCompatActivity implements IObserver {
	private static final int RQS_BUSCAR_IMAGEN_CELULAR = 0;
	private Spinner spinner;
	private Button btnInsertar, btnBuscar;
	private ImageView imageView;
	
	private Objeto objetoActual;
	private ProgressDialog loading;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prueba);
		
		buscarInfo();
		spinner = (Spinner) findViewById(R.id.spinnerPRUEBA);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				objetoActual = (Objeto)spinner.getSelectedItem();
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		btnInsertar = (Button) findViewById(R.id.btnInsertarPRUEBA);
		btnInsertar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent,"Elegir imagen"),RQS_BUSCAR_IMAGEN_CELULAR);
			}
		});
		btnBuscar = (Button) findViewById(R.id.btnBuscarPRUEBA);
		btnBuscar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String entidad = null;
				if(objetoActual instanceof Pintura){
					entidad = "Pintura";
				}else if(objetoActual instanceof Invento){
					entidad = "Invento";
				}
				ModuloImagen.obtenerModulo().buscarImagen(objetoActual.getNombre(),entidad,MostarImagenActivity.this);
			}
		});
		imageView = (ImageView)findViewById(R.id.imageViewPRUEBA);
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
		
		ModuloEntidad.obtenerModulo().buscarObjetos(this);
	}
	
	private void actualizarSpinners(Objeto[] g){
		ArrayAdapter<Objeto> adapter = new ArrayAdapter<Objeto>(this, R.layout.support_simple_spinner_dropdown_item, g);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	private void ponerImagen(Guardable guardable){
		if(guardable instanceof Imagen){
			Imagen imagen = (Imagen)guardable;
			imageView.setImageBitmap(imagen.getBitmap());
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == RQS_BUSCAR_IMAGEN_CELULAR){
			String entidad = null;
			if(objetoActual instanceof Pintura){
				entidad = "Pintura";
			}else if(objetoActual instanceof Invento){
				entidad = "Invento";
			}
			ModuloImagen.obtenerModulo().insertarImagen(objetoActual.getNombre(), entidad, this,data.getData(),this);
		}
	}
	
	
	public void update(Guardable[] g, int request, String respuesta) {
		switch (respuesta){
			case ControlDB.res_exito:
				switch (request){
					case ModuloEntidad.RQS_BUSQUEDA_OBJETO_TOTAL:
						actualizarSpinners((Objeto[])g);
						break;
					case ModuloImagen.RQS_BUSQUEDA_IMAGEN_UNICA:
						ponerImagen(g[0]);
						break;
				}
				loading.dismiss();
				break;
		}
	}
}
