package com.example.tallermaterial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class CrearLibro extends AppCompatActivity {
    private EditText ISBN, nombre, autor;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_libro);
        ISBN = findViewById(R.id.txtISBN);
        nombre = findViewById(R.id.txtNombre);
        autor = findViewById(R.id.txtAutor);
    }

    public void guardar(View v){
        String isbn, nom, aut, id;
        Libro l;
        isbn = ISBN.getText().toString();
        nom = nombre.getText().toString();
        aut = autor.getText().toString();

        l = new Libro(isbn, nom, aut, "");
        l.guardar();
        limpiar();
        Snackbar.make(v, R.string.msj_guardado_exitosamente,
                Snackbar.LENGTH_LONG).show();
    }

    public void limpiar(View v){
        limpiar();
    }

    public void limpiar(){
        ISBN.setText("");
        nombre.setText("");
        autor.setText("");
        ISBN.requestFocus();
    }
}