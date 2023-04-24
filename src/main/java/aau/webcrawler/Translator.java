package aau.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Translator {

    private String sourceLanguage;
    private String targetLanguage;
    private String apiKey = "514029dd4amshfb834a2dc204ac9p15e59ajsn4de57f356dd4";
    private String url = "https://google-translator9.p.rapidapi.com/v2";

    public Translator(String sourceLanguage, String targetLanguage) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    private String createJsonDataToBeTransferred(String textTotranslate){
        return "{\r\"q\": \""+textTotranslate+"\",\r\"source\": \""+sourceLanguage+"\",\r\"target\": \""+targetLanguage+"\",\r\"format\": \"text\"\r}";
    }

    private Connection createConnection(String textTotranslate){
        String data = createJsonDataToBeTransferred(textTotranslate);
        Connection connection =  Jsoup.connect(url)
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "google-translator9.p.rapidapi.com")
                .requestBody(data)
                .method(Connection.Method.POST)
                .ignoreContentType(true);
        return connection;
    }

    private Connection.Response executeConnectionToTranslator(Connection connection) throws IOException {
        return connection.execute();
    }

    private String returnOnlyTheTranslationOfJson(String jsonString){
        String [] jsonLines = jsonString.split("\n");
        String formattedString = "";
        for(int i = 0; i<jsonLines.length; i++){
            if(jsonLines[i].contains("translatedText")){
                formattedString=formatTheJsonLineOfTheTranslatedText(jsonLines[i]);
                break;
            }
        }
        return formattedString;
    }
    private String formatTheJsonLineOfTheTranslatedText(String jsonLineOfTheTranslatedText) {
        String [] splitJsonLine = jsonLineOfTheTranslatedText.split("\"");
        return splitJsonLine[3];
    }

    public String translateText(String textTotranslate) throws IOException {
        Connection connection= createConnection(textTotranslate);
        Connection.Response response = executeConnectionToTranslator(connection);
        String json = response.body();
        String translatedText = returnOnlyTheTranslationOfJson(json);
        return translatedText;
    }

}
