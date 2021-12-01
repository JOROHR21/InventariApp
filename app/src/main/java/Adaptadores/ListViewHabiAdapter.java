package Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roberth.inventariapp.R;

import java.util.ArrayList;

import Models.Habi;

public class ListViewHabiAdapter extends BaseAdapter {

    Context context;
    ArrayList<Habi> habiData;
    LayoutInflater layoutInflater;
    Habi habiModel;

    public ListViewHabiAdapter(Context context , ArrayList<Habi> habiData) {
        this.context = context;
        this.habiData = habiData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }


    @Override
    public int getCount() {
        return habiData.size();
    }

    @Override
    public Object getItem(int position) {
        return habiData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        View rowView = convertView;
        if (rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_habi,
                    null,
                    true);
        }
        //enlazamos las vistas  //VERIFICAR SI SON LOS MISMOS DEL LYEDITAR
        TextView producto = rowView.findViewById(R.id.lblproductoh);
        TextView cantidad = rowView.findViewById(R.id.lblCantidadh);
        TextView fecharegistro = rowView.findViewById(R.id.lblFechaRegistro);


        habiModel = habiData.get(position);
        producto.setText(habiModel.getProductoh());
        cantidad.setText(habiModel.getCantidadh());
        fecharegistro.setText(habiModel.getFecharegistro());

        return rowView;
    }
}
