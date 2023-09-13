package com.example.imagedaynasa;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<MyListItem> {

    public MyCustomAdapter(Context context, int activity_list_view, List<MyListItem> items) {
        super(context, activity_list_view, items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyListItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_view, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titulon);
        TextView descriptionTextView = convertView.findViewById(R.id.txtdescrip);
        ImageView urlTextView = convertView.findViewById(R.id.txturl);
        WebView videoUrl = convertView.findViewById(R.id.videov);

        titleTextView.setText(item.getTitle());
        descriptionTextView.setText(item.getDescription());

        if ("video".equals(item.getTipo())) {
            // Si es un video, configura la vista de video
            videoUrl.setVisibility(View.VISIBLE);
            urlTextView.setVisibility(View.GONE);

            String videoset = "<iframe width=\"100%\" height=\"100%\" src=" + item.getUrlImagen() + " title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
            videoUrl.loadData(videoset, "text/html", "utf-8");
            videoUrl.getSettings().setJavaScriptEnabled(true);
            videoUrl.setWebChromeClient(new WebChromeClient());
        } else {
            // Si no es un video, configura la vista de imagen
            videoUrl.setVisibility(View.GONE);
            urlTextView.setVisibility(View.VISIBLE);

            Picasso.get().load(item.getUrlImagen()).into(urlTextView);
        }

        return convertView;
    }

}
