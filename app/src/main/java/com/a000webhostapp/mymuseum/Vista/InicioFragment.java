package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;

import java.text.Collator;
import java.util.ArrayList;

public class InicioFragment extends Fragment implements IObserver, SwipeRefreshLayout.OnRefreshListener {
	private Objeto[] objetosCargados;
	
	private ProgressDialog loading;
	private SwipeRefreshLayout swipe;
	private boolean busqueda, busquedaDirecta;
	private String nombreABuscar;
	
	public InicioFragment() {
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
	}

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inicio, container, false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipeActualizar_InicioFragment);
		swipe.setOnRefreshListener(this);
		buscarObjetos();
		actualizarLista();
	}
	
	@Override
	public void onRefresh() {
		ModuloEntidad.obtenerModulo().buscarObjetos(this);
	}
	private void buscarObjetos(){
		loading = new ProgressDialog(getContext()){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setCancelable(false);
		loading.setMessage("Espere un momento...");
		loading.show();
		ModuloEntidad.obtenerModulo().buscarObjetos(this);
	}
	private void actualizarLista(){
		if(objetosCargados != null){
			Objeto[] objetosOrdenados = new Objeto[objetosCargados.length];
			int i2 = objetosCargados.length-1;
			for(int i = 0; i < objetosCargados.length; i++){
				objetosOrdenados[i] = objetosCargados[i2];
				i2--;
			}
			
			ArticuloInventoArrayAdapter articuloInventoArrayAdapter = new ArticuloInventoArrayAdapter(getContext(), objetosOrdenados);
			
			ListView inventosRecientesList = (ListView) getView().findViewById(R.id.inventos_recientes_list);
			inventosRecientesList.setAdapter(articuloInventoArrayAdapter);
			
		}
	}
	
	public void busquedaInventos(String nombre){
		loading = new ProgressDialog(getContext()){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setCancelable(false);
		loading.setMessage("Buscando...");
		loading.show();
		busqueda = true;
		ModuloEntidad.obtenerModulo().buscarInventosRefinada(this,nombre);
	}
	public void busquedaPinturas(String nombre){
		loading = new ProgressDialog(getContext()){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setCancelable(false);
		loading.setMessage("Buscando...");
		loading.show();
		busqueda = true;
		ModuloEntidad.obtenerModulo().buscarPinturasRefinada(this,nombre);
	}
	public void busquedaObjetoDirecto(String nombre, String entidad){
		loading = new ProgressDialog(getContext()){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setCancelable(false);
		loading.setMessage("Buscando...");
		loading.show();
		busquedaDirecta = true;
		switch (entidad){
			case ControlDB.str_obj_Invento:
				ModuloEntidad.obtenerModulo().buscarInventoDirecto(this,nombre);
				break;
			case ControlDB.str_obj_Pintura:
				ModuloEntidad.obtenerModulo().buscarPinturaDirecto(this,nombre);
				break;
		}
	}
	
	/*public void update(Guardable[]g, String respuesta) {
		if(loading.isShowing() || swipe.isRefreshing()){
			switch(respuesta){
				case ControlDB.res_exito:
					if(g != null){
						if(g.length != 0 && g[0] instanceof Objeto){
							objetosCargados = (Objeto[])g;
							Log.v("Cantidad", objetosCargados.length+"");
							
							if(busqueda){
								if(g[0] instanceof Invento){
									busquedaPrivadaInventosCargados(objetosCargados);
								}else if(g[0] instanceof Pintura){
									busquedaPrivadaPinturasCargados(objetosCargados);
								}
							}else if(busquedaDirecta){
								if(g.length == 1){
									if(g[0] instanceof Invento){
										Intent intent = new Intent(getContext(), ArticuloInventoActivity.class);
										intent.putExtra("Invento", g[0]);
										startActivity(intent);
									}else if(g[0] instanceof Pintura){
										Intent intent = new Intent(getContext(), ArticuloPinturaActivity.class);
										intent.putExtra("Pintura", g[0]);
										startActivity(intent);
									}
								}
							}
							loading.dismiss();
							swipe.setRefreshing(false);
						}
					}
					break;
				case ControlDB.res_falloConexion:
					loading.dismiss();
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							swipe.setRefreshing(false);
						}
					});
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(getActivity(), ControlDB.res_falloConexion, "Error").mostrar();
					break;
				case ControlDB.res_tablaInventoVacio:
					loading.dismiss();
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							swipe.setRefreshing(false);
						}
					});
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(getActivity(), ControlDB.res_tablaInventoVacio, "Error").mostrar();
					break;
			}
			actualizarLista();
		}
	}*/
	public void update(Guardable[] g,int request, String respuesta){
		if(loading.isShowing() || swipe.isRefreshing()){
			switch(respuesta){
				case ControlDB.res_exito:
					if(g != null){
						if(g.length != 0 && g[0] instanceof Objeto){
							Intent intent;
							switch (request){
								case ModuloEntidad.RQS_BUSQUEDA_OBJETO_TOTAL:
								case ModuloEntidad.RQS_BUSQUEDA_INVENTOS_REFINADO:
								case ModuloEntidad.RQS_BUSQUEDA_PINTURAS_REFINADO:
									objetosCargados = (Objeto[])g;
									break;
								case ModuloEntidad.RQS_BUSQUEDA_INVENTO_DIRECTA:
									intent = new Intent(getContext(), ArticuloInventoActivity.class);
									intent.putExtra("Invento", g[0]);
									startActivity(intent);
									break;
								case ModuloEntidad.RQS_BUSQUEDA_PINTURA_DIRECTA:
									intent = new Intent(getContext(), ArticuloPinturaActivity.class);
									intent.putExtra("Pintura", g[0]);
									startActivity(intent);
									break;
							}
							loading.dismiss();
							swipe.setRefreshing(false);
						}
					}
					break;
				case ControlDB.res_falloConexion:
					loading.dismiss();
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							swipe.setRefreshing(false);
						}
					});
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(getActivity(), ControlDB.res_falloConexion, "Error").mostrar();
					break;
				case ControlDB.res_tablaInventoVacio:
					loading.dismiss();
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							swipe.setRefreshing(false);
						}
					});
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(getActivity(), ControlDB.res_tablaInventoVacio, "Error").mostrar();
					break;
			}
			actualizarLista();
		}
	}
	
}
