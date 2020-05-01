package me.taller.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.taller.crudsqlite.controllers.DonacionController;
import me.taller.crudsqlite.modelos.Donacion;

public class EditarDonacionActivity extends AppCompatActivity {
    private EditText etEditarNombre, etEditarDireccion;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Donacion donacion;//La donacion que vamos a estar editando
    private DonacionController donacionController;
    private Spinner spinBarrios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        donacionController = new DonacionController(EditarDonacionActivity.this);

        // Rearmar la donacion
        // Nota: igualmente solamente podríamos mandar el id y recuperar la donacion de la BD

        donacion = getIntent().getExtras().getParcelable("donacion");

        // Ahora declaramos las vistas
        etEditarDireccion = findViewById(R.id.etEditarDireccion);
        etEditarNombre = findViewById(R.id.etEditarNombre);
        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionDonacion);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosDonacion);
        spinBarrios = findViewById(R.id.spin_barrios);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.barrios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBarrios.setAdapter(adapter);


        // Rellenar los EditText con los datos de la mascota
        etEditarDireccion.setText(String.valueOf(donacion.getDireccion()));
        etEditarNombre.setText(donacion.getNombre());
        setDataSpinBarrio(adapter);
        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etEditarNombre.setError(null);
                etEditarDireccion.setError(null);
                // Crear la Donacion con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = etEditarNombre.getText().toString();
                String nuevaDireccion = etEditarDireccion.getText().toString();
                String nuevoBarrio = spinBarrios.getItemAtPosition(spinBarrios.getSelectedItemPosition()).toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MMMM-dd HH:mm");
                String nuevafecha = format.format(Calendar.getInstance().getTime());//get instance date
                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Digite el Nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if (nuevaDireccion.isEmpty()) {
                    etEditarDireccion.setError("Digite la Direccion");
                    etEditarDireccion.requestFocus();
                    return;
                }

                //validacion comentada
                // Si no es entero, igualmente marcar error

                /*
                int nuevaEdad;
                try {
                    nuevaEdad = Integer.parseInt(posibleNuevaEdad);
                } catch (NumberFormatException e) {
                    etEditarEdad.setError("Escribe un número");
                    etEditarEdad.requestFocus();
                    return;
                }+*/

                // Si llegamos hasta aquí es porque los datos ya están validados
                donacion.setNombre(nuevoNombre);
                donacion.setDireccion(nuevaDireccion);
                donacion.setBarrio(nuevoBarrio);
                donacion.setFechaCreacion(nuevafecha);

                int filasModificadas = donacionController.guardarCambios(donacion);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarDonacionActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }

    private void setDataSpinBarrio(ArrayAdapter<CharSequence> adapter) {
        int spinnerPosition = adapter.getPosition(donacion.getBarrio());
        spinBarrios.setSelection(spinnerPosition);
    }
}
