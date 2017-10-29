package com.a000webhostapp.mymuseum.Vista;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscarObjetoActivity extends AppCompatActivity {
	private Button save;
	private Spinner objetosSpinner;
	private EditText nombreObjetoEdit;
	
	public static final int requestPermisionCamera = 1;
	public static final int requestQR = 4;
	public static final int responseScannerQR = 5;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_objeto);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		nombreObjetoEdit = (EditText)findViewById(R.id.NomObjeto_BuscarObjeto);
		
		save = (Button) findViewById(R.id.Save_BuscarObjeto);
		
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				switch ((String)objetosSpinner.getSelectedItem()){
					case ControlDB.str_obj_Invento:
						if(!nombreObjetoEdit.getText().toString().isEmpty()){
							Intent data = new Intent();
							data.setData(Uri.parse(ControlDB.str_obj_Invento + "=" + nombreObjetoEdit.getText()));
							setResult(RESULT_OK,data);
							finish();
						}
						break;
					case ControlDB.str_obj_Pintura:
						if(!nombreObjetoEdit.getText().toString().isEmpty()){
							Intent data = new Intent();
							data.setData(Uri.parse(ControlDB.str_obj_Pintura + "=" + nombreObjetoEdit.getText()));
							setResult(RESULT_OK,data);
							finish();
						}
						break;
				}
				
				onBackPressed();
			}
		});
		
		objetosSpinner = (Spinner) findViewById(R.id.spinnerObjeto_BuscarObjeto);
		
		actualizarSpinnerObjetos();
		
	}
	private void actualizarSpinnerObjetos(){
		List<String> spinnerArray =  new ArrayList<String>(Arrays.asList(ControlDB.objetos));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		objetosSpinner.setAdapter(adapter);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == requestQR && resultCode == RESULT_OK){
			setResult(responseScannerQR,data);
			finish();
		}
	}
	
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.articulo_invento_menu_qr, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.busqueda_escanearQR:
				escanearQR();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void escanearQR(){
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, requestPermisionCamera);
			
		}else{
			Intent intent = new Intent(this, LectorQRActivity.class);
			startActivityForResult(intent,requestQR);
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode){
			case requestPermisionCamera:
				if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
					Intent intent = new Intent(this, LectorQRActivity.class);
					startActivityForResult(intent,requestQR);
				}else{
					Toast.makeText(this, "Por favor, garantizar el permiso de la camara o sino no te va a andar", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
}
