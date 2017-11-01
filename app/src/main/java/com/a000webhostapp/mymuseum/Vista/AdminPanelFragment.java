package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.EditarPeriodoActivity;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.EliminarPeriodoActivity;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.NuevoPeriodoActivity;
import com.a000webhostapp.mymuseum.Vista.TrasladoABM.NuevoTrasladoActivity;

public class AdminPanelFragment extends Fragment {
	
    private RelativeLayout agregarNuevoInventoButton, agregarNuevoInventorButton, agregarNuevoPeriodoButton;
	private RelativeLayout editarInventorButton, editarPeriodoButton, editarObjetoButton;
	private RelativeLayout eliminarInventoButton,eliminarInventorButton, eliminarPeriodoButton;
	private RelativeLayout generarTrasladoButton,verHistorialButton;
	
	
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
                    case R.id.agregar_nuevo_objeto_button:
                        intent = new Intent(getActivity(), ElegirNuevoObjetoActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.agregar_nuevo_persona_button:
                        intent = new Intent(getActivity(), ElegirNuevaPersonaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.agregar_nuevo_periodo_button:
                        intent = new Intent(getActivity(), NuevoPeriodoActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.editar_persona_button:
                        intent = new Intent(getActivity(), ElegirEditarPersonaActivity.class);
                        startActivity(intent);
                        break;
					case R.id.editar_periodo_button:
						intent = new Intent(getActivity(), EditarPeriodoActivity.class);
						startActivity(intent);
						break;
					case R.id.editar_objeto_button:
						break;
					case R.id.eliminar_objeto_button:
						intent = new Intent(getActivity(), ElegirEliminarObjetoActivity.class);
						startActivity(intent);
						break;
					case R.id.eliminar_persona_button:
						intent = new Intent(getActivity(), ElegirEliminarPersonaActivity.class);
						startActivity(intent);
						break;
					case R.id.eliminar_periodo_button:
						intent = new Intent(getActivity(), EliminarPeriodoActivity.class);
						startActivity(intent);
						break;
					case R.id.generar_traslado_button:
						intent = new Intent(getActivity(), NuevoTrasladoActivity.class);
						startActivity(intent);
						break;
					case R.id.ver_historial_button:
						intent = new Intent(getActivity(), HistorialPinturaActivity.class);
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
		
		agregarNuevoInventoButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_objeto_button);
		agregarNuevoInventoButton.setOnClickListener(clickListenerBotones);
		
		agregarNuevoInventorButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_persona_button);
		agregarNuevoInventorButton.setOnClickListener(clickListenerBotones);
		
		agregarNuevoPeriodoButton = (RelativeLayout) viewFinal.findViewById(R.id.agregar_nuevo_periodo_button);
		agregarNuevoPeriodoButton.setOnClickListener(clickListenerBotones);
		
		editarInventorButton = (RelativeLayout) viewFinal.findViewById(R.id.editar_persona_button);
		editarInventorButton.setOnClickListener(clickListenerBotones);
		
		editarPeriodoButton = (RelativeLayout) viewFinal.findViewById(R.id.editar_periodo_button);
		editarPeriodoButton.setOnClickListener(clickListenerBotones);
		
		editarObjetoButton = (RelativeLayout) viewFinal.findViewById(R.id.editar_objeto_button);
		editarObjetoButton.setOnClickListener(clickListenerBotones);
		
		eliminarInventoButton = (RelativeLayout) viewFinal.findViewById(R.id.eliminar_objeto_button);
		eliminarInventoButton.setOnClickListener(clickListenerBotones);
		
		eliminarInventorButton = (RelativeLayout) viewFinal.findViewById(R.id.eliminar_persona_button);
		eliminarInventorButton.setOnClickListener(clickListenerBotones);
		
		eliminarPeriodoButton = (RelativeLayout) viewFinal.findViewById(R.id.eliminar_periodo_button);
		eliminarPeriodoButton.setOnClickListener(clickListenerBotones);
		
		generarTrasladoButton = (RelativeLayout) viewFinal.findViewById(R.id.generar_traslado_button);
		generarTrasladoButton.setOnClickListener(clickListenerBotones);
		
		verHistorialButton = (RelativeLayout) viewFinal.findViewById(R.id.ver_historial_button);
		verHistorialButton.setOnClickListener(clickListenerBotones);
		
	}
}
