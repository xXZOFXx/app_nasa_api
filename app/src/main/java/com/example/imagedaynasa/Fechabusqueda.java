package com.example.imagedaynasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.widget.ArrayAdapter;
public class Fechabusqueda extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public TextView respuesta;
    public ListView listado;
    public List<MyListItem> myListItems;
    private boolean isFirstDateSelected = false;
    private String firstDate = "";
    private String secondDate = "";
    private MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechabusqueda);

        Button boton = findViewById(R.id.btnuno);
        Button botondos = findViewById(R.id.btnfdos);
        Button botonBuscar = findViewById(R.id.btnbuscarr);

        listado = findViewById(R.id.lista);
        myListItems = new ArrayList<>();
        adapter = new MyCustomAdapter(this, R.layout.activity_list_view, myListItems);

        listado.setAdapter(adapter);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView valorunoxd = findViewById(R.id.txtfuno);
                TextView valordosfech = findViewById(R.id.txtfdos);
                String valorFechaUno = valorunoxd.getText().toString();
                String valorFechaDos = valordosfech.getText().toString();

                OkHttpClient client = new OkHttpClient();
                String url = "https://api.nasa.gov/planetary/apod?start_date=" + valorFechaUno +
                        "&end_date=" + valorFechaDos + "&api_key=YOUR_API";

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            try {
                                String responseBody = response.body().string();

                                JSONArray job = new JSONArray(responseBody);

                                myListItems.clear();

                                for (int i = 0; i < job.length(); ++i) {
                                    JSONObject rec = (JSONObject) job.get(i);

                                    String titulo = rec.getString("title");
                                    String descripcion = rec.getString("explanation");
                                    String imagen = rec.getString("url");
                                    String tipof = rec.getString("media_type");

                                    myListItems.add(new MyListItem(titulo, descripcion, imagen, tipof));
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        botondos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new Fecha();
                datePicker.show(getSupportFragmentManager(), "date picker two");
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new Fecha();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String formattedDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH).format(c.getTime());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateForDisplay = formatter.format(c.getTime());

        if (!isFirstDateSelected) {
            firstDate = formattedDateForDisplay;
            TextView textView = findViewById(R.id.txtfuno);
            textView.setText(formattedDateForDisplay);
            isFirstDateSelected = true;
        } else {
            secondDate = formattedDateForDisplay;
            TextView textView = findViewById(R.id.txtfdos);
            textView.setText(formattedDateForDisplay);
            isFirstDateSelected = false;
        }
    }
}
