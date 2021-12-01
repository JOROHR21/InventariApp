package areascasa;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roberth.inventariapp.R;

import java.util.ArrayList;
import java.util.UUID;

import Adaptadores.ListViewBanoAdapter;
import Models.Bano;


public class menubano extends AppCompatActivity {

    private ArrayList<Bano> listBanos= new ArrayList<Bano>();
    ArrayAdapter<Bano> arrayAdapterBano;
    ListViewBanoAdapter listViewBanoAdapter;
    LinearLayout lyeditarb;
    ListView lvbano;

    EditText inputnombrepb, inputcantidadpb;
    Button btncancelarpb;

    Bano banoSeleccionada;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubano);

        inputnombrepb = findViewById(R.id.inputnombrepb);
        inputcantidadpb = findViewById(R.id.inputcantidadpb);
        btncancelarpb = findViewById(R.id.btncancelarpb);

        lvbano = findViewById(R.id.lvbano);
        lyeditarb = findViewById(R.id.lyeditarb);

        lvbano.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                banoSeleccionada = (Bano) parent.getItemAtPosition(position);
                inputnombrepb.setText(banoSeleccionada.getProductob());
                inputcantidadpb.setText(banoSeleccionada.getCantidadb());
                //hacer vivisble el lyeditar
                lyeditarb.setVisibility(View.VISIBLE);
            }
        });
        btncancelarpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyeditarb.setVisibility(View.GONE);
                banoSeleccionada = null;

            }
        });

        inicializarFirebase();
        ListarBano();

    }




    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void ListarBano(){
        databaseReference.child("Bano").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listBanos.clear();
                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Bano b = objSnaptshot.getValue(Bano.class);
                    listBanos.add(b);
                }
                //iniciar nuestro adaptador
                listViewBanoAdapter = new ListViewBanoAdapter(menubano.this,listBanos);
                //arrayAdapterCocina = new ArrayAdapter<Cocina>(menucocina.this, android.R.layout.simple_list_item_1, listCocinas);
                lvbano.setAdapter(listViewBanoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crud_bano, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String productob = inputnombrepb.getText().toString();
        String cantidadb = inputcantidadpb.getText().toString();

        switch (item.getItemId()) {
            case R.id.bano_agregar:
                insertar();
                break;
            case R.id.bano_guardar:
                if (banoSeleccionada != null){
                    if (validarInputs() == false);
                    Bano b = new Bano();
                    b.setIdbano(banoSeleccionada.getIdbano());
                    b.setProductob(productob);
                    b.setCantidadb(cantidadb);
                    databaseReference.child("Bano").child(b.getIdbano()).setValue(b);
                    Toast.makeText(this , "Actualizado Correctamente" , Toast.LENGTH_SHORT);
                    lyeditarb.setVisibility(View.GONE);
                    banoSeleccionada = null;
                } else
                {
                    Toast.makeText(this, "Seleccione un Producto" , Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.bano_eliminar:
                if (banoSeleccionada != null){
                    Bano b2 = new Bano();
                    b2.setIdbano(banoSeleccionada.getIdbano());
                    databaseReference.child("Bano").child(b2.getIdbano()).removeValue();
                    lyeditarb.setVisibility(View.GONE);
                    banoSeleccionada = null;
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
                menubano.this
        );
        View mView = getLayoutInflater().inflate(R.layout.insertarbano, null);
        Button btnInsertarb = (Button) mView.findViewById(R.id.btnInsertarb);
        final EditText medtinsertarpb = (EditText) mView.findViewById(R.id.edtinsertpb);
        final EditText medtinsertarcb = (EditText) mView.findViewById(R.id.edtinsertcb);

        mBuilder.setView(mView);
        final  AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnInsertarb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productob = medtinsertarpb.getText().toString();
                String cantidadb = medtinsertarcb.getText().toString();

                if (productob.isEmpty() || productob.length()<1){
                    showError(medtinsertarpb,  "Nombre Invalido");
                }else if (cantidadb.isEmpty() || cantidadb.length() <1){
                    showError(medtinsertarcb, "Cantidad Requerida");
                }else{
                    Bano b = new Bano();
                    b.setIdbano(UUID.randomUUID().toString());
                    b.setProductob(productob);
                    b.setCantidadb(cantidadb);
                    // c.setFecharegistro(getFechaNormal());
                    databaseReference.child("Bano").child(b.getIdbano()).setValue(b);
                    Toast.makeText( menubano.this, "Producto Agregado" , Toast.LENGTH_SHORT).show();
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
        String productob = inputnombrepb.getText().toString();
        String cantidadb = inputcantidadpb.getText().toString();
        if (productob.isEmpty() || productob.length()<1){
            showError(inputnombrepb,  "Nombre Invalido");
            return true;
        }else if (cantidadb.isEmpty() || cantidadb.length() <1){
            showError(inputcantidadpb, "Cantidad Requerida");
            return true;
        }else{
            return false;
        }

    }
}