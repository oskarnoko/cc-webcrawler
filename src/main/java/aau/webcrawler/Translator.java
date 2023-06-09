package aau.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Translator {

    private String sourceLanguage;
    private String targetLanguage;
    private final String url = "https://google-translator9.p.rapidapi.com/v2";

    public Translator(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    private String createJsonDataToBeTranslated(String textTotranslate) throws IOException {
        if(sourceLanguage==null){
            sourceLanguage = this.getSourceLanguage(textTotranslate);
        }
        return "{\r\"q\": \""+textTotranslate+"\",\r\"source\": \""+sourceLanguage+"\",\r\"target\": \""+targetLanguage+"\",\r\"format\": \"text\"\r}";
    }

    private String createJsonDataToBeLanguageDetected(String textToDetectLanguage){
        return "{\r\n    \"q\": \"" + textToDetectLanguage + "\"\r\n}";
    }

    private Connection createConnectionToRapidAPIGoogleTranslator(String jsonDataToBeTransferred, String urlOfAPI){
        Connection connection =  Jsoup.connect(urlOfAPI)
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", Variables.RAPID_API_KEY)
                .header("X-RapidAPI-Host", Variables.RAPIC_API_HOST)
                .requestBody(jsonDataToBeTransferred)
                .method(Connection.Method.POST)
                .ignoreContentType(true);
        return connection;
    }

    private String returnOnlyTheTranslationOfJson(String jsonString){
        String [] jsonLines = jsonString.split("\n");
        String formattedString = "";
        for (String jsonLine : jsonLines) {
            if (jsonLine.contains("translatedText")) {
                formattedString = formatTheJsonLineOfTheTranslatedText(jsonLine);
                break;
            }
        }
        return formattedString;
    }

    private String returnOnlyTheLanguageDetectionOfJson(String jsonString){
        String [] jsonLines = jsonString.split("\n");
        String formattedString = "";
        for (String jsonLine : jsonLines) {
            if (jsonLine.contains("language")) {
                formattedString = formatTheJsonLineOfTheTranslatedText(jsonLine);
                break;
            }
        }
        return formattedString;
    }
    private String formatTheJsonLineOfTheTranslatedText(String jsonLineOfTheTranslatedText) {

        String [] splitJsonLine = jsonLineOfTheTranslatedText.split("\"");
        if(splitJsonLine.length<4){
            return "";
        }
        return splitJsonLine[3];
    }

    private String sendJsonDataToAPIURLAndReturnReceivedJson(String jsonData, String url) throws IOException{
        Connection connection= createConnectionToRapidAPIGoogleTranslator(jsonData, url);
        Connection.Response response = connection.execute();
        String receivedJson = response.body();
        return receivedJson;
    }

    public synchronized String translateText(String textToTranslate) throws IOException {
        String jsonData = createJsonDataToBeTranslated(textToTranslate);
        String receivedJson = sendJsonDataToAPIURLAndReturnReceivedJson(jsonData, url);
        String translatedText = returnOnlyTheTranslationOfJson(receivedJson);
        return translatedText;
    }

    public synchronized String getSourceLanguage(String detectLanguageOfThisString) throws IOException{
        if(sourceLanguage==null){
            String jsonData = createJsonDataToBeLanguageDetected(detectLanguageOfThisString);
            String receivedJson = sendJsonDataToAPIURLAndReturnReceivedJson(jsonData,url+"/detect");
            sourceLanguage = returnOnlyTheLanguageDetectionOfJson(receivedJson);
        }
        return sourceLanguage;
    }

}
