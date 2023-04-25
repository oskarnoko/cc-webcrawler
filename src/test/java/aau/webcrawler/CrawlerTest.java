package aau.webcrawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CrawlerTest {

    private Crawler crawler;

    public static final String NAME_OF_OUTPUTFILE = "report-of-website.md";

    @Before
    public void setUp(){
        this.crawler = new Crawler("google.at", 0, "en");
    }

    @Test
    public void testIfCrawlerExists(){

        Assert.assertNotNull(crawler);
    }

    @Test
    public void testIfCrawlerFileExists() throws FileNotFoundException {
        this.crawler.crawlThroughWebsite(0, "google.at");

        int counter = 1;
        String [] splitFileName = NAME_OF_OUTPUTFILE.split("[.]");
        String outputFileName = splitFileName[0]+"_"+counter+"."+splitFileName[1];
        File outputFile = new File(outputFileName);


        while(outputFile.exists()){
            outputFile = new File(splitFileName[0]+"_"+counter+"."+splitFileName[1]);
            counter++;
        }
        counter-=2;
        outputFile = new File(splitFileName[0]+"_"+counter+"."+splitFileName[1]);
        Scanner reader = new Scanner(outputFile);
        String expectedOutput = "input: <a>google.at</a>" +
                "<br>depth: 0" +
                "<br>source language: " +
                "<br>target language: en" +
                "<br>summary:" +
                "<br> Link: <a>http://google.at </a>";
        String actualOutput = "";
        for(int i = 0; i<6; i++){
            actualOutput+= reader.nextLine();
        }
        reader.close();
        Assert.assertEquals(expectedOutput, actualOutput);
    }

}
