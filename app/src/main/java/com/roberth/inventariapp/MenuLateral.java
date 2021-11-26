package com.roberth.inventariapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

import cocina.menucocina;


public class MenuLateral extends AppCompatActivity {
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
                        Intent irmenucocina = new Intent(MenuLateral.this, menucocina.class);
                        startActivity(irmenucocina);
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

                    case R.id.nav_csesion:
                        Log.i("MENU_DRAWER_TAG", "Cerrar Sesion item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;

                }

                return true;
            }
        });

    }
}