package aau.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    /***
     * dont forget to add the http oder fehlerdings zum überprüfen
    ***/



    public static void main(String[] args) {

        String url = "https://klutronic.com";
        int depth = 2;

        Crawler crawl = new Crawler(url, depth,  "");

    }



}
