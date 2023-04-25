package aau.webcrawler;

import java.net.HttpURLConnection;

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

    public static boolean checkIfURLCorrect(String url){
        String regex = "(https?)://www.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if(url.matches(regex)){
            return true;
        }
        return false;
    }

}
