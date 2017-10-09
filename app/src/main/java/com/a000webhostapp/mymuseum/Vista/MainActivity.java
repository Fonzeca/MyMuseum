package com.a000webhostapp.mymuseum.Vista;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.AdminPanelFragment;
import com.a000webhostapp.mymuseum.Vista.InfoMuseoFragment;
import com.a000webhostapp.mymuseum.Vista.InicioFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragmentActual;
	private Fragment incioFragment, infoFragment, adminFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intanciamos el Inicio Fragment

		incioFragment = new InicioFragment();

        //Ponemos el Inicio Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content, incioFragment).commit();



        //Cofiguramos el FloatingButton
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_search_white);
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
					if(incioFragment == null){
						incioFragment = new InicioFragment();
					}
                    fragmentActual = incioFragment;
                }else if(id == R.id.nav_info_del_museo){
					if(infoFragment == null){
						infoFragment = new InfoMuseoFragment();
					}
                    fragmentActual = infoFragment;
                }else if (id == R.id.nav_panel_de_administrador) {
					if(adminFragment == null){
						adminFragment = new AdminPanelFragment();
					}
                    fragmentActual = adminFragment;
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
}
