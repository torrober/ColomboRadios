/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torrober.colomboradios;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author graba
 */
public class Estaciones {

    public static ArrayList<String> obtenerEstacionesByUrl(String url) {
        Elements radios = Scrap.getHTML(url).select("ul#radios").select("li");
        ArrayList<String> datos = new ArrayList();
        for (Element radio : radios) {
            try {
                String nombre = radio.select("img").attr("abs:alt");
                String img = radio.select("img").attr("abs:src");
                String link = radio.select("a").attr("abs:href");
                String id = link.split("#")[1];
                nombre = nombre.replace(url, "");
                datos.add(nombre+","+link+","+id+","+img);
            } catch (Exception e) {
                //System.out.println(e);
            }
        }
        return datos;
    }
    public static void ReproducirEstacion (String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("streams");
                            JSONObject radio = jsonArray.getJSONObject(1);
                            String mediaType = radio.getString("mediaType");
                            if(mediaType != "HTML"){
                                System.out.println(radio.getString("url"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
