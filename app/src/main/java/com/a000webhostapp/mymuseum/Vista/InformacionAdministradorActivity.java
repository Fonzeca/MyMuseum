package com.a000webhostapp.mymuseum.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Controlador.ModuloInformacion;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverInfo;
import com.a000webhostapp.mymuseum.R;

public class InformacionAdministradorActivity extends AppCompatActivity implements IObserverInfo {
    
    private TextView viewClave;
	private TextView totalObjetos, totalPinturas, totalInventos, totalInventores, totalPintores;
	private TextView totalBusquedas;
	private TextView[] inventosMasBuscados;
	private TextView[] pinturasMasBuscadas;
	
	private TextView totalTraslados;
	private TextView inventorDestacado;
	
	private ModuloNotificacion notificacion;
	private int contaRequest = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_administrador);
	
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
        
		
        notificacion = new ModuloNotificacion(this);
        
        viewClave = (TextView) findViewById(R.id.view_clave_info_general);
	
		totalObjetos = (TextView) findViewById(R.id.total_objetos_info_general);
		totalPinturas = (TextView) findViewById(R.id.total_pinturas_info_general);
		totalInventos = (TextView) findViewById(R.id.total_inventos_info_general);
		totalInventores = (TextView) findViewById(R.id.total_inventores_info_general);
		totalPintores = (TextView) findViewById(R.id.total_pintores_info_general);
	
		totalBusquedas = (TextView) findViewById(R.id.busquedas_realizadas_info_general);
	
		inventosMasBuscados = new TextView[3];
		inventosMasBuscados[0] = (TextView) findViewById(R.id.inventos_mas_buscados_1_info_general);
		inventosMasBuscados[1] = (TextView) findViewById(R.id.inventos_mas_buscados_2_info_general);
		inventosMasBuscados[2] = (TextView) findViewById(R.id.inventos_mas_buscados_3_info_general);
	
		pinturasMasBuscadas = new TextView[3];
		pinturasMasBuscadas[0] = (TextView) findViewById(R.id.pinturas_mas_buscadas_1_info_general);
		pinturasMasBuscadas[1] = (TextView) findViewById(R.id.pinturas_mas_buscadas_2_info_general);
		pinturasMasBuscadas[2] = (TextView) findViewById(R.id.pinturas_mas_buscadas_3_info_general);
	
		totalTraslados = (TextView) findViewById(R.id.total_traslados_info_general);
		inventorDestacado = (TextView) findViewById(R.id.inventor_destacado_info_general);
  
		buscarInfo();
		
		
    }
    private void buscarInfo(){
    	notificacion.mostrarLoading();
    	ModuloInformacion mo = ModuloInformacion.obtenerModulo();
		mo.obtenerTotalInventos(this);
		contaRequest++;
		mo.obtenerTotalInventores(this);
		contaRequest++;
		mo.obtenerTotalPinturas(this);
		contaRequest++;
		mo.obtenerTotalPintores(this);
		contaRequest++;
		mo.obtenerTotalObjetos(this);
		contaRequest++;
		mo.obtenerTopInventos(this,3);
		contaRequest++;
		mo.obtenerTopPinturas(this,3);
		contaRequest++;
		mo.obtenerTotalTraslados(this);
		contaRequest++;
		mo.obtenerTotalBusquedas(this);
		contaRequest++;
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	private void insertarListaTop(String[] data,int reqest){
		for(int i = 0; i < data.length; i++){
			if(i == 3)break;
			String[] partes = data[i].split("=");
			String mostrar = partes[0] + " con " + partes[1] + " busquedas";
			if(reqest==ModuloInformacion.RQS_BUSQUEDA_TOP_INVENTOS){
				inventosMasBuscados[i].setText(mostrar);
			}else if(reqest==ModuloInformacion.RQS_BUSQUEDA_TOP_PINTURAS){
				pinturasMasBuscadas[i].setText(mostrar);
			}
		}
 	
	}
	
	public void update(String[] data, int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					switch (request){
						case ModuloInformacion.RQS_BUSQUEDA_INFO_TOTAL_INVENTOS:
							totalInventos.setText(data[0]);
							contaRequest--;
							break;
						case ModuloInformacion.RQS_BUSQUEDA_INFO_TOTAL_INVENTORES:
							totalInventores.setText(data[0]);
							contaRequest--;
							break;
						case ModuloInformacion.RQS_BUSQUEDA_INFO_TOTAL_PINTURAS:
							totalPinturas.setText(data[0]);
							contaRequest--;
							break;
						case ModuloInformacion.RQS_BUSQUEDA_INFO_TOTAL_PINTORES:
							totalPintores.setText(data[0]);
							contaRequest--;
							break;
						case ModuloInformacion.RQS_BUSQUEDA_INFO_TOTAL_OBJETO:
							totalObjetos.setText(data[0]);
							contaRequest--;
							break;
						case ModuloInformacion.RQS_BUSQUEDA_TOP_INVENTOS:
						case ModuloInformacion.RQS_BUSQUEDA_TOP_PINTURAS:
							insertarListaTop(data, request);
							contaRequest--;
							break;
						case ModuloInformacion.RQS_BUSQUEDA_INFO_TOTAL_TRASLADOS:
							totalTraslados.setText(data[0]);
							contaRequest--;
							break;
						case ModuloInformacion.RQS_BUSQUEDA_INFO_TOTAL_BUSQUEDAS:
							totalBusquedas.setText(data[0]);
							contaRequest--;
							break;
					}
					if(contaRequest == 0){
						notificacion.loadingDismiss();
					}
					break;
				default:
					notificacion.loadingDismiss();
					notificacion.mostrarError(respuesta);
					break;
			}
			
		}
	}
}
