package aau.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Crawler {

    private ArrayList<String> visitedWebsites;

    private MDWriter mdWriter;

    private Translator translator;

    private int depthToCrawl;


    public Crawler(String websiteName, int depthToCrawl, String targetTranslationLanguage){

        this.visitedWebsites = new ArrayList<>();
        this.mdWriter=new MDWriter(Variables.NAME_OF_OUTPUTFILE);
        this.translator = new Translator(targetTranslationLanguage);
        this.depthToCrawl = depthToCrawl;

        String sourceLanguage = generateSourceLanguageOfWebsite(websiteName);

        String compactOverview = MDHelper.generateCompactOverview(websiteName, depthToCrawl, sourceLanguage, targetTranslationLanguage);
        this.mdWriter.writeToFile(compactOverview);
        this.mdWriter.writeToFile("<br>summary:\n");




    }

    private String generateSourceLanguageOfWebsite(String websiteName) {
        if(!URLValidation.checkIfURLValid(websiteName)){
            return "";
        }
        Document document;
        try {
            document = Jsoup.connect(websiteName).userAgent("Mozilla").timeout(100000).ignoreContentType(true).ignoreHttpErrors(true).execute().parse();
            String onlyTextOfWebsite = Jsoup.parse(document.outerHtml()).text();

            return translator.getSourceLanguage(onlyTextOfWebsite);
        } catch (IOException e) {
            return "";
        }
    }

    public void crawlThroughWebsite(int depth, String url) {
        if(depth >= 0 ) {
            url = URLValidation.fixURL(url);
            Document doc = requestURLAndWriteToFile(url, depth);
            if (doc!= null) {
                writeHeadersToFile(doc, depth);
                findNextLinkToCrawl(doc, depth);
            }
        }
    }

    private void findNextLinkToCrawl(Document doc, int depth) {
        for (Element link : doc.select("a[href]")) {
            String next_link = link.absUrl("href");
            next_link = URLValidation.fixURL(next_link);
            if(URLValidation.checkIfURLValid(next_link)){
                if(!visitedWebsites.contains(next_link)) {
                    crawlThroughWebsite(depth-1, next_link);
                }
            }
        }
    }

    private void writeHeadersToFile(Document document, int depth) {
        int [] headerCounter = new int[6];
        Arrays.fill(headerCounter, 1);

        for(Element header:document.select("h1, h2, h3, h4, h5, h6")){
            String tagName = header.tagName();
            int nrOfHeader = Integer.parseInt(tagName.charAt(1)+"");

            String headerCounterAtTheEnd = generateHeaderCounterAtTheEnd(headerCounter, nrOfHeader);
            String hashtagForHeadings = generateHashtags(nrOfHeader);
            String arrowStrShowingDepth = generateArrowStrShowingDepth(depth);

            headerCounter[nrOfHeader-1]++;
            String headerText;
            try {
                headerText = translator.translateText(header.text());
            } catch (IOException e) {
                headerText = header.text();
            }
            mdWriter.writeToFile(hashtagForHeadings+" "+arrowStrShowingDepth+headerText+" "+headerCounterAtTheEnd+"\n");
        }
    }

    private String generateHashtags(int nrOfHeader) {
        StringBuilder hashtags = new StringBuilder();
        for(int i = 0; i<nrOfHeader; i++){
            hashtags.append("#");
        }
        return hashtags.toString();
    }

    private String generateArrowStrShowingDepth(int currentDepth){
        StringBuilder arrowStr = new StringBuilder();
        for(int i = 0; i < depthToCrawl-currentDepth; i++){
            arrowStr.append("--");
        }
        if(arrowStr.length()!=0){
            arrowStr.append(">");
        }
        return arrowStr.toString();
    }

    private String generateHeaderCounterAtTheEnd(int[] headerCounter, int nrOfHeader) {
        StringBuilder headerCounterAtTheEnd = new StringBuilder();

        for(int i = 1; i<=nrOfHeader;i++){
            headerCounterAtTheEnd.append(headerCounter[i - 1]).append(".");
        }
        return headerCounterAtTheEnd.toString();
    }

    private Document requestURLAndWriteToFile(String url, int depth) {
        try {

            Connection.Response response = Jsoup.connect(url).userAgent("Mozilla").timeout(100000).ignoreContentType(true).ignoreHttpErrors(true).execute();

            Document document = response.parse();

            int responseCode = response.statusCode();

            String arrowStrShowingDepth = generateArrowStrShowingDepth(depth);

            if(URLValidation.checkIfHTTPStatusCodeOK(responseCode)) {
                mdWriter.writeToFile("<br>"+arrowStrShowingDepth+" Link: <a>" + url+" </a>\n");
                visitedWebsites.add(url);
                return document;
            }else{
                mdWriter.writeToFile("<br>"+arrowStrShowingDepth+" Broken Link: <a>" + url+" </a>\n");
                visitedWebsites.add(url);
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

}
