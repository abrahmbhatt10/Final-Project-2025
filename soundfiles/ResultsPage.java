import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Below code inspired by ChatGPT

public class ResultsPage extends JPanel {
    private static final int MATRIX_ROWS = 7;
    private static final int MATRIX_COLS = 64;
    private final JCheckBox[][][] checkboxMatrix;
    ActionListener playMusicListener;
    JButton[] playMusic;
    MIDI playMIDI;
    JustSound justSound;
    JFrame parentFrame;

    public ResultsPage(MIDI m_midi, JustSound m_justSound, JFrame m_parentFrame) {
        playMIDI = m_midi;
        justSound = m_justSound;
        parentFrame = m_parentFrame;

        setLayout(new BorderLayout());
        playMusic = new JButton[3];
        playMusicListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if(source.equals(playMusic[0])) {
                    for (int timerCount = 0; timerCount < playMIDI.getTimeSlots(); timerCount++) {
                        try {
                            justSound.makeMelody(playMIDI.getInputMelody1(), playMIDI, timerCount);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if(source.equals(playMusic[1])) {
                    for(int timerCount = 0; timerCount < playMIDI.getTimeSlots(); timerCount++) {
                        try {
                            justSound.makeMelody(playMIDI.getInputMelody2(), playMIDI,timerCount);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else {
                    for(int timerCount = 0; timerCount < playMIDI.getTimeSlots(); timerCount++) {
                        try {
                            justSound.makeMelody(playMIDI.getOutputMelody(),playMIDI,timerCount);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        };
        checkboxMatrix = new JCheckBox[3][MATRIX_ROWS][MATRIX_COLS];
        JPanel rowsPanel = new JPanel(new GridLayout(3, 1, 0, 10)); // Vertical spacing between rows

        for (int row = 0; row < 3; row++) {
            rowsPanel.add(createRowPanel(row));
        }

        add(new JScrollPane(rowsPanel), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton startAgainButton = new JButton("Start Again");
        startAgainButton.addActionListener(e -> onStartAgain());
        bottomPanel.add(startAgainButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createRowPanel(int rowIndex) {
        JPanel rowPanel = new JPanel(new BorderLayout());

        // Play button
        JButton playButton = new JButton("Play " + (rowIndex + 1));
        playButton.addActionListener(playMusicListener);
        int finalRowIndex = rowIndex;
        //playButton.addActionListener(e -> {
          //  onPlayPressed(finalRowIndex);
        //});
        rowPanel.add(playButton, BorderLayout.WEST);

        // Matrix panel with border
        JPanel matrixPanel = new JPanel(new GridLayout(MATRIX_ROWS, MATRIX_COLS, 1, 1));
        matrixPanel.setBorder(new LineBorder(Color.BLACK, 3)); // Thick border
        for (int i = 0; i < MATRIX_ROWS; i++) {
            for (int j = 0; j < MATRIX_COLS; j++) {
                JCheckBox cb = new JCheckBox();
                cb.setMargin(new Insets(0, 0, 0, 0));
                cb.setOpaque(true); // Required for background color
                checkboxMatrix[rowIndex][i][j] = cb;
                matrixPanel.add(cb);
            }
        }

        rowPanel.add(matrixPanel, BorderLayout.CENTER);
        return rowPanel;
    }

    private void onPlayPressed(int rowIndex) {
        System.out.println("Play button " + (rowIndex + 1) + " pressed");
        for (int i = 0; i < MATRIX_ROWS; i++) {
            for (int j = 0; j < MATRIX_COLS; j++) {
                if (checkboxMatrix[rowIndex][i][j].isSelected()) {
                    System.out.printf("Row %d: Checkbox at (%d,%d) is selected%n", rowIndex + 1, i, j);
                }
            }
        }
    }

    private void onStartAgain() {
        System.out.println("Start Again button pressed — resetting all checkboxes");
        parentFrame.setVisible(true);
        setVisible(false);
    }

    public void setMelodyColors(MIDI midi) {
        Color[] rowColors = {
                new Color(255, 230, 230),
                new Color(230, 255, 230),
                new Color(230, 230, 255),
                new Color(255, 255, 230),
                new Color(230, 255, 255),
                new Color(255, 230, 255),
                new Color(240, 240, 240)
        };
        boolean[][] pArr;
        for(int melodyIndex = 0; melodyIndex < 3; melodyIndex++) {
            if(melodyIndex == 0) {
                pArr = midi.getInputMelody1();
            }
            else if(melodyIndex == 1) {
                pArr = midi.getInputMelody2();
            } else {
                pArr = midi.getOutputMelody();
            }
            boolean rowSelected = false;
            for (int i = 0; i < midi.getScaleLen(); i++) {
                rowSelected = false;
                for (int j = 0; j < midi.getTimeSlots(); j++) {
                     if(pArr[i][j]) {
                         rowSelected = true;
                         checkboxMatrix[melodyIndex][i][j].setSelected(true);
                     }
                }
                if(rowSelected) {
                    for(int j = 0; j < midi.getTimeSlots(); j++) {
                        checkboxMatrix[melodyIndex][i][j].setBackground(rowColors[i]);
                    }
                }
            }
        }
    }

}
