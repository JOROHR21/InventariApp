package com.roberth.inventariapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import Adaptadores.slideradapter;
import areascasa.menubano;
import areascasa.menucocina;
import areascasa.menuhabi;
import areascasa.menusala;


public class MenuLateral extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    SliderView sliderView;

    int[] images={R.drawable.uno,
            R.drawable.uno,
            R.drawable.uno,
            R.drawable.uno,
            R.drawable.uno,
    };

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            //condicionando los item del case
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral);




        //referenciando en MenuLateral.java
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle( MenuLateral.this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            private Object view;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Log.i("MENU_DRAWER_TAG", "Home item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_gastos:
                       // Intent irmenu = new Intent(Login.this, MenuLateral.class);
                        //startActivity(irmenu);
                        //Log.i("MENU_DRAWER_TAG", "Gastos item is clicked");
                        //drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_informes:
                        Log.i("MENU_DRAWER_TAG", "Informes item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_calculadora:
                        Log.i("MENU_DRAWER_TAG", "Caculadora item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_lectorqr:
                        Log.i("MENU_DRAWER_TAG", "Lector QR item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Salir:
                        Log.i("MENU_DRAWER_TAG", "Cerrar Sesion item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }

                return true;
            }
        });

        CardView card1;
        CardView card2;
        CardView card3;
        CardView card4;

        card1 = (CardView) findViewById(R.id.cardcocina);
        card2 = (CardView) findViewById(R.id.cardsala);
        card3 = (CardView) findViewById(R.id.cardbaño);
        card4 = (CardView) findViewById(R.id.cardhabi);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);

        sliderView= findViewById(R.id.imagesilder);

        slideradapter slideradapter= new slideradapter(images);
        sliderView.setSliderAdapter(slideradapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        mAuth =FirebaseAuth.getInstance();

    } //AQUI TERMINA EL OnCreate


    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){

            case R.id.cardcocina:
                i = new Intent(this,menucocina.class);
                startActivity(i);
                break;
            case R.id.cardsala:
                i = new Intent(this, menusala.class);
                startActivity(i);
                break;
            case R.id.cardbaño:
                i = new Intent(this, menubano.class);
                startActivity(i);
                break;
            case R.id.cardhabi:
                i = new Intent(this, menuhabi.class);
                startActivity(i);
                break;
                
        }

    }
    private void initView(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

    }

    public void clickCerrarSesion(View view) {
        mAuth.signOut();
        Intent irlogin=new Intent(MenuLateral.this,Login.class);
        startActivity(irlogin);
        finish();
    }
}
//para boton cerrar


