package me.taller.crudsqlite.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Donacion implements Parcelable {

    private String nombre;
    private String direccion;
    private String barrio;
    private String fechaCreacion;

    private String id; // El ID de la BD

    public Donacion(String id, String nombre, String direccion, String barrio, String fechaCreacion){
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaCreacion = fechaCreacion;
        this.barrio = barrio;
    }


    protected Donacion(Parcel in) {
        nombre = in.readString();
        direccion = in.readString();
        barrio = in.readString();
        fechaCreacion = in.readString();
        id = in.readString();
    }

    public static final Creator<Donacion> CREATOR = new Creator<Donacion>() {
        @Override
        public Donacion createFromParcel(Parcel in) {
            return new Donacion(in);
        }

        @Override
        public Donacion[] newArray(int size) {
            return new Donacion[size];
        }
    };

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    @Override
    public String toString() {
        return "Donacion{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", barrio='" + barrio + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(direccion);
        parcel.writeString(barrio);
        parcel.writeString(fechaCreacion);
        parcel.writeString(id);
    }
}
