import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JustSound implements ActionListener {
    Synthesizer syn;
    MidiChannel[] midChannel;
    Instrument[] instrument;
    Timer t;

    public JustSound () {
        try {
            syn = MidiSystem.getSynthesizer();
            syn.open();
            midChannel = syn.getChannels();

            instrument = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(instrument[40]);
            t = new Timer(125, this);
        } catch (MidiUnavailableException ex) {
            System.out.println("Just Sound error");
            ex.printStackTrace();
        }
    }

    void makeMelody(JCheckBox[][] melodyWeb, MIDI midi) {
        int noteNumber = 0;
        int playtimes = 3;
        for(int p = 0; p < playtimes; p++) {
            for(int i = 0; i < midi.getScaleLen(); i++) {
                for(int k= 0; k < midi.getTimeSlots(); k++) {
                    if(melodyWeb[i][k].isSelected()) {
                        System.out.println("selected note playing");
                        noteNumber = (2*i)*48;
                        if(i >= 3) {
                            noteNumber = noteNumber-1;
                        }
                        this.midChannel[5].noteOn(noteNumber,400);
                        System.out.println("makeMelody "+noteNumber);
                        t.start();
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("JustSound ActionEvent");
    }
}
