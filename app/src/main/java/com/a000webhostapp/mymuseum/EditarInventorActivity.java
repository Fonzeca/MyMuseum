package com.a000webhostapp.mymuseum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Entidades.Invento;
import com.a000webhostapp.mymuseum.Entidades.Inventor;
import com.a000webhostapp.mymuseum.Entidades.ModuloEntidad;

public class EditarInventorActivity extends AppCompatActivity {
    private EditText nombre, año, lugar;
    private CheckBox AC;
    private Button guardar;

    private Inventor inventor;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_inventor);
		
		inventor = (Inventor) getIntent().getSerializableExtra("Inventor");
		
		
		nombre = (EditText) findViewById(R.id.nomyapeInventor_EditInventor);
        año = (EditText) findViewById(R.id.añoNacInventor_EditInventor);
        lugar = (EditText) findViewById(R.id.lugarNacInventor_EditInventor);

        AC = (CheckBox) findViewById(R.id.ACInvento_EditInvento);

        guardar = (Button)findViewById(R.id.Save_EditInvento);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ModuloEntidad.obtenerModulo().editarInventor();
            }
        });
		
		nombre.setText(inventor.getNombreCompleto());
		if(inventor.getAñoNacimiento() < 0){
			año.setText(String.valueOf(Math.abs(inventor.getAñoNacimiento())));
			AC.setChecked(true);
		}else{
			año.setText(String.valueOf(inventor.getAñoNacimiento()));
		}
		lugar.setText(inventor.getLugarNacimiento());
		

    }
}
