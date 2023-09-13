package com.example.imagedaynasa;

public class MyListItem {
    private String title;
    private String description;
    private String urlImagen;

    private String tipo;

    public MyListItem(String title, String description, String url, String tipo) {
        this.title = title;
        this.description = description;

        this.urlImagen = url;
        this.tipo = tipo;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;

    }
    public String getUrlImagen(){

        return urlImagen;

    }

    public String getTipo() {
        return tipo;
    }
}
