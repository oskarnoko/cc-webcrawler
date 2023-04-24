package aau.webcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MDWriter {
    private BufferedWriter bufferedWriter;
    private String nameOfFile;

    public MDWriter(String nameOfFile){
        try {
            this.nameOfFile = nameOfFile;
            createCompletelyNewFile();
            initializeWriter();
        } catch (IOException e) {
            System.out.println("Enter correct name of file!");
            e.printStackTrace();
        }
    }

    /***
     * Creates a new File, that does not exist by adding _Nr to the name of the file
     * @throws IOException when the file does not exist
     */
    private void createCompletelyNewFile() throws IOException{
        File outputFile = createFileThatDoesNotExistAndRenameNameOfFile();
        outputFile.createNewFile();
    }

    private File createFileThatDoesNotExistAndRenameNameOfFile(){
        File outputFile;
        String [] splitFileName = nameOfFile.split("[.]");
        //keep adding _Nr; e.g. _1 until file doesn't exist and can be created
        int counter = 1;
        outputFile = renameFileName(splitFileName[0]+"_"+counter+"."+splitFileName[1]);
        while(outputFile.exists()){
            outputFile = renameFileName(splitFileName[0]+"_"+counter+"."+splitFileName[1]);
            counter++;
        }
        return outputFile;
    }

    private File renameFileName(String nameOfNewFile){
        nameOfFile = nameOfNewFile;
        return new File(nameOfFile);
    }

    private void initializeWriter() throws IOException{
        FileWriter fw = new FileWriter(nameOfFile, true);
        bufferedWriter = new BufferedWriter(fw);
    }

    /**
     * Writes a String to the beforehand declared path
     * @param str String to be written to the beforehand declared path
     */
    public void writeToFile(String str) {
        try {
            this.bufferedWriter.write(str);
            this.bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
