package com.example.tallermaterial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetalleLibro extends AppCompatActivity {
    private TextView isbn, nombre, autor;
    private ImageView foto;
    private Bundle bundle;
    private Intent intent;
    private StorageReference storageReference;
    private Libro l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        String isb, nom, aut, id;

        foto = findViewById(R.id.imgFotoDetalle);
        isbn = findViewById(R.id.lblISBNDetalle);
        nombre = findViewById(R.id.lblNombreDetalle);
        autor = findViewById(R.id.lblAutorDEtalle);

        storageReference = FirebaseStorage.getInstance().getReference();

        intent = getIntent();
        bundle = intent.getBundleExtra("datos");

        isb = bundle.getString("isbn");
        nom = bundle.getString("nombre");
        aut = bundle.getString("autor");
        id = bundle.getString("id");
        l = new Libro(isb, nom, aut, id);
        storageReference.child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto);
            }
        });

        isbn.setText(isb);
        nombre.setText(nom);
        autor.setText(aut);

    }

    public void eliminar(View v){
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.Titulo_eliminar);
        builder.setMessage(R.string.mensaje_eliminar);
        positivo = getString(R.string.mensaje_positivo);
        negativo = getString(R.string.mensaje_negativo);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            l.eliminar();
            onBackPressed();
            }
        });

        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog =  builder.create();
        dialog.show();
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(DetalleLibro.this, MainActivity.class);
        startActivity(intent);
    }

}