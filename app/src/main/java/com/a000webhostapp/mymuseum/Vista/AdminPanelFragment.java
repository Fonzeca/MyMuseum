package com.a000webhostapp.mymuseum.Vista;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.a000webhostapp.mymuseum.R;

public class AdminPanelFragment extends Fragment {
	
    private RelativeLayout agregarNuevoInventoButton, agregarNuevoInventorButton, agregarNuevoPeriodoButton;
	private RelativeLayout editarInventorButton, editarPeriodoButton;
	private RelativeLayout eliminarInventoButton,eliminarInventorButton, eliminarPeriodoButton;
	
	
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
                    case R.id.editar_inventor_button:
                        intent = new Intent(getActivity(), EditarInventorActivity.class);
                        startActivity(intent);
                        break;
					case R.id.editar_periodo_button:
						intent = new Intent(getActivity(), EditarPeriodoActivity.class);
						startActivity(intent);
						break;
					case R.id.eliminar_invento_button:
						intent = new Intent(getActivity(), EliminarInventoActivity.class);
						startActivity(intent);
						break;
					case R.id.eliminar_inventor_button:
						intent = new Intent(getActivity(), EliminarInventorActivity.class);
						startActivity(intent);
						break;
					case R.id.eliminar_periodo_button:
						intent = new Intent(getActivity(), EliminarPeriodoActivity.class);
						startActivity(intent);
						break;
                }

            }
        };

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_panel, container, false);
    }
	
	public void onViewCreated(View viewFinal, Bundle savedInstanceState) {
		
		agregarNuevoInventoButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_invento_button);
		agregarNuevoInventoButton.setOnClickListener(clickListenerBotones);
		
		agregarNuevoInventorButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_inventor_button);
		agregarNuevoInventorButton.setOnClickListener(clickListenerBotones);
		
		agregarNuevoPeriodoButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_periodo_button);
		agregarNuevoPeriodoButton.setOnClickListener(clickListenerBotones);
		
		editarInventorButton = (RelativeLayout) viewFinal.findViewById(R.id.editar_inventor_button);
		editarInventorButton.setOnClickListener(clickListenerBotones);
		
		editarPeriodoButton = (RelativeLayout) viewFinal.findViewById(R.id.editar_periodo_button);
		editarPeriodoButton.setOnClickListener(clickListenerBotones);
		
		eliminarInventoButton = (RelativeLayout) viewFinal.findViewById(R.id.eliminar_invento_button);
		eliminarInventoButton.setOnClickListener(clickListenerBotones);
		
		eliminarInventorButton = (RelativeLayout) viewFinal.findViewById(R.id.eliminar_inventor_button);
		eliminarInventorButton.setOnClickListener(clickListenerBotones);
		
		eliminarPeriodoButton = (RelativeLayout) viewFinal.findViewById(R.id.eliminar_periodo_button);
		eliminarPeriodoButton.setOnClickListener(clickListenerBotones);
		
	}
}
