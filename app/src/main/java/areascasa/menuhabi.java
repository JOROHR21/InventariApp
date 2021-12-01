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


import Adaptadores.ListViewHabiAdapter;
import Models.Habi;

public class menuhabi extends AppCompatActivity {

    private ArrayList<Habi> listHabis= new ArrayList<Habi>();
    ArrayAdapter<Habi> arrayAdapterHabi;
    ListViewHabiAdapter listViewHabiAdapter;
    LinearLayout lyeditarh;
    ListView lvhabi;

    EditText inputnombreph, inputcantidadph;
    Button btncancelarph;

    Habi habiSeleccionada;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuhabi);

        inputnombreph = findViewById(R.id.inputnombreph);
        inputcantidadph = findViewById(R.id.inputcantidadph);
        btncancelarph = findViewById(R.id.btncancelarph);

        lvhabi = findViewById(R.id.lvhabi);
        lyeditarh = findViewById(R.id.lyeditarh);

        lvhabi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                habiSeleccionada = (Habi) parent.getItemAtPosition(position);
                inputnombreph.setText(habiSeleccionada.getProductoh());
                inputcantidadph.setText(habiSeleccionada.getCantidadh());
                //hacer vivisble el lyeditar
                lyeditarh.setVisibility(View.VISIBLE);
            }
        });
        btncancelarph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyeditarh.setVisibility(View.GONE);
                habiSeleccionada = null;

            }
        });

        inicializarFirebase();
        ListarHabi();

    }




    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void ListarHabi(){
        databaseReference.child("Habi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHabis.clear();
                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Habi h = objSnaptshot.getValue(Habi.class);
                    listHabis.add(h);
                }
                //iniciar nuestro adaptador
                listViewHabiAdapter = new ListViewHabiAdapter(menuhabi.this,listHabis);
                //arrayAdapterCocina = new ArrayAdapter<Cocina>(menucocina.this, android.R.layout.simple_list_item_1, listCocinas);
                lvhabi.setAdapter(listViewHabiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crud_habi, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String productoh = inputnombreph.getText().toString();
        String cantidadh = inputcantidadph.getText().toString();

        switch (item.getItemId()) {
            case R.id.habi_agregar:
                insertar();
                break;
            case R.id.habi_guardar:
                if (habiSeleccionada != null){
                    if (validarInputs() == false);
                    Habi h = new Habi();
                    h.setIdhabi(habiSeleccionada.getIdhabi());
                    h.setProductoh(productoh);
                    h.setCantidadh(cantidadh);
                    databaseReference.child("Habi").child(h.getIdhabi()).setValue(h);
                    Toast.makeText(this , "Actualizado Correctamente" , Toast.LENGTH_SHORT);
                    lyeditarh.setVisibility(View.GONE);
                    habiSeleccionada = null;
                } else
                {
                    Toast.makeText(this, "Seleccione un Producto" , Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.habi_eliminar:
                if (habiSeleccionada != null){
                    Habi h2 = new Habi();
                    h2.setIdhabi(habiSeleccionada.getIdhabi());
                    databaseReference.child("Habi").child(h2.getIdhabi()).removeValue();
                    lyeditarh.setVisibility(View.GONE);
                    habiSeleccionada = null;
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
                menuhabi.this
        );
        View mView = getLayoutInflater().inflate(R.layout.insertarhabi, null);
        Button btnInsertarh = (Button) mView.findViewById(R.id.btnInsertarh);
        final EditText medtinsertarph = (EditText) mView.findViewById(R.id.edtinsertph);
        final EditText medtinsertarch = (EditText) mView.findViewById(R.id.edtinsertch);

        mBuilder.setView(mView);
        final  AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnInsertarh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productoh = medtinsertarph.getText().toString();
                String cantidadh = medtinsertarch.getText().toString();

                if (productoh.isEmpty() || productoh.length()<1){
                    showError(medtinsertarph,  "Nombre Invalido");
                }else if (cantidadh.isEmpty() || cantidadh.length() <1){
                    showError(medtinsertarch, "Cantidad Requerida");
                }else{
                    Habi h = new Habi();
                    h.setIdhabi(UUID.randomUUID().toString());
                    h.setProductoh(productoh);
                    h.setCantidadh(cantidadh);
                    // c.setFecharegistro(getFechaNormal());
                    databaseReference.child("Habi").child(h.getIdhabi()).setValue(h);
                    Toast.makeText( menuhabi.this, "Producto Agregado" , Toast.LENGTH_SHORT).show();
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
        String productoh = inputnombreph.getText().toString();
        String cantidadh = inputcantidadph.getText().toString();
        if (productoh.isEmpty() || productoh.length()<1){
            showError(inputnombreph,  "Nombre Invalido");
            return true;
        }else if (cantidadh.isEmpty() || cantidadh.length() <1){
            showError(inputcantidadph, "Cantidad Requerida");
            return true;
        }else{
            return false;
        }

    }
}