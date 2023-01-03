package com.torrober.colomboradios;

public class Estacion {
    String url;
    String img;
    String nombre;

    public Estacion(String url, String img, String nombre) {
        this.url = url;
        this.img = img;
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
