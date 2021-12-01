package areascasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roberth.inventariapp.R;

import java.util.ArrayList;
import java.util.UUID;

import Adaptadores.ListViewCocinaAdapter;
import Models.Cocina;

public class menucocina extends AppCompatActivity  {


    private ArrayList<Cocina> listCocinas= new ArrayList<Cocina>();
    ArrayAdapter<Cocina> arrayAdapterCocina;
    ListViewCocinaAdapter listViewCocinaAdapter;
    LinearLayout lyeditar;
    ListView lvcocina;

    EditText inputnombrep, inputcantidadp;
    Button btncancelarp;

    Cocina cocinaSeleccionada;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menucocina);

        inputnombrep = findViewById(R.id.inputnombrep);
        inputcantidadp = findViewById(R.id.inputcantidadp);
        btncancelarp = findViewById(R.id.btncancelarp);

        lvcocina = findViewById(R.id.lvcocina);
        lyeditar = findViewById(R.id.lyeditar);

        lvcocina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                cocinaSeleccionada = (Cocina) parent.getItemAtPosition(position);
                inputnombrep.setText(cocinaSeleccionada.getProducto());
                inputcantidadp.setText(cocinaSeleccionada.getCantidad());
                //hacer vivisble el lyeditar
                lyeditar.setVisibility(View.VISIBLE);
            }
        });
        btncancelarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyeditar.setVisibility(View.GONE);
                cocinaSeleccionada = null;

            }
        });

        inicializarFirebase();
        listarCocina();
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarCocina(){
        databaseReference.child("Cocina").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCocinas.clear();
                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Cocina c = objSnaptshot.getValue(Cocina.class);
                    listCocinas.add(c);
                }
                //iniciar nuestro adaptador
                listViewCocinaAdapter = new ListViewCocinaAdapter(menucocina.this,listCocinas);
                //arrayAdapterCocina = new ArrayAdapter<Cocina>(menucocina.this, android.R.layout.simple_list_item_1, listCocinas);
                lvcocina.setAdapter(listViewCocinaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crud_cocina, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String producto = inputnombrep.getText().toString();
        String cantidad = inputcantidadp.getText().toString();

        switch (item.getItemId()) {
            case R.id.cocina_agregar:
                insertar();
                break;
            case R.id.cocina_guardar:
                if (cocinaSeleccionada != null){
                if (validarInputs() == false);
                    Cocina c = new Cocina();
                    c.setIdcocina(cocinaSeleccionada.getIdcocina());
                    c.setProducto(producto);
                    c.setCantidad(cantidad);
                    databaseReference.child("Cocina").child(c.getIdcocina()).setValue(c);
                    Toast.makeText(this , "Actualizado Correctamente" , Toast.LENGTH_SHORT);
                    lyeditar.setVisibility(View.GONE);
                    cocinaSeleccionada = null;
                } else
                    {
                    Toast.makeText(this, "Seleccione un Producto" , Toast.LENGTH_SHORT).show();

                    }
                break;
            case R.id.cocina_eliminar:
                if (cocinaSeleccionada != null){
                    Cocina c2 = new Cocina();
                    c2.setIdcocina(cocinaSeleccionada.getIdcocina());
                    databaseReference.child("Cocina").child(c2.getIdcocina()).removeValue();
                    lyeditar.setVisibility(View.GONE);
                    cocinaSeleccionada = null;
                    Toast.makeText(this , "Eliminado Correctamente" , Toast.LENGTH_SHORT);
                }else {
                    Toast.makeText(this, "Seleccione un Producto" , Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertar(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                menucocina.this
        );
        View mView = getLayoutInflater().inflate(R.layout.insertarcocina, null);
        Button btnInsertar = (Button) mView.findViewById(R.id.btnInsertar);
        final EditText medtinsertarp = (EditText) mView.findViewById(R.id.edtinsertp);
        final EditText medtinsertarc = (EditText) mView.findViewById(R.id.edtinsertc);

        mBuilder.setView(mView);
        final  AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String producto = medtinsertarp.getText().toString();
                String cantidad = medtinsertarc.getText().toString();

                if (producto.isEmpty() || producto.length()<1){
                    showError(medtinsertarp,  "Nombre Invalido");
                }else if (cantidad.isEmpty() || cantidad.length() <1){
                    showError(medtinsertarc, "Cantidad Requerida");
                }else{
                    Cocina c = new Cocina();
                    c.setIdcocina(UUID.randomUUID().toString());
                    c.setProducto(producto);
                    c.setCantidad(cantidad);
                   // c.setFecharegistro(getFechaNormal());
                    databaseReference.child("Cocina").child(c.getIdcocina()).setValue(c);
                    Toast.makeText( menucocina.this, "Producto Agregado" , Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

    }
    public void showError(EditText input, String s){
        input.requestFocus();
        input.setError(s);
    }
   // public String getFechaNormal(){
       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        //String fecha = getFechaNormal();
        //return fecha;

        public  boolean validarInputs(){
            String producto = inputnombrep.getText().toString();
            String cantidad = inputcantidadp.getText().toString();
            if (producto.isEmpty() || producto.length()<1){
                showError(inputnombrep,  "Nombre Invalido");
                return true;
            }else if (cantidad.isEmpty() || cantidad.length() <1){
                showError(inputcantidadp, "Cantidad Requerida");
                return true;
            }else{
                return false;
            }

        }
}
//}