package com.a000webhostapp.mymuseum.Vista.TrasladoABM;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Alexis on 28/10/2017.
 */

public class EliminarTrasladoActivity extends AppCompatActivity {
	
	private Button save;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				eliminarInformacion();
			}
		});
		
	}
	private void eliminarInformacion(){
		
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
}
