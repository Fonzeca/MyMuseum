package com.a000webhostapp.mymuseum.Vista.TrasladoABM;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.Modelo.Traslado;
import com.a000webhostapp.mymuseum.R;

/**
 * Created by Alexis on 25/10/2017.
 */

public class NuevoTrasladoActivity extends AppCompatActivity {
	
	private TextView destino, fecha;
	private Button save;
	private Pintura pintura;
	private Traslado trasladoAnterior;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generar_traslado);
		
		pintura = (Pintura) getIntent().getSerializableExtra("Pintura");
		trasladoAnterior = (Traslado) getIntent().getSerializableExtra("TrasladoAnterior");
		
		destino = (TextView)findViewById(R.id.textDestino_GenerarTraslado);
		fecha = (TextView)findViewById(R.id.textFechadetraslado_GenerarTraslado);
		
		save = (Button)findViewById(R.id.boton_Generar_GenerarTraslado);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String str_trasladoAnt;
				if(trasladoAnterior != null){
					str_trasladoAnt = trasladoAnterior.getLugarDestino();
				}else{
					str_trasladoAnt = "MyMuseum";
				}
				Traslado nuevoTraslado = new Traslado(pintura,str_trasladoAnt,destino.getText().toString(), fecha.getText().toString());
				Intent intent = new Intent();
				intent.putExtra("Traslado", nuevoTraslado);
				setResult(RESULT_OK,intent);
				finish();
			}
		});
		
		
		
		
	}
	
}
