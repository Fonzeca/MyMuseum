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
import com.a000webhostapp.mymuseum.R;

import java.text.Collator;
import java.util.ArrayList;

public class InicioFragment extends Fragment implements IObserver, SwipeRefreshLayout.OnRefreshListener {
	private Invento[] inventosCargados;
	
	private ProgressDialog loading;
	private SwipeRefreshLayout swipe;
	private boolean cargado, busqueda, busquedaDirecta;
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
		if(!cargado){
			buscarInventos();
		}
		actualizarLista();
    }
	
	@Override
	public void onRefresh() {
		ModuloEntidad.obtenerModulo().buscarInventos(this);
	}
	private void buscarInventos(){
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
		ModuloEntidad.obtenerModulo().buscarInventos(this);
	}
    private void actualizarLista(){
		if(inventosCargados != null){
			Invento[] inventosOrdenados = new Invento[inventosCargados.length];
			int i2 = inventosCargados.length-1;
			for(int i = 0; i < inventosCargados.length; i++){
				inventosOrdenados[i] = inventosCargados[i2];
				i2--;
			}
			
			ArticuloInventoArrayAdapter articuloInventoArrayAdapter = new ArticuloInventoArrayAdapter(getContext(), inventosOrdenados);
			
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
		nombreABuscar = nombre;
		busqueda = true;
		ModuloEntidad.obtenerModulo().buscarInventos(this);
	}
	public void busquedaInventosDirecta(String nombre){
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
		nombreABuscar = nombre;
		Log.v("InicioFragment", nombre);
		busquedaDirecta = true;
		ModuloEntidad.obtenerModulo().buscarInventos(this);
	}
	private void busquedaPrivadaInventosCargados(Invento[] inventosCargados){
		ArrayList<Invento> match = new ArrayList<Invento>();
		Collator comparador = Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		
		for (Invento in: inventosCargados) {
			
			Log.v("Numero de ciclos", in.getNombre().length() - nombreABuscar.length()+"");
			
			for (int i = 0; i <= in.getNombre().length() - nombreABuscar.length(); i++){
				Log.v("Compracion", in.getNombre().substring(i,i+nombreABuscar.length()) + "    -    " + nombreABuscar);
				if(comparador.compare(in.getNombre().substring(i,i+nombreABuscar.length()), nombreABuscar) == 0){
					
					//BORRAR ESTO--------------------------------------------------------------------------
					if(busquedaDirecta){
						Intent intent = new Intent(getContext(), ArticuloInventoActivity.class);
						intent.putExtra("Invento", in);
						startActivity(intent);
						busquedaDirecta = false;
						return;
					}
					//Hasta aca----------------------------------------------------------------------------
					match.add(in);
					break;
				}
			}
		}
		
		if(match.size() == 0){
			new DialogoAlerta(getActivity(), "No se encontro el objeto buscado", "Error").mostrar();
		}else{
			this.inventosCargados = new Invento[match.size()];
			for(int i = 0; i < match.size();i++){
				this.inventosCargados[i] = match.get(i);
			}
		}
		busqueda = false;
		
	}
    
    public void update(Guardable[]g, String respuesta) {
		if(loading.isShowing() || swipe.isRefreshing()){
			if(respuesta.equals(ControlDB.res_exitoBusqueda) && g != null){
				if(g[0] instanceof Invento){
					inventosCargados = (Invento[])g;
					if(busqueda){
						busquedaPrivadaInventosCargados(inventosCargados);
					}else if(busquedaDirecta){
						if(g.length == 1){
							Intent intent = new Intent(getContext(), ArticuloInventoActivity.class);
							intent.putExtra("Invento", g[0]);
							startActivity(intent);
						}else{
							busquedaPrivadaInventosCargados(inventosCargados);
						}
					}
					cargado = true;
					loading.dismiss();
					swipe.setRefreshing(false);
				}
			}else if(respuesta.equals(ControlDB.res_falloConexion)){
				loading.dismiss();
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						swipe.setRefreshing(false);
					}
				});
				//Creamos un alertDialog en el Thread UI del activity
				new DialogoAlerta(getActivity(), ControlDB.res_falloConexion, "Error").mostrar();
			}else if(respuesta.equals(ControlDB.res_tablaInventoVacio)){
				loading.dismiss();
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						swipe.setRefreshing(false);
					}
				});
				//Creamos un alertDialog en el Thread UI del activity
				new DialogoAlerta(getActivity(), ControlDB.res_tablaInventoVacio, "Error").mostrar();
			}
			actualizarLista();
		}
    }
	
}
