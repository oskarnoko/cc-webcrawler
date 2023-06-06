# cc-webcrawler
This is a webcrawler for the course 'Clean Code'

After starting the WebCrawler you will be asked to enter the following input

----------------------------------------------------------------------
Enter the input parameters for the webcrawler, use space (' ') as delimiter
First input the URL, next the depth of websites to crawl, and last the target language based on ISO 639-1 (all without extra spaces); e.g.:
google.at 2 en
Different formats than this are not going to be accepted!
----------------------------------------------------------------------

Press ENTER to confirm the input

Implementation explanation:
The Main method creates a object of the type WebCrawler, in the WebCrawler everything is initialized and it submits a task to the ExecutorService(Thread-Pool), which starts a new thread and tells the Crawler to start crawling (task=crawlThroughWebsites()).
On start the crawler generates a completely new file as well as a compact overview of the site. The completely new file is created for comparing multiple websites to each other.
Whenever a heading is found it's translated to the language and written to the file with some intendation to show on which depth the heading is.
Whenever a new site is found it's written as a link to the file and the crawler submits another task to the ExecutorService for this website again (translating and writing all the headings with intendations).
When no more Threads are crawling through a website in the ExecutorService, the ExecutorService is shutdown().

Testing explanation:
The Translator, URLValidation and MDHelper were tested, as well as the Crawler. 
