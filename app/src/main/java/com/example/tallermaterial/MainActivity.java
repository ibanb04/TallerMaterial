package com.example.tallermaterial;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdaptadorLibro.onLibroClickListener {
    private RecyclerView lista;
    private  AdaptadorLibro adapter;
    private LinearLayoutManager llm;
    private ArrayList<Libro> libros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        lista = findViewById(R.id.lstLibros);
        libros = Datos.obtener();
        llm = new LinearLayoutManager(this);
        adapter = new AdaptadorLibro(libros, this);
        llm.setOrientation(RecyclerView.VERTICAL);

        lista.setLayoutManager(llm);
        lista.setAdapter(adapter);
    }

    public void agregar(View v){
        Intent intent;
        intent = new Intent(MainActivity.this, CrearLibro.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onlibroClick(Libro l) {
        Intent intent;
        Bundle bundle;
        bundle = new Bundle();
        bundle.putString("id", l.getId());
        bundle.putString("isbn", l.getISBN());
        bundle.putString("nombre", l.getNombre());
        bundle.putString("autor", l.getAutor());

        intent = new Intent(MainActivity.this,DetalleLibro.class);
        intent.putExtra("datos", bundle);
        startActivity(intent);
    }
}