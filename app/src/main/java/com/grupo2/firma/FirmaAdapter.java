package com.grupo2.firma;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FirmaAdapter extends RecyclerView.Adapter<FirmaAdapter.ViewHolder> {

    private final List<Signature> listaFirmas;
    private final Context context;

    public FirmaAdapter(Context context, List<Signature> listaFirmas) {
        this.context = context;
        this.listaFirmas = listaFirmas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_firma, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Signature firma = listaFirmas.get(position);
        holder.tvDescripcion.setText(firma.getDescripcion());

        // Convertir byte[] a Bitmap
        byte[] blob = firma.getFirmaDigital();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        holder.imgFirma.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return listaFirmas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescripcion;
        ImageView imgFirma;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            imgFirma = itemView.findViewById(R.id.imgFirma);
        }
    }
}

