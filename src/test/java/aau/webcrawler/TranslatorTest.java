package aau.webcrawler;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import java.io.IOException;

public class TranslatorTest {
    private Translator testTranslator;
    private String sourceLanguage;
    private String targetLanguage;
    @Before
    public void setUp(){
        sourceLanguage = "en";
        targetLanguage = "de";
        testTranslator = new Translator(targetLanguage);
    }
    @Test
    public void translatorExists(){
        Assert.assertNotNull(testTranslator);
    }
    @Test
    public void testTranslateTextToCorrectLanguageFromCorrectLanguage() throws IOException{
        String expectedStr = "Hallo Welt!";
        String sourceStr = "Hello world!";
        String actualStr = testTranslator.translateText(sourceStr);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void testTranslateTextToCorrectLanguageFromWrongLanguage() throws IOException{
        String expectedStr = "Hallo Welt";
        String sourceStr = "Bonjour le monde";
        String actualStr = testTranslator.translateText(sourceStr);
        Assert.assertNotEquals(expectedStr, actualStr);
    }

    @Test
    public void testTranslateTextToWrongLanguageFromCorrectLanguage() throws IOException{
        String expectedStr = "Bonjour le monde";
        String sourceStr = "Hello world";
        String actualStr = testTranslator.translateText(sourceStr);
        Assert.assertNotEquals(expectedStr, actualStr);
    }

    @Test
    public void testDetectLanguage1() throws IOException{
        String testStr = "Hallo welt";
        String expected = "de";
        String actualStr = testTranslator.getSourceLanguage(testStr);
        Assert.assertEquals(expected, actualStr);
    }
    @Test
    public void testDetectLanguage2() throws IOException{
        String testStr = "Hello world!";
        String expected = "en";
        String actualStr = testTranslator.getSourceLanguage(testStr);
        Assert.assertEquals(expected, actualStr);
    }

}
