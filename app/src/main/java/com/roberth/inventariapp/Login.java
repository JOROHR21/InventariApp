package com.roberth.inventariapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login<Protected> extends AppCompatActivity {
    FirebaseAuth mfirebaseAutH;
    FirebaseAuth.AuthStateListener mAuthListener;

    public static final int REQUEST_CODE = 1603;

    //Declarando tipos de inicio de sesion mediante firebaseUI
    List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
            // AGREGA REGISTRO POR CEL new AuthUI.IdpConfig.PhoneBuilder().build(),
            // AGREGA REGISTRO POR EMAIL new AuthUI.IdpConfig.EmailBuilder().build()
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            mfirebaseAutH = FirebaseAuth.getInstance();
            mAuthListener =new  FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    //consultando si el usuario esta en firebase
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user !=null){
                        Toast.makeText(Login.this,"Sesion Exitosa", Toast.LENGTH_SHORT).show();

                    }else{
                        startActivityForResult(AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(provider)
                                .setIsSmartLockEnabled(false)
                                .build(), REQUEST_CODE


                        );
                    }
                }
            };

    }//Fin del oncreate!




    //validamos si entro e inicio sesion que quede login al entrar
    @Override
    protected void onResume() {
        Intent irmenu = new Intent(Login.this, MenuLateral.class);
        startActivity(irmenu);
        super.onResume();
        mfirebaseAutH.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mfirebaseAutH.removeAuthStateListener(mAuthListener);
    }


   // public void cerrarsesion(View view) {

     //   AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>(){
         //   @Override
           // public void onComplete(@NonNull Task<Void> task) {
          //      Toast.makeText(Login.this, "Sesion Cerrada", Toast.LENGTH_SHORT).show();

         //   }
       // });
    //}
}