package com.example.tallermaterial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CrearLibro extends AppCompatActivity {
    private EditText ISBN, nombre, autor;
    private ImageView foto;
    private Uri uri;
    private StorageReference storageReference;
    private boolean bandera = false;

    private String db = "Libros";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_libro);

        ISBN = findViewById(R.id.txtISBN);
        nombre = findViewById(R.id.txtNombre);
        autor = findViewById(R.id.txtAutor);
        foto = findViewById(R.id.imgFotoSeleccionada);

        //libros = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Libros");


        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public void guardar(View v){
        String isbn, nom, aut, id;
        Libro l;
        InputMethodManager imp;

        imp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imp.hideSoftInputFromWindow(ISBN.getWindowToken(), 0);

        if (validar()) {


                isbn = ISBN.getText().toString();
                nom = nombre.getText().toString();
                aut = autor.getText().toString();
                id = Datos.getId();
                checkISBN(isbn);
               if (bandera){
                    l = new Libro(isbn, nom, aut, id);
                    l.guardar();
                    limpiar();
                    subirFoto(id);
                    Snackbar.make(v, R.string.msj_guardado_exitosamente,
                            Snackbar.LENGTH_LONG).show();
                    uri = null;
               }else {
                    Snackbar.make(v, R.string.mensaje_error_isbn,
                            Snackbar.LENGTH_LONG).show();
                }

        }
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
                getString(R.string.seleccionar_foto)), 1);
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

    public  void checkISBN(String isbn){

        databaseReference.orderByChild("isbn").equalTo(isbn).limitToFirst(1).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    Log.i("SNAPSHOT INFO",String.valueOf(snapshot.getValue()));
                    bandera = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public boolean validar(){

        if (ISBN.getText().toString().isEmpty()){
            ISBN.setError(getString(R.string.mensaje_error_ISBN));
            ISBN.requestFocus();
            return false;
        }

        if (nombre.getText().toString().isEmpty()){
            nombre.setError(getString(R.string.mensaje_error_nombre));
            nombre.requestFocus();
            return false;
        }
        if (autor.getText().toString().isEmpty()){
            autor.setError(getString(R.string.mensaje_error_autor));
            autor.requestFocus();
            return false;
        }

        if (uri == null){
            Snackbar.make((View)ISBN, R.string.mensaje_error_foto, Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}