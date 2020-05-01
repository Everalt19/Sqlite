package me.taller.crudsqlite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.taller.crudsqlite.AuxBaseDatos;
import me.taller.crudsqlite.modelos.Donacion;


public class DonacionController {
    private AuxBaseDatos auxBaseDatos;
    private String NOMBRE_TABLA = "donaciones";

    public DonacionController(Context contexto) {
        auxBaseDatos = new AuxBaseDatos(contexto);
    }


    public int eliminarDonacion(Donacion donacion) {

        SQLiteDatabase baseDeDatos = auxBaseDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(donacion.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaDonacion(Donacion donacion) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = auxBaseDatos.getWritableDatabase();
        ContentValues valoresInsertar = new ContentValues();
        valoresInsertar.put("id",donacion.getId());
        valoresInsertar.put("nombre", donacion.getNombre());
        valoresInsertar.put("direccion", donacion.getDireccion());
        valoresInsertar.put("barrio",donacion.getBarrio());
        valoresInsertar.put("fecha", donacion.getFechaCreacion());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresInsertar);
    }

    public int guardarCambios(Donacion donacionEditada) {
        SQLiteDatabase baseDeDatos = auxBaseDatos.getWritableDatabase();
        ContentValues valoresActualizar = new ContentValues();
        valoresActualizar.put("nombre", donacionEditada.getNombre());
        valoresActualizar.put("direccion", donacionEditada.getDireccion());
        valoresActualizar.put("barrio", donacionEditada.getBarrio());
        valoresActualizar.put("fecha", donacionEditada.getFechaCreacion());
        // where id...
        String campoActualizar = "id = ?";
        // ... = idDonacion
        String[] argumentosParaActualizar = {String.valueOf(donacionEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresActualizar, campoActualizar, argumentosParaActualizar);
    }

    public ArrayList<Donacion> obtenerDonacion() {
        ArrayList<Donacion> donaciones = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = auxBaseDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"id, nombre", "direccion","barrio","fecha" };
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from donaciones
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return donaciones;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return donaciones;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de donaciones
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String idObtenidoDeBD = cursor.getString(0);
            String nombreObtenidoDeBD = cursor.getString(1);
            String direccionObtenidaDeBD = cursor.getString(2);
            String barrioObtenidaDeBD = cursor.getString(3);
            String fechaObtenidadeBD = cursor.getString(4);

            Donacion donacionObtenidaDeBD = new Donacion(idObtenidoDeBD,nombreObtenidoDeBD, direccionObtenidaDeBD, barrioObtenidaDeBD, fechaObtenidadeBD);
            donaciones.add(donacionObtenidaDeBD);



        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de mascotas :)
        cursor.close();
        return donaciones;
    }
}