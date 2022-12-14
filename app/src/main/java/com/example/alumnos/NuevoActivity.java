package com.example.alumnos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alumnos.DbContactos;

import java.util.Calendar;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtMatricula, txtApellidos, txtApellidoM,txtSexo,txtFecha;
    Button btnGuarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtMatricula = findViewById(R.id.txtMatricula);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtApellidoM = findViewById(R.id.txtApellidoM);
        txtSexo=findViewById(R.id.txtSexo);
        txtFecha=findViewById(R.id.txtFecha_nacimiento);
        btnGuarda = findViewById(R.id.btnGuarda);

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNombre.getText().toString().equals("") && !txtMatricula.getText().toString().equals("") && !txtApellidos.getText().toString().equals("") && !txtApellidoM.getText().toString().equals("") && !txtSexo.getText().toString().equals("") && !txtFecha.getText().toString().equals("")) {

                    DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                    long id = dbContactos.insertarContacto(txtNombre.getText().toString(), txtMatricula.getText().toString(), txtApellidos.getText().toString(),txtApellidoM.getText().toString(), txtSexo.getText().toString(), txtFecha.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                        Intent intent = new Intent(NuevoActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NuevoActivity.this, "LA MATRICULA YA EXISTE", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        txtMatricula.setText("");
        txtApellidos.setText("");
        txtSexo.setText("");
        txtFecha.setText("");
    }

    public void abrircalendario(View view)
    {
        Calendar cal = Calendar.getInstance();
        int año = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(NuevoActivity.this,
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