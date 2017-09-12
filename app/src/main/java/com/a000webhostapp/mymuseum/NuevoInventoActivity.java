package com.a000webhostapp.mymuseum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NuevoInventoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void startNuevoInventorActivity(View view) {
        Intent intent = new Intent(this, NuevoInventorActivity.class);
        startActivity(intent);
    }

    public void startNuevoPeriodoActivity(View view) {
        Intent intent = new Intent(this, NuevoPeriodoActivity.class);
        startActivity(intent);
    }
}
