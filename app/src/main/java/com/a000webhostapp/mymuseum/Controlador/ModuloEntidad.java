package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.ISujetoEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.Modelo.Traslado;

import java.text.Collator;
import java.util.ArrayList;


/**
 * Created by Alexis on 19/9/2017.
 * ESTA CLASE ES PARA MANEJAR LAS ENTIDADES Y MANDARLAS A LA BD(BASE DE DATOS)
 */

public class ModuloEntidad implements ISujetoEntidad {
	private ArrayList<IObserverEntidad> observers;
	private ArrayList<Request> requests;
    private static ModuloEntidad me;
    
    public static final int RQS_BUSQUEDA_INVENTORES_TOTAL = 0;
	public static final int RQS_BUSQUEDA_PERIODOS_TOTAL = 1;
	public static final int RQS_BUSQUEDA_PINTORES_TOTAL = 2;
	public static final int RQS_BUSQUEDA_INVENTOS_TOTAL = 3;
	public static final int RQS_BUSQUEDA_PINTURAS_TOTAL = 4;
	public static final int RQS_BUSQUEDA_INVENTOS_REFINADO = 5;
	public static final int RQS_BUSQUEDA_PINTURAS_REFINADO = 6;
	public static final int RQS_BUSQUEDA_INVENTO_DIRECTA = 7;
	public static final int RQS_BUSQUEDA_PINTURA_DIRECTA = 8;
	public static final int RQS_BUSQUEDA_OBJETO_TOTAL = 9;
	public static final int RQS_BUSQUEDA_TRASLADOS_TOTAL = 10;
	public static final int RQS_BUSQUEDA_TRASLADOS_UNICO_PINTURA = 11;
	
	public static final int RQS_ALTA_INVENTOR = 100;
	public static final int RQS_ALTA_PINTOR = 101;
	public static final int RQS_ALTA_PERIODO = 102;
	public static final int RQS_ALTA_INVENTO = 103;
	public static final int RQS_ALTA_PINTURA = 104;
	public static final int RQS_ALTA_TRASLADO = 105;
	
	public static final int RQS_MODIFICACION_INVENTOR = 200;
	public static final int RQS_MODIFICACION_PINTOR = 201;
	public static final int RQS_MODIFICACION_PERIODO = 202;
	public static final int RQS_MODIFICACION_INVENTO = 203;
	public static final int RQS_MODIFICACION_PINTURA = 204;
	
	public static final int RQS_BAJA_INVENTOR = 300;
	public static final int RQS_BAJA_PINTOR = 301;
	public static final int RQS_BAJA_PERIODO = 302;
	public static final int RQS_BAJA_INVENTO = 303;
	public static final int RQS_BAJA_PINTURA = 304;
	
	

    private ModuloEntidad(){
		observers = new ArrayList<IObserverEntidad>();
		requests = new ArrayList<Request>();
    }
    public static ModuloEntidad obtenerModulo(){

        if(me == null){
            me = new ModuloEntidad();
        }
        return me;
    }
    
    //---------------
    public void crearInvento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina, IObserverEntidad ob){
        Invento invento = new Invento(nombre,descripcion,periodo,inventor,añoInvencion,isMaquina);
        Request request = new Request(RQS_ALTA_INVENTO);
        registrarObserver(ob,request);
        new ControlDB(request).insertar(invento);
    }
    public void buscarInventos(IObserverEntidad observer){
        //Mandamos a buscar los Inventos
		Request request = new Request(RQS_BUSQUEDA_INVENTOS_TOTAL);
		registrarObserver(observer,request);
		new ControlDB(request).buscar(ControlDB.str_obj_Invento);
    }
	public void buscarInventosRefinada(IObserverEntidad observer, RequestBusqueda req){
		Request request = req;
		registrarObserver(observer,request);
		new ControlDB(request).buscar(ControlDB.str_obj_Invento);
	}
	public void buscarInventoDirecto(IObserverEntidad observer, String nombre){
		//Mandamos a buscar los Inventos
		Request request = new Request(RQS_BUSQUEDA_INVENTO_DIRECTA);
		registrarObserver(observer,request);
		new ControlDB(request).buscarDirecto(ControlDB.str_obj_Invento,nombre);
	}
    public void editarInvento(Guardable g, IObserverEntidad observer){
		Request request = new Request(RQS_MODIFICACION_INVENTO);
		registrarObserver(observer,request);
        new ControlDB(request).modificar(g);
    }
	public void eliminarInvento(int id, IObserverEntidad observer){
		String entidad = "entidad=" + ControlDB.str_objeto;
		String idBorra = "registro_id=" + id;
		Request request = new Request(RQS_BAJA_INVENTO);
		registrarObserver(observer,request);
		
		new ControlDB(request).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
    //---------------
	public void crearPintura(String nombre, String descripcion, Periodo periodo, Pintor pintor, int añoInvencion,IObserverEntidad observer){
		Pintura pintura = new Pintura(nombre,descripcion,periodo,pintor,añoInvencion);
		Request request = new Request(RQS_ALTA_PINTURA);
		registrarObserver(observer,request);
		new ControlDB(request).insertar(pintura);
	}
	public void buscarPinturas(IObserverEntidad observer){
		Request request = new Request(RQS_BUSQUEDA_PINTURAS_TOTAL);
		registrarObserver(observer,request);
		new ControlDB(request).buscar(ControlDB.str_obj_Pintura);
	}
	public void buscarPinturasRefinada(IObserverEntidad observer, RequestBusqueda req){
		Request request = req;
		registrarObserver(observer,request);
		new ControlDB(request).buscar(ControlDB.str_obj_Pintura);
	}
	public void buscarPinturaDirecto(IObserverEntidad observer, String nombre){
		Request request = new Request(RQS_BUSQUEDA_PINTURA_DIRECTA);
		registrarObserver(observer,request);
		new ControlDB(request).buscarDirecto(ControlDB.str_obj_Pintura,nombre);
	}
	public void eliminarPintura(int id,IObserverEntidad observer){
		String entidad = "entidad="+ControlDB.str_objeto;
		String idBorra = "registro_id=" + id;
		Request request = new Request(RQS_BAJA_PINTURA);
		registrarObserver(observer,request);
		new ControlDB(request).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
	public void editarPintura(Guardable g,IObserverEntidad observer){
		Request request = new Request(RQS_MODIFICACION_PINTURA);
		registrarObserver(observer,request);
		new ControlDB(request).modificar(g);
	}
    //---------------
	public void crearPintor(String nombre, String lugarNacimiento, int añoNacimiento,IObserverEntidad observer){
		Pintor pintor = new Pintor(nombre,lugarNacimiento,añoNacimiento);
		Request request = new Request(RQS_ALTA_PINTOR);
		registrarObserver(observer,request);
		new ControlDB(request).insertar(pintor);
	}
	public void buscarPintores(IObserverEntidad observer){
		Request request = new Request(RQS_BUSQUEDA_PINTORES_TOTAL);
		registrarObserver(observer,request);
		new ControlDB(request).buscar(ControlDB.str_per_Pintor);
	}
	public void eliminarPintor(int id,IObserverEntidad observer){
		String entidad = "entidad="+ControlDB.str_persona;
		String idBorra = "registro_id=" + id;
		Request request = new Request(RQS_BAJA_PINTOR);
		registrarObserver(observer,request);
		
		new ControlDB(request).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
	public void editarPintor(Guardable g,IObserverEntidad observer){
		Request request = new Request(RQS_MODIFICACION_PINTOR);
		registrarObserver(observer,request);
		new ControlDB(request).modificar(g);
	}
	
	//---------------
    public void crearInventor(String nombrecompleto, String lugarNacimiento, int añoNacimiento,IObserverEntidad observer){
        Inventor inventor = new Inventor(nombrecompleto,lugarNacimiento,añoNacimiento);
		Request request = new Request(RQS_ALTA_INVENTOR);
		registrarObserver(observer,request);
        new ControlDB(request).insertar(inventor);
    }
    public void buscarInventores(IObserverEntidad observer){
        //mandamos a buscar los inventores
		Request request = new Request(RQS_BUSQUEDA_INVENTORES_TOTAL);
		registrarObserver(observer,request);
        new ControlDB(request).buscar("Inventor");
    }
    public void editarInventor(Guardable g,IObserverEntidad observer){
		Request request = new Request(RQS_MODIFICACION_INVENTOR);
		registrarObserver(observer,request);
        new ControlDB(request).modificar(g);
    }
	public void eliminarInventor(int id,IObserverEntidad observer){
		String entidad = "entidad=" + ControlDB.str_persona;
		String idBorra = "registro_id=" + id;
		Request request = new Request(RQS_BAJA_INVENTOR);
		registrarObserver(observer,request);
		new ControlDB(request).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
    //---------------
    public void crearPeriodo(String nombre, int añoInicio, int añoFin,IObserverEntidad observer){
        Periodo peri = new Periodo(nombre, añoInicio,añoFin);
		Request request = new Request(RQS_ALTA_PERIODO);
		registrarObserver(observer,request);
        new ControlDB(request).insertar(peri);

    }
    public void buscarPeriodos(IObserverEntidad observer){
        //mandamos a buscar los periodos
		Request request = new Request(RQS_BUSQUEDA_PERIODOS_TOTAL);
		registrarObserver(observer,request);
        new ControlDB(request).buscar("Periodo");
    }
    public void editarPeriodo(Guardable g,IObserverEntidad observer){
		Request request = new Request(RQS_MODIFICACION_PERIODO);
		registrarObserver(observer,request);
        new ControlDB(request).modificar(g);
    }
    public void eliminarPeriodo(int id,IObserverEntidad observer){
		String entidad = "entidad=Periodo";
		String idBorra = "registro_id=" + id;
		Request request = new Request(RQS_BAJA_PERIODO);
		registrarObserver(observer,request);
		new ControlDB(request).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
	//---------------
	public void crearTraslado(String nombrePintura,int idPintura, String lugarOrigen, String lugarDestino, String fechaTraslado, IObserverEntidad observer){
		Traslado nuevoTraslado = new Traslado(nombrePintura,idPintura,lugarOrigen,lugarDestino,fechaTraslado);
		Request request = new Request(RQS_ALTA_TRASLADO);
		registrarObserver(observer,request);
		new ControlDB(request).insertar(nuevoTraslado);
	}
	public void buscarTraslados(IObserverEntidad observer, Pintura p){
		Request request = new RequestHistorialPintura(RQS_BUSQUEDA_TRASLADOS_UNICO_PINTURA, p);
		registrarObserver(observer,request);
		new ControlDB(request).buscarTrasladosIDPintura();
	}
	public void buscarTrasladosTOTAL(IObserverEntidad observer){
		Request request = new Request(RQS_BUSQUEDA_TRASLADOS_TOTAL);
		registrarObserver(observer,request);
		new ControlDB(request).buscarTraslados();
	}
	//---------------
	public void buscarObjetos(IObserverEntidad observer){
		Request request = new Request(RQS_BUSQUEDA_OBJETO_TOTAL);
		registrarObserver(observer,request);
		new ControlDB(request).buscar(ControlDB.str_objeto);
	}
	//---------------
	private Objeto[] busquedaPrivadaObjetos(Guardable[] g, Request request){
		ArrayList<Objeto> match = new ArrayList<>();
		Collator comparador = Collator.getInstance();
		ArrayList<Objeto> inventosCargados = new ArrayList<>();
		RequestBusqueda requestBusqueda = null;
		comparador.setStrength(Collator.PRIMARY);
		
		
		for(Guardable ob : g){
			if(ob instanceof Objeto){
				inventosCargados.add((Objeto)ob);
			}
		}
		if(request instanceof RequestBusqueda){
			requestBusqueda = (RequestBusqueda) request;
		}
		
		if(requestBusqueda != null){
			String nombreABuscar = requestBusqueda.getNombre();
			for (Objeto in: inventosCargados) {
				for (int i = 0; i <= in.getNombre().length() - nombreABuscar.length(); i++){
					if(comparador.compare(in.getNombre().substring(i,i+nombreABuscar.length()), nombreABuscar) == 0){
						if(requestBusqueda.getPeriodo() == null || requestBusqueda.getPeriodo().getNombrePeriodo().equals(in.getPeriodo().getNombrePeriodo())){
							if(in instanceof Invento){
								if(requestBusqueda.getInventor() == null || ((Invento)in).getInventor().getNombre().equals(requestBusqueda.getInventor().getNombre())){
									match.add(in);
								}
							}else if(in instanceof Pintura){
								if(requestBusqueda.getPintor() == null || ((Pintura)in).getPintor().getNombre().equals(requestBusqueda.getPintor().getNombre())){
									match.add(in);
								}
							}
						}
						break;
					}
				}
			}
		}
		
		if(match.size() == 0){
			return null;
		}else{
			return match.toArray(new Objeto[match.size()]);
		}
	}
	private void insertarTrasladosEnPintura(Request request, Guardable[] guardables){
		Pintura pintura = null;
		Traslado[] traslados = null;
		if(request instanceof RequestHistorialPintura && guardables instanceof Traslado[]){
			pintura = ((RequestHistorialPintura)request).getPintura();
			traslados = (Traslado[]) guardables;
		}else{
			//ERROR ACA
			return;
		}
		
		//SE BOORA TODO!!
		pintura.borrarTodoTraslado();
		
		for (Traslado t : traslados){
			pintura.agregarTraslado(t);
		}
	}
	//---------------
	
	public void registrarObserver(IObserverEntidad ob, Request request) {
		observers.add(ob);
		requests.add(request);
	}
	
	public boolean eliminarObserver(IObserverEntidad ob) {
		requests.remove(observers.indexOf(ob));
		observers.remove(ob);
		return true;
	}
	
	public void notificarObserver(Request request,Guardable[] g, String respuesta) {
		for(int i = 0; i < requests.size(); i++){
			if(request.equals(requests.get(i))){
				observers.get(i).update(g,request.getId(),respuesta);
			}
		}
	}
	
	public void resultadoControlDB(Request request, String respuesta, Guardable[] g){
		if(respuesta.equals(ControlDB.res_exito)) {
			switch (request.getId()) {
				case RQS_BUSQUEDA_OBJETO_TOTAL:
				case RQS_BUSQUEDA_INVENTORES_TOTAL:
				case RQS_BUSQUEDA_PERIODOS_TOTAL:
				case RQS_BUSQUEDA_PINTORES_TOTAL:
				case RQS_BUSQUEDA_INVENTOS_TOTAL:
				case RQS_BUSQUEDA_PINTURAS_TOTAL:
				case RQS_BUSQUEDA_TRASLADOS_TOTAL:
					notificarObserver(request,g,respuesta);
					break;
				case RQS_BUSQUEDA_INVENTOS_REFINADO:
				case RQS_BUSQUEDA_PINTURAS_REFINADO:
					Objeto[] resultado = busquedaPrivadaObjetos(g,request);
					if(resultado == null){
						notificarObserver(request,resultado,ControlDB.res_busquedaFallida);
					}else{
						notificarObserver(request,resultado,respuesta);
					}
					break;
				case RQS_BUSQUEDA_INVENTO_DIRECTA:
				case RQS_BUSQUEDA_PINTURA_DIRECTA:
					notificarObserver(request,g,respuesta);
					break;
				case RQS_BUSQUEDA_TRASLADOS_UNICO_PINTURA:
					insertarTrasladosEnPintura(request,g);
					notificarObserver(request,g,respuesta);
					break;
				case RQS_ALTA_INVENTO:
				case RQS_ALTA_INVENTOR:
				case RQS_ALTA_PERIODO:
				case RQS_ALTA_PINTOR:
				case RQS_ALTA_PINTURA:
				case RQS_ALTA_TRASLADO:
				case RQS_BAJA_INVENTO:
				case RQS_BAJA_INVENTOR:
				case RQS_BAJA_PERIODO:
				case RQS_BAJA_PINTOR:
				case RQS_BAJA_PINTURA:
				case RQS_MODIFICACION_INVENTO:
				case RQS_MODIFICACION_INVENTOR:
				case RQS_MODIFICACION_PERIODO:
				case RQS_MODIFICACION_PINTOR:
				case RQS_MODIFICACION_PINTURA:
					notificarObserver(request,null,respuesta);
					break;
			}
		}else{
			notificarObserver(request,null,respuesta);
		}
	}
	
}
