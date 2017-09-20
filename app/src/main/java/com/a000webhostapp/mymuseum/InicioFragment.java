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
import com.a000webhostapp.mymuseum.Entidades.Periodo;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public InicioFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // Llenar inventosArrayList con valores de la base de datos.

        Inventor inventor = new Inventor("Alan Turing", "Maida Vale", 1912);
        Periodo periodo = new Periodo("Contemporáneo", 1789, 2017);

        Invento invento = new Invento("Uber", "Servicio de taxis particulares", periodo, inventor, 2011, false);
        Invento invento2 = new Invento("Lyft", "Servicio de taxis particulares", periodo, inventor, 2012, false);

        ArrayList<Invento> inventosArrayList = new ArrayList<Invento>();

        inventosArrayList.add(invento);
        inventosArrayList.add(invento2);

        Invento[] inventosArray = inventosArrayList.toArray(new Invento[inventosArrayList.size()]);

        ArticuloInventoArrayAdapter articuloInventoArrayAdapter = new ArticuloInventoArrayAdapter(getContext(), inventosArray);

        ListView inventosRecientesList = (ListView) getView().findViewById(R.id.inventos_recientes_list);
        inventosRecientesList.setAdapter(articuloInventoArrayAdapter);
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
