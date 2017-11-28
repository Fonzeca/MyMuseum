package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.DAO.ControlFTP;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Observers.IObserverInfo;
import com.a000webhostapp.mymuseum.Observers.ISujetoInfo;

import java.util.ArrayList;

/**
 * Created by Alexis on 27/11/2017.
 */

public class ModuloInformacion implements ISujetoInfo {
	private ArrayList<IObserverInfo> observers;
	private ArrayList<Request> requests;
	private static ModuloInformacion me;
	
	public static final int RQS_BUSQUEDA_INFO_TOTAL_INVENTOS = 2000;
	public static final int RQS_BUSQUEDA_INFO_TOTAL_INVENTORES = 2001;
	public static final int RQS_BUSQUEDA_INFO_TOTAL_PINTURAS = 2002;
	public static final int RQS_BUSQUEDA_INFO_TOTAL_PINTORES = 2003;
	public static final int RQS_BUSQUEDA_INFO_TOTAL_OBJETO = 2004;
	public static final int RQS_BUSQUEDA_INFO_TOTAL_TRASLADOS = 2005;
	public static final int RQS_BUSQUEDA_INFO_TOTAL_BUSQUEDAS = 2006;
	
	public static final int RQS_SUMA_DE_BUSQUEDA = 2101;
	public static final int RQS_BUSQUEDA_TOP_INVENTOS = 2102;
	public static final int RQS_BUSQUEDA_TOP_PINTURAS = 2103;
	
	
	
	private ModuloInformacion(){
		observers = new ArrayList<IObserverInfo>();
		requests = new ArrayList<Request>();
	}
	public static ModuloInformacion obtenerModulo(){
		if(me == null){
			me = new ModuloInformacion();
		}
		return me;
	}
	//------------------------
	public void obtenerTotalInventos(IObserverInfo observer){
		Request request = new Request(RQS_BUSQUEDA_INFO_TOTAL_INVENTOS);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerInfoCantidad(ControlDB.str_obj_Invento);
	}
	public void obtenerTopInventos(IObserverInfo observer, int cantidad){
		Request request = new Request(RQS_BUSQUEDA_TOP_INVENTOS);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerTopBusqueda(ControlDB.str_obj_Invento,cantidad);
	}
	//------------------------
	public void obtenerTotalInventores(IObserverInfo observer){
		Request request = new Request(RQS_BUSQUEDA_INFO_TOTAL_INVENTORES);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerInfoCantidad(ControlDB.str_per_Inventor);
	}
	//------------------------
	public void obtenerTotalPinturas(IObserverInfo observer){
		Request request = new Request(RQS_BUSQUEDA_INFO_TOTAL_PINTURAS);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerInfoCantidad(ControlDB.str_obj_Pintura);
	}
	public void obtenerTopPinturas(IObserverInfo observer, int cantidad){
		Request request = new Request(RQS_BUSQUEDA_TOP_PINTURAS);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerTopBusqueda(ControlDB.str_obj_Pintura,cantidad);
	}
	//------------------------
	public void obtenerTotalPintores(IObserverInfo observer){
		Request request = new Request(RQS_BUSQUEDA_INFO_TOTAL_PINTORES);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerInfoCantidad(ControlDB.str_per_Pintor);
	}
	//------------------------
	public void obtenerTotalObjetos(IObserverInfo observer){
		Request request = new Request(RQS_BUSQUEDA_INFO_TOTAL_OBJETO);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerInfoCantidad(ControlDB.str_objeto);
	}
	//------------------------
	public void sumar1Busqueda(int id){
		Request request = new Request(RQS_SUMA_DE_BUSQUEDA);
		new ControlDB(request).sumarBusqueda(id);
	}
	public void obtenerTotalBusquedas(IObserverInfo observer){
		Request request = new Request(RQS_BUSQUEDA_INFO_TOTAL_BUSQUEDAS);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerTotalBusquedas();
	}
	//------------------------
	public void obtenerTotalTraslados(IObserverInfo observer){
		Request request = new Request(RQS_BUSQUEDA_INFO_TOTAL_TRASLADOS);
		registrarObserver(observer,request);
		new ControlDB(request).obtenerInfoCantidad(ControlDB.str_traslado);
	}
	
	//------------------------
	public void registrarObserver(IObserverInfo ob, Request request) {
		observers.add(ob);
		requests.add(request);
	}
	
	public boolean eliminarObserver(IObserverInfo ob) {
		requests.remove(observers.indexOf(ob));
		observers.remove(ob);
		return true;
	}
	
	public void notificarObserver(Request request, String[] data, String respuesta) {
		for(int i = 0; i < requests.size(); i++){
			if(request.equals(requests.get(i))){
				observers.get(i).update(data,request.getId(),respuesta);
			}
		}
	}
	
	public void resultadoControlDB(Request request, String respuesta, String[] data){
		if(respuesta.equals(ControlDB.res_exito)) {
			switch (request.getId()){
				case RQS_BUSQUEDA_INFO_TOTAL_INVENTOS:
				case RQS_BUSQUEDA_INFO_TOTAL_INVENTORES:
				case RQS_BUSQUEDA_INFO_TOTAL_PINTURAS:
				case RQS_BUSQUEDA_INFO_TOTAL_PINTORES:
				case RQS_BUSQUEDA_INFO_TOTAL_OBJETO:
				case RQS_BUSQUEDA_TOP_INVENTOS:
				case RQS_BUSQUEDA_TOP_PINTURAS:
				case RQS_BUSQUEDA_INFO_TOTAL_TRASLADOS:
				case RQS_BUSQUEDA_INFO_TOTAL_BUSQUEDAS:
					notificarObserver(request,data,respuesta);
					break;
			}
		}else{
			notificarObserver(request,null,respuesta);
		}
	}
	
	
	
}
