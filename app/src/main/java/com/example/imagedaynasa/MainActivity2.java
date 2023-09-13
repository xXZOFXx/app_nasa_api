package com.example.imagedaynasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    public TextView fechaset, txtString, descripcion;

    public ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtString = findViewById(R.id.txtrespuesta);
        fechaset = findViewById(R.id.txtfechav2);
        descripcion = findViewById(R.id.txtdescripcion);
        imagen = findViewById(R.id.imagenv);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extra = intent.getExtras();
            if (extra != null) {
                String valorRe = extra.getString("fecha");

                System.out.println("fecha chida xd " + valorRe);

                fechaset.setText(valorRe);
            }
        }

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.nasa.gov/planetary/apod?api_key=YOUR_API";

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
                        System.out.println("respuesta alv");

                        System.out.println(responseBody);
                        JSONObject responseObject = new JSONObject(responseBody);
                      /*  JSONObject dataObject = responseObject.getJSONObject("data");*/
                        String mailxd = responseObject.getString("title");
                        String desImg = responseObject.getString("explanation");
                        String linkImg = responseObject.getString("url");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtString.setText("copyright: "+mailxd);
                                descripcion.setText(desImg);
                                Picasso.get().load(linkImg).into(imagen);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
