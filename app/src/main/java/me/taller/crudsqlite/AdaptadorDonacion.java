package me.taller.crudsqlite;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.taller.crudsqlite.modelos.Donacion;

public class AdaptadorDonacion extends RecyclerView.Adapter<AdaptadorDonacion.MyViewHolder> {

    private List<Donacion> listaDeDonaciones;

    public void setListaDeDonaciones(List<Donacion> listaDeDonaciones) {
        this.listaDeDonaciones = listaDeDonaciones;
    }

    public AdaptadorDonacion(List<Donacion> donaciones) {
        this.listaDeDonaciones = donaciones;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaDonacion = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fil_donacion, viewGroup, false);
        return new MyViewHolder(filaDonacion);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la mascota de nuestra lista gracias al índice i
        Donacion donacion = listaDeDonaciones.get(i);

        // Obtener los datos de la lista
        String nombre = donacion.getNombre();
        String direccion = donacion.getDireccion();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombre);
        myViewHolder.direccion.setText(direccion+" "+donacion.getBarrio());
        myViewHolder.fecha.setText(donacion.getFechaCreacion());
    }

    @Override
    public int getItemCount() {
        return listaDeDonaciones.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, direccion, fecha;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.direccion = itemView.findViewById(R.id.tvDireccion);
            this.fecha = itemView.findViewById(R.id.txt_fecha);
        }
    }
}
