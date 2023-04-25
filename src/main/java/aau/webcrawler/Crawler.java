package aau.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

    private ArrayList<String> visitedWebsites;

    private MDWriter mdWriter;

    private boolean overviewWritten;


    public Crawler(String websiteName, int depthToCrawl, String targetTranslationLanguage){

        this.overviewWritten = false;
        this.visitedWebsites = new ArrayList<>();
        this.mdWriter=new MDWriter(Variables.NAME_OF_OUTPUTFILE);

        String compactOverview = MDHelper.generateCompactOverview(websiteName, depthToCrawl, "sourceTranslationLanguage", targetTranslationLanguage);
        this.mdWriter.writeToFile(compactOverview);
        this.mdWriter.writeToFile("<br>summary:\n");

        crawl(depthToCrawl, websiteName);


    }

    private void crawl (int depth, String url) {
        if(depth >= 0 ) {
            url = URLValidation.fixURL(url);
            Document doc = request(url);
            if (doc!= null) {
                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    next_link = URLValidation.fixURL(next_link);
                    if(URLValidation.checkIfURLCorrect(next_link)){
                        if(visitedWebsites.contains(next_link) == false) {
                            crawl(depth-1, next_link);
                        }
                    }else{
                        System.out.println("Enter correct URL1: "+next_link);
                    }
                }
            }else{
                if(URLValidation.checkIfURLCorrect(url)){
                    System.out.println("Link "+url+" does not exist!");
                }else{
                    System.out.println("Enter correct URL2: "+url);
                }
            }
        }
    }

    private Document request(String url) {
        try {

            Connection.Response response = Jsoup.connect(url).userAgent("Mozilla").timeout(100000).ignoreContentType(true).ignoreHttpErrors(true).execute();

            Document document = response.parse();

            int responseCode = response.statusCode();

            if(URLValidation.checkIfHTTPStatusCodeOK(responseCode)) {
                mdWriter.writeToFile("<br>Link: " + url+"\n");
                System.out.println(document.title());
                visitedWebsites.add(url);
                return document;
            }else{
                System.out.println("Broken link: " + url);
                System.out.println(document.title());
                visitedWebsites.add(url);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
