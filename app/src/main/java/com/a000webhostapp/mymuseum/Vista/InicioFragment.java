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
import com.a000webhostapp.mymuseum.Modelo.Pintor;
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
		buscarInventos();
		actualizarLista();
    }
	
	@Override
	public void onRefresh() {
		ModuloEntidad.obtenerModulo().buscarObjetos(this);
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
		nombreABuscar = nombre;
		busqueda = true;
		ModuloEntidad.obtenerModulo().buscarInventos(this);
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
		nombreABuscar = nombre;
		busqueda = true;
		ModuloEntidad.obtenerModulo().buscarPinturas(this);
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
		busquedaDirecta = true;
		ModuloEntidad.obtenerModulo().buscarInventos(this);
	}
	public void busquedaPinturaDirecta(String nombre){
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
		busquedaDirecta = true;
		ModuloEntidad.obtenerModulo().buscarPinturas(this);
	}
	private void busquedaPrivadaInventosCargados(Objeto[] objetosCargados){
		ArrayList<Invento> match = new ArrayList<>();
		Collator comparador = Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		ArrayList<Invento> inventosCargados = new ArrayList<>();
		for(Objeto ob : objetosCargados){
			if(ob instanceof Invento){
				inventosCargados.add((Invento)ob);
			}
		}
		
		for (Invento in: inventosCargados) {
			for (int i = 0; i <= in.getNombre().length() - nombreABuscar.length(); i++){
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
		//BORRAR ESTO--------------------------------------------------------------------------
		if(busquedaDirecta){
			new DialogoAlerta(getActivity(), "Ups ! El invento buscado no se encuentra en la base de datos, por favor inténtelo de nuevo.", "Error");
		}
		//Hasta aca----------------------------------------------------------------------------
		if(match.size() == 0){
			new DialogoAlerta(getActivity(), "No se encontro el objeto buscado", "Error").mostrar();
		}else{
			this.objetosCargados = new Objeto[match.size()];
			for(int i = 0; i < match.size();i++){
				this.objetosCargados[i] = match.get(i);
			}
		}
		busqueda = false;
	}
	private void busquedaPrivadaPinturasCargados(Objeto[] objetosCargados){
		ArrayList<Pintura> match = new ArrayList<>();
		Collator comparador = Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		ArrayList<Pintura> pinturasCargadas = new ArrayList<>();
		for(Objeto ob : objetosCargados){
			if(ob instanceof Pintura){
				pinturasCargadas.add((Pintura)ob);
			}
		}
		
		for (Pintura in: pinturasCargadas) {
			for (int i = 0; i <= in.getNombre().length() - nombreABuscar.length(); i++){
				if(comparador.compare(in.getNombre().substring(i,i+nombreABuscar.length()), nombreABuscar) == 0){
					
					//BORRAR ESTO--------------------------------------------------------------------------
					if(busquedaDirecta){
						Intent intent = new Intent(getContext(), ArticuloPinturaActivity.class);
						intent.putExtra("Pintura", in);
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
		//BORRAR ESTO--------------------------------------------------------------------------
		if(busquedaDirecta){
			new DialogoAlerta(getActivity(), "Ups ! La pinura buscada no se encuentra en la base de datos, por favor inténtelo de nuevo.", "Error");
		}
		//Hasta aca----------------------------------------------------------------------------
		if(match.size() == 0){
			new DialogoAlerta(getActivity(), "No se encontro el objeto buscado", "Error").mostrar();
		}else{
			this.objetosCargados = new Objeto[match.size()];
			for(int i = 0; i < match.size();i++){
				this.objetosCargados[i] = match.get(i);
			}
		}
		busqueda = false;
	}
    
    public void update(Guardable[]g, String respuesta) {
		if(loading.isShowing() || swipe.isRefreshing()){
			switch(respuesta){
				case ControlDB.res_exitoBusqueda:
					if(g != null){
						if(g.length != 0 && g[0] instanceof Objeto){
							objetosCargados = (Objeto[])g;
							Log.v("Cantidad", objetosCargados.length+"");
							if(g[0] instanceof Invento){
								if(busqueda){
									busquedaPrivadaInventosCargados(objetosCargados);
								}else if(busquedaDirecta){
									if(g.length == 1){
										Intent intent = new Intent(getContext(), ArticuloInventoActivity.class);
										intent.putExtra("Invento", g[0]);
										startActivity(intent);
									}else{
										busquedaPrivadaInventosCargados(objetosCargados);
									}
								}
							}else if(g[0] instanceof Pintura){
								if(busqueda){
									busquedaPrivadaPinturasCargados(objetosCargados);
								}else if(busquedaDirecta){
									if(g.length == 1){
										Intent intent = new Intent(getContext(), ArticuloPinturaActivity.class);
										intent.putExtra("Pintura", g[0]);
										startActivity(intent);
									}else{
										busquedaPrivadaPinturasCargados(objetosCargados);
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
    }
	
}
