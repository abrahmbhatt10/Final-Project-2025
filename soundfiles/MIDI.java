import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MIDI {
    private boolean[][] inputMelody1;
    private boolean[][] inputMelody2;
    private boolean[][] outputMelody;
    private int scaleLen = 7;
    private int timeSlots = 64;

    public int getTimeSlots() {
        return timeSlots;
    }

    public boolean[][] getInputMelody1() {
        return inputMelody1;
    }
    public boolean[][] getInputMelody2() {
        return inputMelody2;
    }

    public String[] getSongNames() {
        return songNames;
    }

    public String[] getSongFilenames() {
        return songFilenames;
    }

    public int getTotalSongs() {
        return totalSongs;
    }

    String[] songNames;
    String[] songFilenames;
    int totalSongs;

    public MIDI() {
        inputMelody1 = new boolean[scaleLen][64];
        inputMelody2 = new boolean[scaleLen][64];
        outputMelody = new boolean[scaleLen][64];
    }

    public int getScaleLen() {
        return scaleLen;
    }

    public void setScaleLen(int scaleLen) {
        this.scaleLen = scaleLen;
    }

    public boolean[][] getOutputMelody() {
        return outputMelody;
    }

    public String[] readSongsList(String soundFileName){
        // Open files
        String line;
        int lineCount =0;
        try {
            BufferedReader songsReader = new BufferedReader(new FileReader("soundfiles/" + soundFileName));
            line = songsReader.readLine();
            totalSongs = Integer.parseInt(line);
            songNames = new String[totalSongs];
            songFilenames = new String[totalSongs];
            while (((line = songsReader.readLine()) != null) && (lineCount < totalSongs)) {
                String[] parts = line.split(";;");
                if (parts.length == 2) {
                    String field1 = parts[0].trim();
                    String field2 = parts[1].trim();
                    songNames[lineCount] = field1;
                    songFilenames[lineCount] = field2;
                    System.out.println("Field 1: " + field1 + " | Field 2: " + field2);
                    lineCount++;
                } else {
                    System.out.println("Invalid line format: " + line);
                }
                System.out.println(line);  // Process the line
            }
        } catch (IOException e) {
            System.out.println("Error opening test file " + soundFileName+ ".txt");
            e.printStackTrace();
        }
        return songNames;
    }

    /*
        Below code inspired by the tester files from SpellCheck.
    */
    public void readTextFile(String soundFileName){
        // Open files
        try {
            BufferedReader soundReader = new BufferedReader(new FileReader("soundfiles/" + soundFileName + ".txt"));

            this.inputMelody1 = loadNotes(soundReader);
            interweave();

        } catch (IOException e) {
            System.out.println("Error opening test file " + soundFileName+ ".txt");
            e.printStackTrace();
        }
    }
    public void readTextFile(String soundFileName, int melodyType) {
        // Open files
        try {
            BufferedReader soundReader = new BufferedReader(new FileReader("soundfiles/" + soundFileName));
            if(melodyType == 1) {
                this.inputMelody2 = loadNotes(soundReader);
            } else {
                this.inputMelody1 = loadNotes(soundReader);
            }
        } catch (IOException e) {
            System.out.println("Error opening test file " + soundFileName+ ".txt");
            e.printStackTrace();
        }
    }

    public String getMIDIFileName(int selectedIndex) {
        if(selectedIndex < 0 || selectedIndex >= totalSongs) {
            return "";
        }
        return songFilenames[selectedIndex];
    }

    private boolean[][] loadNotes(BufferedReader br) {
        String line;
        boolean[][] returnArray = new boolean[scaleLen][64];
        try {
            // Update instance variables with test data
            for (int i = 0; i < scaleLen; i++) {
                line = br.readLine();
                //System.out.println(line);
                for(int j = 0; j < 64; j++){
                    if(line.charAt(j) == '1'){
                        returnArray[i][j] = true;
                    }
                    else{
                        returnArray[i][j] = false;
                    }
                }
            }
            return returnArray;
        } catch (IOException e) {
            System.out.println("Error opening test file");
            e.printStackTrace();
        }
        return null;
    }

    public void interweave(){
        int donej = -1;
        int previousi = -1;
        int previousj = -1;
        for(int j = 0; j < timeSlots; j++){
            for(int i = 0; i < scaleLen; i++){
                outputMelody[i][j] = inputMelody1[i][j];
            }
        }
        for(int j = 0; j < timeSlots; j++){
            for(int i = 0; i < scaleLen; i++){
                if(outputMelody[i][j]){
                    previousi = i;
                    previousj = j;
                    donej = j;
                    break;
                }
            }
            if(previousi != -1){
                break;
            }
        }
        for(int j = 0; j < previousj; j++){
            outputMelody[previousi][j] = true;
        }
        donej = previousj;
        while(donej < timeSlots - 1){
            System.out.println(" Done j, previous i, prevj" + donej + " " + previousi + " " + previousj);
            boolean columnEmpty = true;
            for(int j = donej + 1; j < timeSlots; j++){
                for(int i = 0; i < scaleLen; i++){
                    if(outputMelody[i][j]){
                        columnEmpty = false;
                        previousi = i;
                        previousj = j;
                        donej = j;
                        System.out.println(" Break1 - Done j, previous i, prevj" + donej + " " + previousi + " " + previousj);
                        break;
                    }
                }
                if(columnEmpty){
                    outputMelody[previousi][j] = true;
                    columnEmpty = true;
                    donej = j;
                    System.out.println(" Column Empty Check - Done j, previous i, prevj" + donej + " " + previousi + " " + previousj);
                }
            }
        }
        /*
        for(int j = 0; j < timeSlots; j++){
            for(int i = 0; i < scaleLen; i++){
                if(!inputMelody2[i][j]){
                    outputMelody[i][j] = false;
                }
            }
        }
         */
    }

    public void setMelodyFromPage(int selectedIndex, JCheckBox[][] pageMelody) {
        if(selectedIndex < 0 || selectedIndex > 2) {
            return;
        }
        if(pageMelody == null) {
            return;
        }
        boolean[][] pMelody;
        if(selectedIndex == 0) {
            pMelody = inputMelody1;
        } else if (selectedIndex == 1) {
            pMelody = inputMelody2;
        } else {
            pMelody = outputMelody;
        }
        for(int i = 0; i < scaleLen; i++) {
            for(int j = 0; j < timeSlots; j++) {
                if(pageMelody[i][j].isSelected()) {
                    pMelody[i][j] = true;
                } else {
                    pMelody[i][j] = false;
                }
            }
        }
    }
}
