package com.a000webhostapp.mymuseum;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AdminPanelFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private RelativeLayout agregarNuevoInventoButton, agregarNuevoInventorButton, agregarNuevoPeriodoButton;
    private View.OnClickListener clickListenerBotones;

    public AdminPanelFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickListenerBotones = new View.OnClickListener() {
            public void onClick(View view) {
                int id = view.getId();
                Intent intent;
                switch(id){
                    case R.id.agregar_nuevo_invento_button:
                        intent = new Intent(getActivity(), NuevoInventoActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.agregar_nuevo_inventor_button:
                        intent = new Intent(getActivity(), NuevoInventorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.agregar_nuevo_periodo_button:
                        intent = new Intent(getActivity(), NuevoPeriodoActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        };

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View viewFinal = inflater.inflate(R.layout.fragment_admin_panel, container, false);


        agregarNuevoInventoButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_invento_button);
        agregarNuevoInventoButton.setOnClickListener(clickListenerBotones);

        agregarNuevoInventorButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_inventor_button);
        agregarNuevoInventorButton.setOnClickListener(clickListenerBotones);

        agregarNuevoPeriodoButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_periodo_button);
        agregarNuevoPeriodoButton.setOnClickListener(clickListenerBotones);



        return viewFinal;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
