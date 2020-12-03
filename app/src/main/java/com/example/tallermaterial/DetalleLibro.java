package com.example.tallermaterial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleLibro extends AppCompatActivity {
    private TextView isbn, nombre, autor;
    private ImageView foto;
    private Bundle bundle;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        String isb, nom, aut;
        foto = findViewById(R.id.imgFotoDetalle);
        isbn = findViewById(R.id.lblISBNDetalle);
        nombre = findViewById(R.id.lblNombreDetalle);
        autor = findViewById(R.id.lblAutorDEtalle);

        intent = getIntent();
        bundle = intent.getBundleExtra("datos");

        isb = bundle.getString("isbn");
        nom = bundle.getString("nombre");
        aut = bundle.getString("autor");

        isbn.setText(isb);
        nombre.setText(nom);
        autor.setText(aut);
    }
}