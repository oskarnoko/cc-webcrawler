package aau.webcrawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;

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
    public void testIfCrawlerFileExists(){
        File outputFile;
        String [] splitFileName = NAME_OF_OUTPUTFILE.split("[.]");
        File file = new File();
        int counter = 1;
        String outputFile = splitFileName[0]+"_"+counter+"."+splitFileName[1];



        while(outputFile.exists()){
            outputFile = renameFileName(splitFileName[0]+"_"+counter+"."+splitFileName[1]);
            counter++;
        }
    }

}
