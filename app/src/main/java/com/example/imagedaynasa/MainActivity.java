package com.example.imagedaynasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    Button botonnav,btnfech;

    public TextView valorFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonnav = findViewById(R.id.btnbuscar);
        btnfech = findViewById(R.id.btnfechas);



        btnfech.setOnClickListener(view -> {

                    Intent abrir = new Intent(getApplicationContext(), Fechabusqueda.class);

                    startActivity(abrir);



        });

        botonnav.setOnClickListener(view ->{


            valorFecha = findViewById(R.id.txtfecha);
            String fechaS = valorFecha.getText().toString();



            Intent abriruno = new Intent(getApplicationContext(), MainActivity2.class);
            abriruno.putExtra("fecha", fechaS);
            startActivity(abriruno);



        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar c = Calendar.getInstance();

            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String fechaActual = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH).format(c.getTime());
            /*dando formato a la fecha*/
        java.util.Date date = new Date(fechaActual);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);
        System.out.println(format);

        TextView textview = (TextView) findViewById(R.id.txtfecha);

        textview.setText(format);

    }
}