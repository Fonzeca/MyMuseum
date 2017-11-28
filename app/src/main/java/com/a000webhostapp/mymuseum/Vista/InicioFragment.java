package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Controlador.RequestBusqueda;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.R;

public class InicioFragment extends Fragment implements IObserverEntidad, SwipeRefreshLayout.OnRefreshListener {
	private Objeto[] objetosCargados;
	
	private ModuloNotificacion notificacion;
	private SwipeRefreshLayout swipe;
	private TextView titulo;
	private String nombreABuscar;
	
	public InicioFragment() {
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		notificacion = new ModuloNotificacion(getActivity());
	}

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inicio, container, false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipeActualizar_InicioFragment);
		swipe.setOnRefreshListener(this);
		
		titulo = (TextView)view.findViewById(R.id.titulo_inicio_fragment);
		buscarObjetos();
		actualizarLista();
	}
	private void reiniciarTitulo(){
		titulo.setText("Objetos agregados recientemente:");
	}
	
	
	@Override
	public void onRefresh() {
		reiniciarTitulo();
		ModuloEntidad.obtenerModulo().buscarObjetos(this);
	}
	private void buscarObjetos(){
		notificacion.mostrarLoading();
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
			
			final ArticuloInventoArrayAdapter articuloInventoArrayAdapter = new ArticuloInventoArrayAdapter(getContext(), objetosOrdenados);
			
			final ListView inventosRecientesList = (ListView) getView().findViewById(R.id.inventos_recientes_list);
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					inventosRecientesList.setAdapter(articuloInventoArrayAdapter);
				}
			});
			
		}
	}
	
	public void busquedaRefinada(RequestBusqueda req){
		notificacion.mostrarLoading();
		if(req.getId() == ModuloEntidad.RQS_BUSQUEDA_INVENTOS_REFINADO){
			ModuloEntidad.obtenerModulo().buscarInventosRefinada(this,req);
		}else if(req.getId() == ModuloEntidad.RQS_BUSQUEDA_PINTURAS_REFINADO){
			ModuloEntidad.obtenerModulo().buscarPinturasRefinada(this,req);
		}
		
	}
	public void busquedaPinturas(RequestBusqueda req){
		notificacion.mostrarLoading();
		
	}
	public void busquedaObjetoDirecto(String nombre, String entidad){
		notificacion.mostrarLoading();
		switch (entidad){
			case ControlDB.str_obj_Invento:
				ModuloEntidad.obtenerModulo().buscarInventoDirecto(this,nombre);
				break;
			case ControlDB.str_obj_Pintura:
				ModuloEntidad.obtenerModulo().buscarPinturaDirecto(this,nombre);
				break;
		}
	}
	
	public void update(Guardable[] g,int request, String respuesta){
		if(notificacion.isLoadingShowing() || swipe.isRefreshing()){
			switch(respuesta){
				case ControlDB.res_exito:
					if(g != null){
						Intent intent;
						switch (request){
							case ModuloEntidad.RQS_BUSQUEDA_INVENTOS_REFINADO:
							case ModuloEntidad.RQS_BUSQUEDA_PINTURAS_REFINADO:
								titulo.setText("Objetos buscados:");
							case ModuloEntidad.RQS_BUSQUEDA_OBJETO_TOTAL:
								objetosCargados = (Objeto[])g;
								break;
							case ModuloEntidad.RQS_BUSQUEDA_INVENTO_DIRECTA:
								intent = new Intent(getContext(), ArticuloObjetoActivity.class);
								intent.putExtra("TipoObjeto", ControlDB.str_obj_Invento);
								intent.putExtra(ControlDB.str_obj_Invento, g[0]);
								startActivity(intent);
								break;
							case ModuloEntidad.RQS_BUSQUEDA_PINTURA_DIRECTA:
								intent = new Intent(getContext(), ArticuloObjetoActivity.class);
								intent.putExtra("TipoObjeto", ControlDB.str_obj_Pintura);
								intent.putExtra(ControlDB.str_obj_Pintura, g[0]);
								startActivity(intent);
								break;
						}
						notificacion.loadingDismiss();
						swipe.setRefreshing(false);
					}
					break;
				case ControlDB.res_busquedaFallida:
					notificacion.loadingDismiss();
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							swipe.setRefreshing(false);
						}
					});
					notificacion.mostarNotificacion(respuesta);
					break;
				case ControlDB.res_falloConexion:
				case ControlDB.res_tablaInventoVacio:
					notificacion.loadingDismiss();
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							swipe.setRefreshing(false);
						}
					});
					notificacion.mostrarError(respuesta);
					break;
					
			}
			actualizarLista();
		}
	}
	
}
