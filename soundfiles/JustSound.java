import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class JustSound {
    Synthesizer syn;
    MidiChannel[] midChannel;
    Instrument[] instrument;

    public JustSound () {
        try {
            syn = MidiSystem.getSynthesizer();
            syn.open();
            midChannel = syn.getChannels();

            instrument = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(instrument[40]);
        } catch (MidiUnavailableException ex) {
            System.out.println("Just Sound error");
            ex.printStackTrace();
        }
    }
 /*
    void makeMelody(JCheckBox[][] melodyWeb, MIDI midi) {
        int noteNumber = 0;
        int playtimes = 3;
        for(int p = 0; p < playtimes; p++) {
            for(int k = 0; k < midi.getTimeSlots(); k++) {
                for(int i= 0; i < midi.getScaleLen(); i++) {
                    if(melodyWeb[i][k].isSelected()) {
                        System.out.println("selected note playing");
                        noteNumber = (2*i)*48;
                        if(i >= 3) {
                            noteNumber = noteNumber-1;
                        }
                        this.midChannel[5].noteOn(noteNumber,400);
                        System.out.println("makeMelody "+noteNumber);
                        try {
                            sleep(125);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        this.midChannel[5].noteOff(noteNumber);

                    }
                }
            }
        }
    }

  */
    void makeMelody(boolean[][] melodyArr, MIDI midi, int timerCount) throws InterruptedException {
        int noteNumber = 0;
        for(int i= 0; i < midi.getScaleLen(); i++) {
            if(melodyArr[i][timerCount]) {
                noteNumber = (2*i)+48;
                if(i >= 3) {
                    noteNumber = noteNumber -1;
                }
                System.out.println("selected note playing "+noteNumber + " pos "+i+" "+timerCount);
                this.midChannel[5].noteOn(noteNumber,400);
                System.out.println("makeMelody "+noteNumber);
            }
            Thread.sleep(60);
        }
    }
}
