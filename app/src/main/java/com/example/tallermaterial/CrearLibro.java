package com.example.tallermaterial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CrearLibro extends AppCompatActivity {
    private EditText ISBN, nombre, autor;
    private ImageView foto;
    private Uri uri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_libro);
        ISBN = findViewById(R.id.txtISBN);
        nombre = findViewById(R.id.txtNombre);
        autor = findViewById(R.id.txtAutor);
        foto = findViewById(R.id.imgFotoSeleccionada);

        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public void guardar(View v){
        String isbn, nom, aut, id;
        Libro l;
        InputMethodManager imp;

        isbn = ISBN.getText().toString();
        nom = nombre.getText().toString();
        aut = autor.getText().toString();
        id = Datos.getId();
        imp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        l = new Libro(isbn, nom, aut,id);
        l.guardar();
        imp.hideSoftInputFromWindow(ISBN.getWindowToken(),0);
        limpiar();
        subirFoto(id);
        Snackbar.make(v, R.string.msj_guardado_exitosamente,
                Snackbar.LENGTH_LONG).show();

    }

    public void subirFoto(String id){
        StorageReference child = storageReference.child(id);
        UploadTask uploadTask = child.putFile(uri);

    }

    public void limpiar(View v){
        limpiar();
    }

    public void limpiar(){
        ISBN.setText("");
        nombre.setText("");
        autor.setText("");
        ISBN.requestFocus();
        foto.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(CrearLibro.this, MainActivity.class);
        startActivity(i);
    }

    public void seleccionarFoto(View v){
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in,
                "seleccione la foto de la persona"), 1);
    }

    protected void onActivityResult(int requesCode, int resultCode, Intent data){
        super.onActivityResult(requesCode, resultCode, data);
        if(requesCode == 1){
            uri = data.getData();
            if (uri != null){
                foto.setImageURI(uri);
            }
        }
    }
}