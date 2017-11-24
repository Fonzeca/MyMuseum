package com.a000webhostapp.mymuseum.Vista;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Constantes;
import com.a000webhostapp.mymuseum.R;

/**
 * Created by Alexis on 7/11/2017.
 */

public class LoginFragment extends Fragment {
	private static String clave = "1234";
	
	private EditText claveText;
	private Button save;
	private OnFragmentButtonPressed listener;
	
	public LoginFragment() {
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if(context instanceof OnFragmentButtonPressed){
			listener = (OnFragmentButtonPressed)context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_admin_panel_login, container, false);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState) {
		claveText = (EditText)view.findViewById(R.id.clave_AdminLogin);
		
		save = (Button)view.findViewById(R.id.save_AdminLogin);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				presionaBoton();
			}
		});
	}
	private void presionaBoton(){
		if(claveText.getText().toString().equals(clave)){
			Constantes.obtenerPermisos();
			listener.onButtonPressedI();
		}
	}
	
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
	
	public interface OnFragmentButtonPressed {
		void onButtonPressedI();
	}
}
