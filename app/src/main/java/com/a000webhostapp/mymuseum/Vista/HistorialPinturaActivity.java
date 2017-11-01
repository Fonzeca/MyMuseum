package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.Modelo.Traslado;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.InventoABM.EditarInventoActivity;
import com.a000webhostapp.mymuseum.Vista.TrasladoABM.NuevoTrasladoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alexis on 25/10/2017.
 */

public class HistorialPinturaActivity extends AppCompatActivity implements IObserver {
	
	private ListView listaPinturas;
	private ProgressDialog loading;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial_pinturas);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		listaPinturas = (ListView)findViewById(R.id.listaDePinturas_historiaPintura);
		
		
		buscarInfo();
		/*trasladosCargados = new ArrayList<>();
		//CARGA EJEMPLO
		
		pintura = new Pintura("Mona Lisa", "la mona", null, null, 500);
		
		trasladosCargados.add(new Traslado(pintura,"MyMuseum", "Museo de artes", "21/10/2017"));
		trasladosCargados.add(new Traslado(pintura,"Museo de artes", "Museo de españa", "22/10/2017"));
		trasladosCargados.add(new Traslado(pintura,"Museo de españa", "MyMuseum", "25/10/2017"));
		trasladosCargados.add(new Traslado(pintura,"MyMuseum", "El museo de carlitos", "26/10/2017"));
		trasladosCargados.add(new Traslado(pintura,"El museo de carlitos", "Museo industrial", "27/10/2017"));
		trasladosCargados.add(new Traslado(pintura,"Museo industrial", "MyMuseum", "01/11/2017"));
		*/
		
	}
	private void actualizarLista(Traslado [] trasladosCargados){
		if(trasladosCargados != null){
			Traslado aux;
			int imin;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
			for(int i = 0; i < trasladosCargados.length; i++){
				imin = i;
				for(int j = i+1; j < trasladosCargados.length; j++){
					Date d1 = null, d2 = null;
					try {
						d1=sdf.parse(trasladosCargados[j].getFechaTraslado());
						d2=sdf.parse(trasladosCargados[imin].getFechaTraslado());
						if(d1.compareTo(d2) < 0){
							imin = j;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				aux = trasladosCargados[i];
				trasladosCargados[i] = trasladosCargados[imin];
				trasladosCargados[imin] = aux;
			}
			TrasladoArrayAdapter articuloInventoArrayAdapter = new TrasladoArrayAdapter(this, trasladosCargados);
			listaPinturas.setAdapter(articuloInventoArrayAdapter);
		}
	}
	private void buscarInfo(){
		runOnUiThread(new Runnable() {
			public void run() {
				loading = new ProgressDialog(HistorialPinturaActivity.this){
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
			}
		});
		ModuloEntidad.obtenerModulo().buscarTrasladosTOTAL(this);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.articulo_historial_menu, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.articulo_historialPintura_add:
				Intent intent = new Intent(this, NuevoTrasladoActivity.class);
				startActivity(intent);
				break;
			case R.id.articulo_historialPintura_edit:
				break;
			case R.id.articulo_historialPintura_remove:
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
			switch (respuesta){
				case ControlDB.res_exito:
					switch (request){
						case ModuloEntidad.RQS_BUSQUEDA_TRASLADOS_TOTAL:
							actualizarLista((Traslado[])g);
							break;
					}
					loading.dismiss();
					break;
			}
		}
	}
}
