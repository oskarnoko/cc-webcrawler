package aau.webcrawler;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class URLValidation {

    public static String fixURL(String url){
        if(!url.contains("http")){
            url = "http://"+url;
        }
        return url;
    }

    public static boolean checkIfHTTPStatusCodeOK(int statusCode){
        if (statusCode == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfURLValid(String url){
        try {
            URL urlToCheck = new URL(url);
            // Check for valid protocol (http, https, ftp, etc.)
            urlToCheck.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            // URL is not valid
            return false;
        }
    }

}
