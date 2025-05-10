/*
    I was inspired by the code from this url: https://stackoverflow.com/questions/31910434/how-to-generate-audio-in-java
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;

// This class is not currently being used, only being used as a reference point for testing.

public class SimpleSound extends JFrame implements ActionListener{
    Synthesizer syn;
    MidiChannel[] midChannel;
    Instrument[] instrument;
    Timer t;
    int j = 0;
    final JButton button1, button2;
    boolean melodyType;
    MIDI midi;

    public SimpleSound() {
        midi = new MIDI();
        midi.readTextFile("maryhadalittlelamb");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        melodyType = false;

        JPanel panel = new JPanel();
        button1 = new JButton("Original: ");
        button2 = new JButton("New: ");
        panel.add(button1);
        panel.add(button2);
        this.add(panel);
        this.pack();

        t = new Timer(125, this);

        try {
            syn = MidiSystem.getSynthesizer();
            syn.open();
            midChannel = syn.getChannels();

            instrument = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(instrument[40]);


            ActionListener myListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource().equals(button1)) {
                        melodyType = false;
                    } else {
                        melodyType = true;
                    }
                    t.start();
                }
            };
            button1.addActionListener(myListener);
            button2.addActionListener(myListener);
        } catch (MidiUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    void makeASound() {
        this.midChannel[5].noteOn(55,550);
        this.midChannel[5].noteOn(59,700);
        this.midChannel[5].noteOn(62,400);
        if (j < 4) {
            this.midChannel[5].noteOn(45, 400);
        }
        else {
            this.midChannel[5].noteOn(43, 400);
        }
    }

    void makeMelody(boolean[][] melodyArr) {
        /*
            Below plays out the melody inputted in
         */
        int noteNumber = 0;
        for(int i = 0; i < midi.getScaleLen(); i++){
            if(melodyArr[i][this.j] == true){
                noteNumber = (2*i) + 48;
                if(i >= 3){
                    noteNumber = noteNumber - 1;
                }
                this.midChannel[5].noteOn(noteNumber, 400);
            }
        }
    }

    void makeMelody(JCheckBox[][] melodyWeb) {
        int noteNumber = 0;
        int playtimes = 3;
        for(int p = 0; p < playtimes; p++) {
            for(int i = 0; i < midi.getScaleLen(); i++) {
                for(int k= 0; k < midi.getTimeSlots(); k++) {
                    if(melodyWeb[i][k].isSelected()) {
                        noteNumber = (2*i)*48;
                        if(i >= 3) {
                            noteNumber = noteNumber-1;
                        }
                        this.midChannel[5].noteOn(noteNumber,400);
                        t.start();
                    }
                }
            }
        }
    }
/*
    public static void main(String[] args) {
        new SimpleSound().setVisible(true);
    }

 */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!melodyType) {
            makeMelody(midi.getInputMelody1());
        } else {
            makeMelody(midi.getOutputMelody());
        }
        j++;
        j = j % midi.getTimeSlots();
    }
}
