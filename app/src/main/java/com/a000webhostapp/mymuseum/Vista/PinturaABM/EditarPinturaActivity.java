package com.a000webhostapp.mymuseum.Vista.PinturaABM;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Controlador.ModuloImagen;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.NuevoPeriodoActivity;
import com.a000webhostapp.mymuseum.Vista.PintorABM.NuevoPintorActivity;

public class EditarPinturaActivity extends AppCompatActivity implements IObserverEntidad {
	private static final int RQS_BUSCARIMAGEN = 0;
    private Pintor[] pintoresCargados;
    private Periodo[] periodosCargados;

    private Pintura pintura;
	
	private LinearLayout agregarPintor, agregarPeriodo;
	
    private TextView nombre, descripcion, año;
    private Spinner nombrePeriodoSpinner, nombrePintoresSpinner;
    private CheckBox ACPintura;
    private Button guardar;
	
	private ImageView imageView;
	private TextView imagenTextView;
	private Uri uriImagen;
	private Imagen imagen;
	private boolean imagenBuscada;
	
	private ModuloNotificacion notificacion;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pintura);
		notificacion = new ModuloNotificacion(this);
        

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pintura = (Pintura) getIntent().getSerializableExtra("Pintura");
        
		buscarInfoSpinners();
		
        nombre = (TextView) findViewById(R.id.editar_pintura_nombre_pintura);
        descripcion = (TextView) findViewById(R.id.editar_pintura_descripcion);
        año = (TextView) findViewById(R.id.editar_pintura_año_invencion);

        nombrePeriodoSpinner = (Spinner) findViewById(R.id.editar_pintura_periodo_spinner);
		
        nombrePintoresSpinner = (Spinner) findViewById(R.id.editar_pintura_nombre_pintor_spinner);
		

        ACPintura = (CheckBox) findViewById(R.id.ACPintura_EditPintura);
		
		agregarPintor = (LinearLayout) findViewById(R.id.AgregarPintor_EditPintura);
		agregarPintor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNuevoPintorActivity();
			}
		});
		agregarPeriodo = (LinearLayout)findViewById(R.id.AgregarPeriodo_EditPintura);
		agregarPeriodo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNuevoPeriodoActivity();
			}
		});

        guardar = (Button) findViewById(R.id.Save_EditPintura);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				//Modificamos todoo el pintura
				pintura.setNombre(nombre.getText().toString());
				pintura.setDescripcion(descripcion.getText().toString());
				int añoModifica;
				if(ACPintura.isChecked()){
					añoModifica = Integer.parseInt("-" + año.getText().toString());
				}else{
					añoModifica = Integer.parseInt(año.getText().toString());
				}
				pintura.setAñoInvencion(añoModifica);
				
				
				pintura.setPeriodo((Periodo)nombrePeriodoSpinner.getSelectedItem());
				pintura.setPintor((Pintor) nombrePintoresSpinner.getSelectedItem());
	
	
				ModuloEntidad.obtenerModulo().editarPintura(pintura,EditarPinturaActivity.this);
				if(uriImagen != null){
					ModuloImagen.obtenerModulo().insertarImagen(nombre.getText().toString(),ControlDB.str_obj_Pintura,EditarPinturaActivity.this,uriImagen,EditarPinturaActivity.this);
				}
				notificacion.mostrarLoading();
				guardar.setEnabled(false);
            }
        });
	
		imageView = (ImageView) findViewById(R.id.imagenView_EditarPintura);
	
		imagenTextView = (TextView)findViewById(R.id.buttonAgregarImagen_EditarPintura);
		imagenTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent,"Elegir imagen"),RQS_BUSCARIMAGEN);
			}
		});
        
        //seteamos los datos
        nombre.setText(pintura.getNombre());
        if(pintura.getAñoInvencion() < 0){
            año.setText(String.valueOf(Math.abs(pintura.getAñoInvencion())));
            ACPintura.setChecked(true);
        }else {
            año.setText(String.valueOf(pintura.getAñoInvencion()));
        }
        descripcion.setText(pintura.getDescripcion());
		
    }
	
	private void buscarInfoSpinners(){
		notificacion.mostrarLoading();
		
		ModuloEntidad.obtenerModulo().buscarPintores(this);
		ModuloEntidad.obtenerModulo().buscarPeriodos(this);
		
		if(!imagenBuscada){
			ModuloImagen.obtenerModulo().buscarImagen(pintura.getNombre(),ControlDB.str_obj_Pintura,this);
		}
		
	}
	private void actualizarSpinnerInventores(){
        int selected = 0;
        if(pintoresCargados != null){
            for (int i = 0; i < pintoresCargados.length; i++){
				//Buscamos cual es el inventor del pintura
				if(pintura.getPintor().getNombre().equals(pintoresCargados[i].getNombre())){
					selected = i;
                }
            }
        }

        ArrayAdapter<Pintor> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, pintoresCargados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nombrePintoresSpinner.setAdapter(adapter);
        nombrePintoresSpinner.setSelection(selected);

    }
    private void actualizarSpinnerPeriodo(){
        int selected = 0;
        if(periodosCargados != null){
            for (int i = 0; i < periodosCargados.length; i++){
				//Buscamos cual es el periodo del pintura
                if(pintura.getPeriodo().getNombrePeriodo().equals(periodosCargados[i].getNombrePeriodo())){
                    selected = i;
                }
            }
        }

        ArrayAdapter<Periodo> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, periodosCargados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nombrePeriodoSpinner.setAdapter(adapter);
        nombrePeriodoSpinner.setSelection(selected);
    }
	
	@Override
	protected void onRestart() {
		super.onRestart();
		buscarInfoSpinners();
	}
	
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == RQS_BUSCARIMAGEN){
			uriImagen = data.getData();
			imageView.setImageBitmap(Imagen.obtenerImagen(uriImagen,this).getBitmap());
			imageView.setAdjustViewBounds(true);
		}
		
	}
	
    public void update(Guardable[] g,int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch(respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request) {
							case ModuloEntidad.RQS_BUSQUEDA_PINTORES_TOTAL:
								if(g[0] instanceof Pintor){
									pintoresCargados = (Pintor[]) g;
									actualizarSpinnerInventores();
									
								}
								break;
							case ModuloEntidad.RQS_BUSQUEDA_PERIODOS_TOTAL:
								if(g[0] instanceof Periodo){
									periodosCargados = (Periodo[]) g;
									actualizarSpinnerPeriodo();
								}
								break;
							case ModuloImagen.RQS_BUSQUEDA_IMAGEN_UNICA:
							if(g[0] instanceof Imagen){
								imagen = (Imagen)g[0];
								imageView.setImageBitmap(imagen.getBitmap());
								imageView.setAdjustViewBounds(true);
							}
							imagenBuscada = true;
							break;
						}
						//Si todoo esta cargado, se ponen los booleanos como deben ser y se quita el loading
						if(periodosCargados != null && pintoresCargados != null && imagenBuscada){
							notificacion.loadingDismiss();
						}
					}else if(request == ModuloEntidad.RQS_MODIFICACION_PINTURA){
						notificacion.loadingDismiss();
						notificacion.mostarNotificacion("Se modifico exitosamente");
						runOnUiThread(new Runnable() {
							public void run() {
								onBackPressed();
							}
						});
					}
					break;
				default:
					notificacion.loadingDismiss();
					notificacion.mostrarError(respuesta);
					break;
			}
		}
    }
	private void startNuevoPintorActivity() {
		Intent intent = new Intent(this, NuevoPintorActivity.class);
		startActivity(intent);
	}
	private void startNuevoPeriodoActivity() {
		Intent intent = new Intent(this, NuevoPeriodoActivity.class);
		startActivity(intent);
	}
    
}
