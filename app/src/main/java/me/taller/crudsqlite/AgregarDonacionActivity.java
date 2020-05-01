package me.taller.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import me.taller.crudsqlite.controllers.DonacionController;
import me.taller.crudsqlite.modelos.Donacion;

public class AgregarDonacionActivity extends AppCompatActivity {
    private Button btnAgregarDonacion, btnCancelarDonacion;
    private EditText etNombre, etDireccion;
    private DonacionController donacionController;
    private Spinner spinBarrios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);
        btnAgregarDonacion = findViewById(R.id.btnAgregarDonacion);
        btnCancelarDonacion = findViewById(R.id.btnCancelarDonacion);
        spinBarrios = findViewById(R.id.spin_barrios);

        // Crear el controlador
        donacionController = new DonacionController(AgregarDonacionActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarDonacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                String randomID = "USER"+ UUID.randomUUID().toString();
                etNombre.setError(null);
                etDireccion.setError(null);
                String nombre = etNombre.getText().toString(),
                direccion = etDireccion.getText().toString();
                String barrio = spinBarrios.getItemAtPosition(spinBarrios.getSelectedItemPosition()).toString();
                if ("".equals(nombre)) {
                    etNombre.setError("Digite un nombre");
                    etNombre.requestFocus();
                    return;
                }
                if ("".equals(direccion)) {
                    etDireccion.setError("Digite una direccion");
                    etDireccion.requestFocus();
                    return;
                }

            /*
                // Ver si es un entero
                int edad;
                try {
                    edad = Integer.parseInt(etEdad.getText().toString());
                } catch (NumberFormatException e) {
                    etEdad.setError("Escribe un número");
                    etEdad.requestFocus();
                    return;
                }
                */

                // Ya pasó la validación
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MMMM-dd HH:mm");
                String fecha = format.format(Calendar.getInstance().getTime());
                Donacion nuevaDonacion = new Donacion(randomID,nombre, direccion, barrio, fecha);

                long id = donacionController.nuevaDonacion(nuevaDonacion);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarDonacionActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }

            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarDonacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
