package com.example.tallermaterial;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorLibro extends  RecyclerView.Adapter<AdaptadorLibro.LibroViewHolder>{
    private ArrayList<Libro> libros;
    private onLibroClickListener clickListener;

    public AdaptadorLibro(ArrayList<Libro> libros, onLibroClickListener clickListener){
        this.libros = libros;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new LibroViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        Libro l = libros.get(position);
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(l.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.foto);
            }
        });

        holder.isbn.setText(l.getISBN());
        holder.nombre.setText(l.getNombre());
        holder.autor.setText(l.getAutor());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onlibroClick(l);
            }
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public static class LibroViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView foto;
        private TextView isbn;
        private TextView nombre;
        private TextView autor;
        private View v;

        public LibroViewHolder(View itemView){
            super(itemView);
            v = itemView;
            foto = v.findViewById(R.id.imgFotoItem);
            isbn = v.findViewById(R.id.lblISBNItem);
            nombre = v.findViewById(R.id.lblNombreItem);
            autor = v.findViewById(R.id.lblAutorItem);

        }
    }

    public interface onLibroClickListener{
        void onlibroClick(Libro l);
    }
}