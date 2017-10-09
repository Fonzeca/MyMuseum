package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.R;

public class InicioFragment extends Fragment implements IObserver, SwipeRefreshLayout.OnRefreshListener {
	private Invento[] inventosCargados;
	
	private ProgressDialog loading;
	private SwipeRefreshLayout swipe;
	private boolean cargado;

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

    public void update(Guardable[]g, int id) {
		if(loading.isShowing() || swipe.isRefreshing()){
			if(g != null){
				if(g[0] instanceof Invento){
					inventosCargados = (Invento[])g;
					actualizarLista();
					cargado = true;
					loading.dismiss();
					swipe.setRefreshing(false);
				}
			}else if(id == -1){
				loading.dismiss();
				swipe.setRefreshing(false);
				//Creamos un alertDialog en el Thread UI del activity
				new DialogoAlerta(getActivity(),"No se pudo conectar", "Error").mostrar();
			}
		}
    }
	
}
