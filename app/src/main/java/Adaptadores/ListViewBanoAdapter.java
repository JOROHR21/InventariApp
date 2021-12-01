package Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roberth.inventariapp.R;

import java.util.ArrayList;

import Models.Bano;


public class ListViewBanoAdapter extends BaseAdapter {

    Context context;
    ArrayList<Bano> banoData;
    LayoutInflater layoutInflater;
    Bano banoModel;

    public ListViewBanoAdapter(Context context , ArrayList<Bano> banoData) {
        this.context = context;
        this.banoData = banoData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }


    @Override
    public int getCount() {
        return banoData.size();
    }

    @Override
    public Object getItem(int position) {
        return banoData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        View rowView = convertView;
        if (rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_bano,
                    null,
                    true);
        }
        //enlazamos las vistas  //VERIFICAR SI SON LOS MISMOS DEL LYEDITAR
        TextView producto = rowView.findViewById(R.id.lblproductob);
        TextView cantidad = rowView.findViewById(R.id.lblCantidadb);
        TextView fecharegistro = rowView.findViewById(R.id.lblFechaRegistro);


        banoModel = banoData.get(position);
        producto.setText(banoModel.getProductob());
        cantidad.setText(banoModel.getCantidadb());
        fecharegistro.setText(banoModel.getFecharegistro());

        return rowView;
    }
}
