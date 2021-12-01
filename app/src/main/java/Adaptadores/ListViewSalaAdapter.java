package Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roberth.inventariapp.R;

import java.util.ArrayList;

import Models.Sala;

public class ListViewSalaAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sala> salaData;
    LayoutInflater layoutInflater;
    Sala salaModel;

    public ListViewSalaAdapter(Context context , ArrayList<Sala> salaData) {
        this.context = context;
        this.salaData = salaData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }


    @Override
    public int getCount() {
        return salaData.size();
    }

    @Override
    public Object getItem(int position) {
        return salaData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        View rowView = convertView;
        if (rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_sala,
                    null,
                    true);
        }
        //enlazamos las vistas  //VERIFICAR SI SON LOS MISMOS DEL LYEDITAR
        TextView producto = rowView.findViewById(R.id.lblproductocs);
        TextView cantidad = rowView.findViewById(R.id.lblCantidadcs);
        TextView fecharegistro = rowView.findViewById(R.id.lblFechaRegistro);


        salaModel = salaData.get(position);
        producto.setText(salaModel.getProductos());
        cantidad.setText(salaModel.getCantidads());
        fecharegistro.setText(salaModel.getFecharegistro());

        return rowView;
    }
}
