package aau.webcrawler;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WebCrawler {

    public static ConcurrentLinkedQueue visitedWebsites;

    private final Scanner inScanner= new Scanner(System.in);

    private String url;
    private int depth;
    private String targetLanguage;

    public WebCrawler(){
        readInput();

        visitedWebsites = new ConcurrentLinkedQueue();

        Crawler crawler = new Crawler(this.url, this.depth, this.targetLanguage);

        crawler.crawlThroughWebsite(this.depth, this.url);

    }

    private void readInput() {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Enter the input parameters for the webcrawler, use space (' ') as delimiter");
        System.out.println("First input the URL, next the depth of websites to crawl, and last the target language based on ISO 639-1; e.g.:");
        System.out.println("google.at 2 en");
        System.out.println("Different formats than this are not going to be accepted!");
        System.out.println("----------------------------------------------------------------------");
        String input = inScanner.nextLine();

        String [] splitInput = input.split(" ");

        if(splitInput.length!=3){
            System.out.println("Enter correct amount of inputs!");
            System.exit(0);
        }
        if(!splitInput[0].matches("[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")){
            System.out.println("Enter correct website!");
            System.exit(0);
        }

        if(!splitInput[1].matches("[0-9]")){
            System.out.println("Enter correct depth of website");
            System.exit(0);
        }

        if(splitInput[2].length()>5||splitInput[2].length()<2||!splitInput[2].matches("[a-z-]*")){
           System.out.println("Enter correct target language according to ISO-639-1");
           System.exit(0);
        }

        this.url = splitInput[0];
        this.depth = Integer.parseInt(splitInput[1]);
        this.targetLanguage = splitInput[2];

    }

}
