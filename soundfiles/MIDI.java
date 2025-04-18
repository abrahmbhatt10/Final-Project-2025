import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MIDI {
    private boolean[][] inputMelody;
    private boolean[][] outputMelody;

    public MIDI() {
        inputMelody = new boolean[12][64];
        outputMelody = new boolean[12][64];
    }

    public boolean[][] getInputMelody() {
        return inputMelody;
    }

    public boolean[][] getOutputMelody() {
        return outputMelody;
    }

    /*
        Below code inspired by the tester files from SpellCheck.
    */
    public void readTextFile(String soundFileName){
        // Open files
        try {
            BufferedReader soundReader = new BufferedReader(new FileReader("soundfiles/" + soundFileName + ".txt"));

            this.inputMelody = loadNotes(soundReader);
            switchARoo();

        } catch (IOException e) {
            System.out.println("Error opening test file " + soundFileName+ ".txt");
            e.printStackTrace();
        }
    }

    private boolean[][] loadNotes(BufferedReader br) {
        String line;
        boolean[][] returnArray = new boolean[12][64];
        try {
            // Update instance variables with test data
            for (int i = 0; i < 12; i++) {
                line = br.readLine();
                System.out.println(line);
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
            for(int i = 0; i < 12; i++){
                if((!firstNote) && (inputMelody[i][j] == true)){
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
        for(int i = 0; i < 12; i++){
            outputI = getSwitchARooI(firstI, i);
            for(int j = 0; j < 64; j++){
                outputMelody[outputI][j] = inputMelody[firstI][j];
            }
        }
    }

    public int getSwitchARooI(int firstI, int i){
        int returnI = 2*firstI - i;
        if(returnI < 0){
            returnI = 12 + returnI;
        }
        else if (returnI > 12){
            returnI = returnI - 12;
        }
        return returnI;
    }
}
