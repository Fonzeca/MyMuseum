package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.R;

public class InicioFragment extends Fragment implements IObserver {
    private OnFragmentInteractionListener mListener;
	
	private ProgressDialog loading;
	private boolean buscando, cargado;

    public InicioFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
		loading = new ProgressDialog(getContext()){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
					buscando = false;
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setCancelable(false);
		loading.setMessage("Espere un momento...");
		loading.show();
		buscando = true;
		ModuloEntidad.obtenerModulo().buscarInventos(this);
    }
	
	/*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    */

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void actualizarLista(Invento[] inventos){
		Invento[] inventosOrdenados = new Invento[inventos.length];
		int i2 = inventos.length-1;
		for(int i = 0; i < inventos.length; i++){
			inventosOrdenados[i] = inventos[i2];
			i2--;
		}
		
        ArticuloInventoArrayAdapter articuloInventoArrayAdapter = new ArticuloInventoArrayAdapter(getContext(), inventosOrdenados);

        ListView inventosRecientesList = (ListView) getView().findViewById(R.id.inventos_recientes_list);
        inventosRecientesList.setAdapter(articuloInventoArrayAdapter);
    }

    public void update(Guardable[]g, int id) {
		if(buscando){
			if(g != null){
				if(g[0] instanceof Invento){
					actualizarLista((Invento[])g);
					cargado = true;
					buscando = false;
					loading.dismiss();
				}
			}else if(id == -1){
				loading.dismiss();
				//Creamos un alertDialog en el Thread UI del activity
				new DialogoAlerta(getActivity(),"No se pudo conectar", "Error").mostrar();
				buscando = false;
			}
			
		}
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
