/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torrober.colomboradios;

import org.jsoup.*;
import org.jsoup.nodes.Document;

/**
 *
 * @author graba
 */
public class Scrap {

    public static Document getHTML(String url) {
        Document html = null;
        try {
            //se obtiene el codigo HTML de una pagina web
            html = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (Exception e) {
            System.out.println("Error al obtener el codigo HTML");
        }
        return html;
    }
}
