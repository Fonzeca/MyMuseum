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
import com.a000webhostapp.mymuseum.R;

/**
 * Created by Gaston on 20/9/2017.
 */

public class ArticuloInventoArrayAdapter extends ArrayAdapter<Invento> {
    private final Context context;
    private final Invento[] inventos;

    public ArticuloInventoArrayAdapter(Context context, Invento[] inventos){
        super(context, -1, inventos);
        this.context = context;
        this.inventos = inventos;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = layoutInflater.inflate(R.layout.articulo_invento_list_item, parent, false);

        TextView nombreInvento = (TextView) rowView.findViewById(R.id.nombre_invento_list_item);
        TextView añoInvencion = (TextView) rowView.findViewById(R.id.año_invencion_list_item);
        TextView nombreInventor = (TextView) rowView.findViewById(R.id.nombre_inventor_list_item);
        TextView verDetalles = (TextView) rowView.findViewById(R.id.ver_detalles_list_item);

        nombreInvento.setText(inventos[position].getNombre());
        if(inventos[position].getAñoInvencion() < 0){
            String texto = String.valueOf(Math.abs(inventos[position].getAñoInvencion())) + " A.C.";
            añoInvencion.setText(texto);
        }else {
            añoInvencion.setText(String.valueOf(inventos[position].getAñoInvencion()));
        }
        nombreInventor.setText(inventos[position].getInventor().getNombreCompleto());

        verDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticuloInventoActivity.class);
                intent.putExtra("Invento", inventos[position]);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
