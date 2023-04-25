package aau.webcrawler;

public class MDHelper {
    public static String generateCompactOverview(String websiteName, int depthToCrawl, String sourceLanguage, String targetTranslationLanguage){
        String compactOverview = "input: " + "<a>"+websiteName+"</a\n" +
                "<br>depth: "+depthToCrawl+"\n" +
                "<br>source language: "+sourceLanguage+"\n" +
                "<br>target language: "+targetTranslationLanguage+"\n";
        return compactOverview;
    }


}
