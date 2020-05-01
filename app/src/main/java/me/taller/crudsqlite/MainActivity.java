package me.taller.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.taller.crudsqlite.controllers.DonacionController;
import me.taller.crudsqlite.modelos.Donacion;

public class MainActivity extends AppCompatActivity {
    private List<Donacion> listaDeDonaciones;
    private RecyclerView recyclerView;
    private AdaptadorDonacion adaptadorDonaciones;
    private DonacionController donacionController;
    private FloatingActionButton fabAgregarDonacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ojo: este código es generado automáticamente, pone la vista y ya, pero
        // no tiene nada que ver con el código que vamos a escribir
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        donacionController = new DonacionController(MainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewDonaciones);
        fabAgregarDonacion = findViewById(R.id.fabAgregarDonacion);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeDonaciones = new ArrayList<>();

        adaptadorDonaciones = new AdaptadorDonacion(listaDeDonaciones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorDonaciones);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeDonaciones();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarDonacionActivity.java
                Donacion donacionSeleccionada =  listaDeDonaciones.get(position);
                Intent intent = new Intent(MainActivity.this, EditarDonacionActivity.class);
                intent.putExtra("donacion", donacionSeleccionada);
                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Donacion donacionParaEliminar = listaDeDonaciones.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                donacionController.eliminarDonacion(donacionParaEliminar);
                                refrescarListaDeDonaciones();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Eliminar ! ")
                        .setMessage("¿Eliminar registro de : " + donacionParaEliminar.getNombre() + " ?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarDonacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarDonacionActivity.class);
                startActivity(intent);
            }
        });

        // Créditos
        /*
        fabAgregarDonacion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Info")
                        .setMessage("Desarrollo de nuevas tecnologias")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })
                        .setPositiveButton("Sitio web", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parzibyte.me"));
                                startActivity(intentNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });
        */


    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeDonaciones();
    }

    public void refrescarListaDeDonaciones() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorDonaciones == null) return;
        listaDeDonaciones = donacionController.obtenerDonacion();
        adaptadorDonaciones.setListaDeDonaciones(listaDeDonaciones);
        adaptadorDonaciones.notifyDataSetChanged();

    }
}
