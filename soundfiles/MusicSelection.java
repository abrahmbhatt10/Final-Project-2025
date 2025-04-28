import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/*
    I took the source file from GraphSelection.java and I retitled it to MusicSelection.java
    from last year's final project as a starting point.
 */

public class MusicSelection extends JFrame implements ActionListener {
    JLabel l, l1, l2;
    JRadioButton rb1,rb2,rb3,rb4, rb5;
    JCheckBox cb1;
    JButton b;
    ButtonGroup bg;
    JTextField tf;
    FrontEnd window;
    JTextField inputX;
    JFrame inputFrame;
    JPanel inputPanel;


    // I got below code from ChatGPT
    public MusicSelection(){
        inputFrame = new JFrame("7x64 RadioButton Matrix with Borders");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(1800, 800); // Adjusted for a lot of buttons

        JPanel mainPanel = new JPanel(new GridLayout(7, 64, 0, 0)); // No internal gaps
        JRadioButton[][] radioButtons = new JRadioButton[7][64];

        // Define the thin and thick borders
        Border thinBorder = new LineBorder(Color.GRAY, 1);
        Border thickBorder = new LineBorder(Color.BLACK, 3);

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 64; col++) {
                radioButtons[row][col] = new JRadioButton();

                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.add(radioButtons[row][col], BorderLayout.CENTER);

                // Decide the border: thick every 16, thin every 4
                Border rightBorder = null;
                if ((col + 1) % 16 == 0) {
                    rightBorder = thickBorder;
                } else if ((col + 1) % 4 == 0) {
                    rightBorder = thinBorder;
                }

                // Apply borders: right only
                if (rightBorder != null) {
                    wrapperPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, rightBorder.getBorderInsets(wrapperPanel).right, ((LineBorder) rightBorder).getLineColor()));
                }

                mainPanel.add(wrapperPanel);
            }
        }

        inputFrame.add(new JScrollPane(mainPanel));
        inputFrame.setVisible(true);
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
