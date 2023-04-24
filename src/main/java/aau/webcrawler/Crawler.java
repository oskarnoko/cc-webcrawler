package aau.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

    private ArrayList<String> visitedWebsites;

    public Crawler(String websiteName, int depth, String targetLanguage){

    }

    private String fixURL(String url){

        if(!url.contains("http")){
            url = "http://"+url;
        }
        if(!url.contains("www.")){
            String [] splittedURL = url.split("//");
            url = splittedURL[0] +"//"+ "www."+splittedURL[1];
        }

        return url;
    }
    private boolean checkIfURLCorrect(String url){
        String regex = "\\b(https?)://www.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if(url.matches(regex)){
            return true;
        }
        return false;
    }
    private void crawl (int depth, String url) {
        if(depth >= 0 ) {
            url = fixURL(url);
            Document doc = request(url);
            if (doc!= null) {
                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    next_link = fixURL(next_link);
                    if(checkIfURLCorrect(next_link)){
                        if(visitedWebsites.contains(next_link) == false) {
                            crawl(depth-1, next_link);
                        }
                    }else{
                        System.out.println("Enter correct URL: "+next_link);
                    }
                }
            }else{
                if(checkIfURLCorrect(url)){
                    System.out.println("Link "+url+" does not exist!");
                }else{
                    System.out.println("Enter correct URL: "+url);
                }
            }
        }
    }

    private Document request(String url) {
        try {

            Connection.Response response = Jsoup.connect(url).userAgent("Mozilla").timeout(100000).ignoreContentType(true).ignoreHttpErrors(true).execute();

            Document document = response.parse();

            int responseCode = response.statusCode();

            if(responseCode == 200) {
                System.out.println("Link: " + url);
                System.out.println(document.title());
                visitedWebsites.add(url);
                return document;
            }else if(responseCode > 200&&responseCode<300) {
                System.out.println("Link: " + url + " Response code: "+responseCode);
                System.out.println(document.title());
                visitedWebsites.add(url);
                return document;
            }else{
                System.out.println("Broken link: " + url);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
