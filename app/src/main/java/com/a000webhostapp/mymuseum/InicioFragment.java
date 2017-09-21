package com.a000webhostapp.mymuseum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a000webhostapp.mymuseum.Entidades.Invento;
import com.a000webhostapp.mymuseum.Entidades.Inventor;
import com.a000webhostapp.mymuseum.Entidades.ModuloEntidad;
import com.a000webhostapp.mymuseum.Entidades.Periodo;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment implements IObserver{
    private OnFragmentInteractionListener mListener;
    private  ArrayList<Invento> inventosArrayList;

    public InicioFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Llenar inventosArrayList con valores de la base de datos.
        inventosArrayList = new ArrayList<Invento>();

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
    private void actualizarLista(Invento[] g){
        Invento[] inventosArray = g;

        ArticuloInventoArrayAdapter articuloInventoArrayAdapter = new ArticuloInventoArrayAdapter(getContext(), inventosArray);

        ListView inventosRecientesList = (ListView) getView().findViewById(R.id.inventos_recientes_list);
        inventosRecientesList.setAdapter(articuloInventoArrayAdapter);
    }

    public void update(Guardable[]g, int id) {
        if(g != null){
            if(g[0] instanceof Invento){
                actualizarLista((Invento[])g);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
