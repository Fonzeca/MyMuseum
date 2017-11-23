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

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;

/**
 * Created by Gaston on 20/9/2017.
 */

public class ArticuloInventoArrayAdapter extends ArrayAdapter<Objeto> {
    private final Context context;
    private final Objeto[] objetos;
	
	private final String strPersonaInventor = "Inventor";
	private final String strPersonaPintor = "Pintor";
	
	private final String strAñoInventor = "Año de invención";
	private final String strAñoPintor = "Año de creación";
	
	
    public ArticuloInventoArrayAdapter(Context context, Objeto[] objetos){
        super(context, -1, objetos);
        this.context = context;
        this.objetos = objetos;
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
		
		TextView nombrePersona = (TextView) rowView.findViewById(R.id.strPersona);
		TextView añoPeriodo = (TextView) rowView.findViewById(R.id.strAño);
		if(objetos[position] instanceof Invento){
			nombrePersona.setText(strPersonaInventor);
			añoPeriodo.setText(strAñoInventor);
		}else if(objetos[position] instanceof Pintura){
			nombrePersona.setText(strPersonaPintor);
			añoPeriodo.setText(strAñoPintor);
		}
		

		
		nombreInvento.setText(objetos[position].getNombre());
		if(objetos[position].getAñoInvencion() < 0){
			String texto = String.valueOf(Math.abs(objetos[position].getAñoInvencion())) + " A.C.";
			añoInvencion.setText(texto);
		}else {
			añoInvencion.setText(String.valueOf(objetos[position].getAñoInvencion()));
		}
		
		if(objetos[position] instanceof Invento){
			nombreInventor.setText(((Invento)objetos[position]).getInventor().getNombre());
		}else if(objetos[position] instanceof Pintura){
			nombreInventor.setText(((Pintura)objetos[position]).getPintor().getNombre());
		}
		

        verDetalles.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				Intent intent = new Intent(context, ArticuloObjetoActivity.class);
				if(objetos[position] instanceof Invento){
					intent.putExtra("TipoObjeto", ControlDB.str_obj_Invento);
					intent.putExtra(ControlDB.str_obj_Invento, (Invento)objetos[position]);
					context.startActivity(intent);
				}else if(objetos[position] instanceof Pintura){
					intent.putExtra("TipoObjeto", ControlDB.str_obj_Pintura);
					intent.putExtra(ControlDB.str_obj_Pintura, (Pintura)objetos[position]);
					context.startActivity(intent);
				}
                
            }
        });

        return rowView;
    }
}
