package aau.webcrawler;

import java.util.Scanner;

public class WebCrawler {


    private Scanner inScanner;

    private String url;
    private int depth;
    private String targetLanguage;

    public WebCrawler(){
        this.inScanner = new Scanner(System.in);
        readInput();

        Crawler crawler = new Crawler(this.url, this.depth, this.targetLanguage);

    }

    private void readInput() {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Enter the input parameters for the webcrawler, use space (' ') as delimiter");
        System.out.println("First input the URL, next the depth of websites to crawl, and last the target language based on ISO 639-1; e.g.:");
        System.out.println("google.at 2 en");
        System.out.println("Different formats than this are not going to be accepted!");
        System.out.println("----------------------------------------------------------------------");
        String input = inScanner.nextLine();

        String [] splittedInput = input.split(" ");

        if(splittedInput.length!=3){
            System.out.println("Enter correct amount of inputs!");
            System.exit(0);
        }
        if(!splittedInput[0].matches("[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")){
            System.out.println("Enter correct website!");
            System.exit(0);
        }

        if(!splittedInput[1].matches("[0-9]")){
            System.out.println("Enter correct depth of website");
            System.exit(0);
        }

        if(splittedInput[2].length()>5||splittedInput[2].length()<2||!splittedInput[2].matches("[a-z-]*")){
           System.out.println("Enter correct target language according to ISO-639-1");
           System.exit(0);
        }

        this.url = splittedInput[0];
        this.depth = Integer.parseInt(splittedInput[1]);
        this.targetLanguage = splittedInput[2];

    }

}
