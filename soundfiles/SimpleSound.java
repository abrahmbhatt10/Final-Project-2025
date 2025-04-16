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

public class SimpleSound extends JFrame implements ActionListener{
    Synthesizer syn;
    MidiChannel[] midChannel;
    Instrument[] instrument;
    Timer t;
    int j = 0;
    MIDI midi;

    public SimpleSound() {
        midi = new MIDI();
        midi.readTextFile("sound1");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton button1 = new JButton("Try");
        this.add(panel);
        panel.add(button1);
        this.pack();
        t = new Timer(250, this);

        try {
            syn = MidiSystem.getSynthesizer();
            syn.open();
            midChannel = syn.getChannels();

            instrument = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(instrument[40]);

            button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    t.start();
                }
            });

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
        for(int i = 0; i < 12; i++){
            if(melodyArr[i][this.j] == true){
                this.midChannel[5].noteOn(i + 48, 400);
            }
        }
    }

    public static void main(String[] args) {
        new SimpleSound().setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        makeMelody(midi.getInputMelody());
        j++;
        j = j % 16;
//        makeMelody(midi.getOutputMelody());
    }
}
