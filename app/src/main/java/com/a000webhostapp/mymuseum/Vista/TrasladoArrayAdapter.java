package com.a000webhostapp.mymuseum.Vista;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.Modelo.Traslado;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaston on 20/9/2017.
 */

public class TrasladoArrayAdapter extends ArrayAdapter<Traslado> {
    private final Context context;
    private final Traslado[] traslados;
	
	
    public TrasladoArrayAdapter(Context context, ArrayList<Traslado> traslados){
        super(context, -1, traslados);
        this.context = context;
        this.traslados = traslados.toArray(new Traslado[traslados.size()]);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View rowView = layoutInflater.inflate(R.layout.articulo_pintura_list_item, parent, false);

        
        TextView nombrePintura = (TextView) rowView.findViewById(R.id.textPintura_historialPintura);
        TextView lugarAnterior = (TextView) rowView.findViewById(R.id.textlugarAnterior_HistorialPintura);
        TextView lugarActual = (TextView) rowView.findViewById(R.id.textLugarActual_HistorialPintura);
        TextView fecha = (TextView) rowView.findViewById(R.id.textFecha_HistorialPintura);
		
		nombrePintura.setText(traslados[position].getPintura().getNombre());
		lugarAnterior.setText(traslados[position].getLugarOrigen());
		lugarActual.setText(traslados[position].getLugarDestino());
		fecha.setText(traslados[position].getFechaTraslado());

        return rowView;
    }
}
