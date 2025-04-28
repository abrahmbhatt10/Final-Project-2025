import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

/*
    I took the source file from GraphSelection.java and I retitled it to MusicSelection.java
    from last year's final project as a starting point.
 */

public class MusicSelection extends JFrame implements ActionListener {
    JFrame frame;
    JPanel gridPanel;
    JPanel outerPanel;
    FrontEnd window;

    // I got below code from ChatGPT
    public MusicSelection(){
        frame = new JFrame("7x64 CheckBox Matrix with Row Highlights");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2000, 1000);

        // Outer panel with BorderLayout
        outerPanel = new JPanel(new BorderLayout());

        // Grid panel inside a scroll pane
        gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 1, 1, 1); // Padding

        JCheckBox[][] checkBoxes = new JCheckBox[7][64];
        JPanel[][] wrapperPanels = new JPanel[7][64]; // Needed to control background coloring

        // Borders
        Border thinBorder = new LineBorder(Color.GRAY, 1);
        Border thickBorder = new LineBorder(Color.BLACK, 3);

        // Distinct colors for each row
        Color[] rowColors = {
                new Color(255, 230, 230), // Light Red
                new Color(230, 255, 230), // Light Green
                new Color(230, 230, 255), // Light Blue
                new Color(255, 255, 230), // Light Yellow
                new Color(230, 255, 255), // Light Cyan
                new Color(255, 230, 255), // Light Pink
                new Color(240, 240, 240)  // Light Gray
        };

        //Melody Name strings
        String[] melodyNames = {
                new String("C"),
                new String("D"),
                new String("E"),
                new String("F"),
                new String("G"),
                new String("A"),
                new String("B")
        };

        // Top-left blank space
        gbc.gridx = 0;
        gbc.gridy = 0;
        gridPanel.add(new JLabel(""), gbc);

        // Add column labels
        for (int col = 0; col < 64; col++) {
            gbc.gridx = col + 1;
            gbc.gridy = 0;
            JLabel colLabel = new JLabel(Integer.toString(col+1), SwingConstants.CENTER);
            colLabel.setFont(new Font("Arial", Font.BOLD, 12));
            gridPanel.add(colLabel, gbc);
        }

        // Add rows with row labels and checkboxes
        for (int row = 0; row < 7; row++) {
            // Row label
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            JLabel rowLabel = new JLabel(melodyNames[row], SwingConstants.CENTER);
            rowLabel.setFont(new Font("Arial", Font.BOLD, 12));
            gridPanel.add(rowLabel, gbc);

            // Checkboxes
            for (int col = 0; col < 64; col++) {
                checkBoxes[row][col] = new JCheckBox();

                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.add(checkBoxes[row][col], BorderLayout.CENTER);
                wrapperPanel.setOpaque(true);
                wrapperPanel.setBackground(Color.WHITE); // Default background

                wrapperPanels[row][col] = wrapperPanel; // Store reference

                // Border logic
                Border rightBorder = null;
                if ((col + 1) % 16 == 0) {
                    rightBorder = thickBorder;
                } else if ((col + 1) % 4 == 0) {
                    rightBorder = thinBorder;
                }

                if (rightBorder != null) {
                    wrapperPanel.setBorder(BorderFactory.createMatteBorder(
                            0, 0, 0,
                            rightBorder.getBorderInsets(wrapperPanel).right,
                            ((LineBorder) rightBorder).getLineColor()
                    ));
                }

                gbc.gridx = col + 1;
                gbc.gridy = row + 1;
                gridPanel.add(wrapperPanel, gbc);

                int finalRow = row;
                int finalCol = col;

                // Behavior: when checkbox selected/deselected
                checkBoxes[row][col].addItemListener(e -> {
                    updateRowHighlight(checkBoxes, wrapperPanels, rowColors, finalRow);
                });
            }
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        outerPanel.add(scrollPane, BorderLayout.CENTER);

        // Submit button
        JButton submitButton = new JButton("Submit");
        outerPanel.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            System.out.println("Selected CheckBoxes:");
            for (int row = 0; row < 7; row++) {
                for (int col = 0; col < 64; col++) {
                    if (checkBoxes[row][col].isSelected()) {
                        System.out.println("Row " + row + ", Column " + col + " selected");
                    }
                }
            }
        });
        frame.add(outerPanel);
        frame.setVisible(true);
    }

    // Updates the background of an entire row based on selection
    private static void updateRowHighlight(JCheckBox[][] checkBoxes, JPanel[][] wrappers, Color[] rowColors, int row) {
        boolean anySelected = false;
        for (int col = 0; col < 64; col++) {
            if (checkBoxes[row][col].isSelected()) {
                anySelected = true;
                break;
            }
        }
        for (int col = 0; col < 64; col++) {
            if (anySelected) {
                wrappers[row][col].setBackground(rowColors[row]);
            } else {
                wrappers[row][col].setBackground(Color.WHITE);
            }
        }
    }

    // Does action performed of action event e
    public void actionPerformed(ActionEvent e){
        if(this.window == null) {
            this.window = new FrontEnd();
            this.window.setGsWindow(this);
        }
        this.window.setVisible(true);
        setVisible(false);
        /*
        MathFunction f = null;
        if(rb1.isSelected()){
            f = new MathFunction(window);
            f.setStringF("x");
            window.setF(f);
        }
        if(rb2.isSelected()){
            f = new MathFunction(window);
            f.setStringF("x^2");
            window.setF(f);
        }
        if(rb3.isSelected()){
            f = new TrigFunc(window);
            f.setStringF("sinx");
            window.setF(f);
        }
        if(rb4.isSelected()){
            f = new TrigFunc(window);
            f.setStringF("cosx");
            window.setF(f);
        }
        if(rb5.isSelected())
        {
            String tStr = "polynomial "+tf.getText();
            //JOptionPane.showMessageDialog(this,"Polynomial Selected:" + tStr);
            f = new PolynomialFunc(tStr, window);
            f.setStringF(tStr);
            window.setF(f);
        }
        if(cb1.isSelected())
        {
            f.setDisplayArea(true);
        }
        else {
            f.setDisplayArea(false);
        }
        if(f != null)
        {
            String tstr1 = inputX.getText();
            if(tstr1 != null && tstr1.length() > 0 && Character.isDigit(tstr1.charAt(0)))
            {
                f.setInputX(Integer.parseInt(tstr1));
            }
        }

         */
        //JOptionPane.showMessageDialog(this,"Displaying Graph: " + f.getStringF());
        window.repaint();
    }
    public static void main(String[] args) {
        MusicSelection gsDemo = new MusicSelection();
    }

}
