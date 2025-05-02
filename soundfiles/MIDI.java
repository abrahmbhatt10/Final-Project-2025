import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
            switchARoo();

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

    public void switchARoo(){
        boolean firstNote = false;
        int firstJ = 0;
        int firstI = 0;
        /*
            Below code finds the first note
         */
        for(int j = 0; j < 64; j++){
            for(int i = 0; i < scaleLen; i++){
                if((!firstNote) && (inputMelody1[i][j] == true)){
                    firstNote = true;
                    firstJ = j;
                    firstI = i;
                    break;
                }
            }
            if(firstNote){
                break;
            }
        }
        int outputI = 0;
        for(int i = 0; i < scaleLen; i++){
            outputI = getSwitchARooI(firstI, i);
            for(int j = 0; j < 64; j++){
                outputMelody[outputI][j] = inputMelody1[firstI][j];
            }
        }
    }

    public int getSwitchARooI(int firstI, int i){
        int returnI = 2*firstI - i;
        if(returnI < 0){
            returnI = scaleLen + returnI;
        }
        else if (returnI > scaleLen){
            returnI = returnI - scaleLen;
        }
        return returnI;
    }
}
