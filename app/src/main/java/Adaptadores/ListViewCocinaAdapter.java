package Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roberth.inventariapp.R;

import java.util.ArrayList;

import Models.Cocina;

public class ListViewCocinaAdapter extends BaseAdapter {

    Context context;
    ArrayList<Cocina> cocinaData;
    LayoutInflater layoutInflater;
    Cocina cocinaModel;

    public ListViewCocinaAdapter(Context context , ArrayList<Cocina> cocinaData) {
        this.context = context;
        this.cocinaData = cocinaData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }


    @Override
    public int getCount() {
        return cocinaData.size();
    }

    @Override
    public Object getItem(int position) {
        return cocinaData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        View rowView = convertView;
        if (rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_cocina,
                     null,
                    true);
        }
        //enlazamos las vistas  //VERIFICAR SI SON LOS MISMOS DEL LYEDITAR
        TextView producto = rowView.findViewById(R.id.lblproductoc);
        TextView cantidad = rowView.findViewById(R.id.lblCantidadc);
        TextView fecharegistro = rowView.findViewById(R.id.lblFechaRegistro);


        cocinaModel = cocinaData.get(position);
        producto.setText(cocinaModel.getProducto());
        cantidad.setText(cocinaModel.getCantidad());
        fecharegistro.setText(cocinaModel.getFecharegistro());

        return rowView;
    }
}
