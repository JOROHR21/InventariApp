package cocina;

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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roberth.inventariapp.R;

import java.util.ArrayList;

import Adaptadores.ListViewCocinaAdapter;
import Models.Cocina;

public class menucocina extends AppCompatActivity {

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
                arrayAdapterCocina = new ArrayAdapter<Cocina>(
                        menucocina.this,
                        android.R.layout.simple_list_item_1,
                        listCocinas
                );
                lvcocina.setAdapter(arrayAdapterCocina);
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

        String productos = inputnombrep.getText().toString();
        String cantidad = inputcantidadp.getText().toString();

        switch (item.getItemId()){
            case R.id.cocina_agregar:
                insertar();
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


    }
}