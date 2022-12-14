package com.example.alumnos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.alumnos.DbContactos;
import com.example.alumnos.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class VerActivity extends AppCompatActivity {

    EditText txtNombre, txtMatricula, txtApellidos, txtApellidoM,txtSexo,txtFecha;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;

    Contactos contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.txtNombre);
        txtMatricula = findViewById(R.id.txtMatricula);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtApellidoM = findViewById(R.id.txtApellidoM);
        txtSexo = findViewById(R.id.txtSexo);
        txtFecha = findViewById(R.id.txtFecha);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbContactos dbContactos = new DbContactos(VerActivity.this);
        contacto = dbContactos.verContacto(id);

        if(contacto != null){
            txtNombre.setText(contacto.getNombre());
            txtMatricula.setText(contacto.getMatricula());
            txtApellidos.setText(contacto.getApellidos());
            txtApellidoM.setText(contacto.getApellidoM());
            txtSexo.setText(contacto.getSexo());
            txtFecha.setText(contacto.getFecha_nacimiento());
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtMatricula.setInputType(InputType.TYPE_NULL);
            txtApellidos.setInputType(InputType.TYPE_NULL);
            txtApellidoM.setInputType(InputType.TYPE_NULL);
            txtSexo.setInputType(InputType.TYPE_NULL);
            txtFecha.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este alumno?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(dbContactos.eliminarContacto(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void abrircalendario(View view)
    {
        Calendar cal = Calendar.getInstance();
        int año = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(VerActivity.this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth)
                    {
                        String fecha = dayofMonth + "/" + (month+1) + "/" + year;
                        txtFecha.setText(fecha);
                    }
                }, año, mes, dia);
        dpd.show();

    }
}