import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Below code inspired by ChatGPT.
 */

public class MultiPageForm extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MultiPageForm() {
        setTitle("Multi-Page Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2000, 1000);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add 5 pages
        cardPanel.add(createPage("Welcome Page", null, "Next", 1), "Page1");
        cardPanel.add(createPage("Select Melody 1", new String[]{"Option A", "Option B", "Option C"}, "Next", 2), "Page2");
        cardPanel.add(createPage("Grid 1", null, "Next", 3), "Page3");
        cardPanel.add(createPage("Select Melody 2", new String[]{"Choice 1", "Choice 2", "Choice 3"}, "Next", 4), "Page4");
        cardPanel.add(createFinalPage(), "Grid 2");

        add(cardPanel);
        setVisible(true);
    }

    private JPanel createPage(String labelText, String[] comboOptions, String buttonText, int pageNumber) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText, JLabel.CENTER);
        panel.add(label, BorderLayout.NORTH);

        if (comboOptions != null) {
            JComboBox<String> comboBox = new JComboBox<>(comboOptions);
            panel.add(comboBox, BorderLayout.CENTER);
        }

        JButton nextButton = new JButton(buttonText);
        panel.add(nextButton, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> cardLayout.show(cardPanel, "Page" + (pageNumber + 1)));

        return panel;
    }

    private JPanel createFinalPage() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Page 5 - Final Page", JLabel.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Form submitted successfully!");
            System.exit(0);
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MultiPageForm::new);
    }
}

