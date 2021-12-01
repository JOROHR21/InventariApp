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
import Adaptadores.ListViewSalaAdapter;
import Models.Sala;

public class menusala extends AppCompatActivity {

    private ArrayList<Sala> listSalas= new ArrayList<Sala>();
    ArrayAdapter<Sala> arrayAdapterSala;
    ListViewSalaAdapter listViewSalaAdapter;
    LinearLayout lyeditars;
    ListView lvsala;

    EditText inputnombreps, inputcantidadps;
    Button btncancelarps;

    Sala salaSeleccionada;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menusala);

        inputnombreps = findViewById(R.id.inputnombreps);
        inputcantidadps = findViewById(R.id.inputcantidadps);
        btncancelarps = findViewById(R.id.btncancelarps);

        lvsala = findViewById(R.id.lvsala);
        lyeditars = findViewById(R.id.lyeditars);

        lvsala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                salaSeleccionada = (Sala) parent.getItemAtPosition(position);
                inputnombreps.setText(salaSeleccionada.getProductos());
                inputcantidadps.setText(salaSeleccionada.getCantidads());
                //hacer vivisble el lyeditar
                lyeditars.setVisibility(View.VISIBLE);
            }
        });
        btncancelarps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyeditars.setVisibility(View.GONE);
                salaSeleccionada = null;

            }
        });

        inicializarFirebase();
        listarSala();
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarSala(){
        databaseReference.child("Sala").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSalas.clear();
                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Sala s = objSnaptshot.getValue(Sala.class);
                    listSalas.add(s);
                }
                //iniciar nuestro adaptador
                listViewSalaAdapter = new ListViewSalaAdapter(menusala.this,listSalas);
                //arrayAdapterCocina = new ArrayAdapter<Cocina>(menucocina.this, android.R.layout.simple_list_item_1, listCocinas);
                lvsala.setAdapter(listViewSalaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crud_sala, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String productos = inputnombreps.getText().toString();
        String cantidads = inputcantidadps.getText().toString();

        switch (item.getItemId()) {
            case R.id.sala_agregar:
                insertar();
                break;
            case R.id.sala_guardar:
                if (salaSeleccionada != null){
                    if (validarInputs() == false);
                    Sala s = new Sala();
                    s.setIdsala(salaSeleccionada.getIdsala());
                    s.setProductos(productos);
                    s.setCantidads(cantidads);
                    databaseReference.child("Sala").child(s.getIdsala()).setValue(s);
                    Toast.makeText(this , "Actualizado Correctamente" , Toast.LENGTH_SHORT);
                    lyeditars.setVisibility(View.GONE);
                    salaSeleccionada = null;
                } else
                {
                    Toast.makeText(this, "Seleccione un Producto" , Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.sala_eliminar:
                if (salaSeleccionada != null){
                    Sala s2 = new Sala();
                    s2.setIdsala(salaSeleccionada.getIdsala());
                    databaseReference.child("Sala").child(s2.getIdsala()).removeValue();
                    lyeditars.setVisibility(View.GONE);
                    salaSeleccionada = null;
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
                menusala.this
        );
        View mView = getLayoutInflater().inflate(R.layout.insertarsala, null);
        Button btnInsertars = (Button) mView.findViewById(R.id.btnInsertars);
        final EditText medtinsertarps = (EditText) mView.findViewById(R.id.edtinsertps);
        final EditText medtinsertarcs = (EditText) mView.findViewById(R.id.edtinsertcs);

        mBuilder.setView(mView);
        final  AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnInsertars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productos = medtinsertarps.getText().toString();
                String cantidads = medtinsertarcs.getText().toString();

                if (productos.isEmpty() || productos.length()<1){
                    showError(medtinsertarps,  "Nombre Invalido");
                }else if (cantidads.isEmpty() || cantidads.length() <1){
                    showError(medtinsertarcs, "Cantidad Requerida");
                }else{
                    Sala s = new Sala();
                    s.setIdsala(UUID.randomUUID().toString());
                    s.setProductos(productos);
                    s.setCantidads(cantidads);
                    // c.setFecharegistro(getFechaNormal());
                    databaseReference.child("Sala").child(s.getIdsala()).setValue(s);
                    Toast.makeText( menusala.this, "Producto Agregado" , Toast.LENGTH_SHORT).show();
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
        String productos = inputnombreps.getText().toString();
        String cantidads = inputcantidadps.getText().toString();
        if (productos.isEmpty() || productos.length()<1){
            showError(inputnombreps,  "Nombre Invalido");
            return true;
        }else if (cantidads.isEmpty() || cantidads.length() <1){
            showError(inputcantidadps, "Cantidad Requerida");
            return true;
        }else{
            return false;
        }

    }
}