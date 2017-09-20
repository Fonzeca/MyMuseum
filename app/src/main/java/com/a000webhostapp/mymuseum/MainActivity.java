package com.a000webhostapp.mymuseum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Entidades.Inventor;

public class MainActivity extends AppCompatActivity implements AdminPanelFragment.OnFragmentInteractionListener, InfoMuseoFragment.OnFragmentInteractionListener, InicioFragment.OnFragmentInteractionListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragmentActual;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intanciamos el Inicio Fragment

        Fragment fragment = new InicioFragment();

        //Ponemos el Inicio Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();



        //Cofiguramos el FloatingButton
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Cofiguramos la hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        //Cofiguramos el NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                fragmentActual = null;

                if(id == R.id.nav_inicio){
                    fragmentActual = new InicioFragment();
                }else if(id == R.id.nav_info_del_museo){
                    fragmentActual= new InfoMuseoFragment();
                }else if (id == R.id.nav_panel_de_administrador) {
                    fragmentActual = new AdminPanelFragment();
                }

                //Hacemos la transicion del fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_content, fragmentActual).commit();

                //Cerramos el drawer
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void onFragmentInteraction(Uri uri) {

    }
}
