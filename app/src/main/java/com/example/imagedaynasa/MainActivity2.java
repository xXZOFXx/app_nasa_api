package com.example.imagedaynasa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    public TextView txtString, descripcion;
    Button salvarimg;
    public ImageView imagen;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtString = findViewById(R.id.txtrespuesta);
        descripcion = findViewById(R.id.txtdescripcion);
        imagen = findViewById(R.id.imagenv);

        salvarimg = findViewById(R.id.btnguardar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.nasa.gov/planetary/apod?api_key=API_KEY";

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
                        String mailxd = responseObject.getString("title");
                        String desImg = responseObject.getString("explanation");
                        String linkImg = responseObject.getString("url");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtString.setText("copyright: " + mailxd);
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

        salvarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarGaleria();
            }
        });
    }

    private void salvarGaleria() {
        BitmapDrawable bitmapD = (BitmapDrawable) imagen.getDrawable();
        if (bitmapD != null) {
            Bitmap bitmap = bitmapD.getBitmap();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File directorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MySpaceimages");
                if (!directorio.exists()) {
                    if (!directorio.mkdirs()) {
                        Toast.makeText(this, "Error al crear el directorio", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                String nombreArchivo = String.format("%d.jpg", System.currentTimeMillis());
                File salidaArchivo = new File(directorio, nombreArchivo);

                try (FileOutputStream outputStream = new FileOutputStream(salidaArchivo)) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    Toast.makeText(this, "Imagen guardada en la galería", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "No se puede guardar la imagen en dispositivos anteriores a Android 10.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "La imagen no está disponible para guardar.", Toast.LENGTH_SHORT).show();
        }
    }
}
