import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MultiPageForm {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Shared data
    private JComboBox<String> comboBoxPage1;
    private JCheckBox[][] checkBoxMatrixPage2;
    private JComboBox<String>[][] comboBoxMatrixPage3;
    private JCheckBox[][] checkBoxMatrixPage4;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MultiPageForm().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Multi-Page Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createPage1(), "Page1");
        mainPanel.add(createPage2(), "Page2");
        mainPanel.add(createPage3(), "Page3");
        mainPanel.add(createPage4(), "Page4");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Page 1: Dropdown
    private JPanel createPage1() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select an option from the dropdown:");
        comboBoxPage1 = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "Page2"));

        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBoxPage1, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);
        panel.add(createNavPanel(null, "Page2", false), BorderLayout.SOUTH);
        return panel;
    }

    // Page 2: 7x64 checkbox matrix with row highlight
    private JPanel createPage2() {
        checkBoxMatrixPage2 = new JCheckBox[7][64];
        JPanel panel = new JPanel(new BorderLayout());

        JPanel grid = createCheckboxMatrix(checkBoxMatrixPage2);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "Page3"));

        panel.add(new JScrollPane(grid), BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);
        panel.add(createNavPanel("Page1", "Page3", false), BorderLayout.SOUTH);
        return panel;
    }

    // Page 3: 7x10 dropdown matrix
    private JPanel createPage3() {
        comboBoxMatrixPage3 = new JComboBox[7][10];
        JPanel panel = new JPanel(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(7, 11, 2, 2));

        for (int row = 0; row < 7; row++) {
            grid.add(new JLabel("Row " + row, SwingConstants.CENTER));
            for (int col = 0; col < 10; col++) {
                comboBoxMatrixPage3[row][col] = new JComboBox<>(new String[]{"A", "B", "C"});
                grid.add(comboBoxMatrixPage3[row][col]);
            }
        }

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "Page4"));

        panel.add(grid, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);
        panel.add(createNavPanel("Page2", "Page4", false), BorderLayout.SOUTH);
        return panel;
    }

    // Page 4: Final 7x64 checkbox matrix with submit button
    private JPanel createPage4() {
        checkBoxMatrixPage4 = new JCheckBox[7][64];
        JPanel panel = new JPanel(new BorderLayout());

        JPanel grid = createCheckboxMatrix(checkBoxMatrixPage4);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this::handleSubmit);
        panel.add(createNavPanel("Page3", null, true), BorderLayout.SOUTH);
        panel.add(new JScrollPane(grid), BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);
        return panel;
    }
    private JPanel createNavPanel(String backPage, String nextPage, boolean showSubmit) {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        if (backPage != null) {
            JButton back = new JButton("Back");
            back.addActionListener(e -> cardLayout.show(mainPanel, backPage));
            navPanel.add(back);
        }

        if (nextPage != null) {
            JButton next = new JButton("Next");
            next.addActionListener(e -> cardLayout.show(mainPanel, nextPage));
            navPanel.add(next);
        }

        if (showSubmit) {
            JButton submit = new JButton("Submit");
            submit.addActionListener(this::handleSubmit);
            navPanel.add(submit);
        }

        return navPanel;
    }

    // Create a 7x64 checkbox matrix with row highlighting
    private JPanel createCheckboxMatrix(JCheckBox[][] checkBoxes) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Color[] rowColors = {
                new Color(255, 230, 230),
                new Color(230, 255, 230),
                new Color(230, 230, 255),
                new Color(255, 255, 230),
                new Color(230, 255, 255),
                new Color(255, 230, 255),
                new Color(240, 240, 240)
        };

        // Top-left empty corner
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel(""), gbc);

        // Column labels
        for (int col = 0; col < 64; col++) {
            gbc.gridx = col + 1;
            gbc.gridy = 0;
            JLabel colLabel = new JLabel("C" + col, SwingConstants.CENTER);
            colLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            panel.add(colLabel, gbc);
        }

        // Matrix
        for (int row = 0; row < 7; row++) {
            gbc.gridy = row + 1;
            gbc.gridx = 0;
            JLabel rowLabel = new JLabel("Row " + row, SwingConstants.CENTER);
            panel.add(rowLabel, gbc);

            for (int col = 0; col < 64; col++) {
                checkBoxes[row][col] = new JCheckBox();
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.add(checkBoxes[row][col], BorderLayout.CENTER);
                wrapper.setOpaque(true);
                wrapper.setBackground(Color.WHITE);

                int finalRow = row;
                checkBoxes[row][col].addItemListener(e -> updateRowHighlight(checkBoxes, rowColors, finalRow, panel));

                gbc.gridx = col + 1;
                panel.add(wrapper, gbc);
            }
        }

        return panel;
    }

    // Row highlighter
    private void updateRowHighlight(JCheckBox[][] checkBoxes, Color[] rowColors, int row, JPanel panel) {
        boolean anySelected = false;
        for (int col = 0; col < checkBoxes[row].length; col++) {
            if (checkBoxes[row][col].isSelected()) {
                anySelected = true;
                break;
            }
        }

        Component[] components = panel.getComponents();
        for (Component comp : components) {
            GridBagConstraints gbc = ((GridBagLayout) panel.getLayout()).getConstraints(comp);
            if (gbc.gridy == row + 1 && gbc.gridx > 0) {
                comp.setBackground(anySelected ? rowColors[row] : Color.WHITE);
            }
        }
    }

    // Submission handler
    private void handleSubmit(ActionEvent e) {
        StringBuilder result = new StringBuilder("<html><h2>Submission Summary:</h2>");

        result.append("<b>Page 1 Dropdown:</b> ").append(comboBoxPage1.getSelectedItem()).append("<br><br>");

        result.append("<b>Page 2 Checkboxes:</b><br>");
        result.append(getCheckboxSelections(checkBoxMatrixPage2));

        result.append("<br><b>Page 3 Dropdown Matrix:</b><br>");
        for (int row = 0; row < 7; row++) {
            result.append("Row ").append(row).append(": ");
            for (int col = 0; col < 10; col++) {
                result.append(comboBoxMatrixPage3[row][col].getSelectedItem()).append(" ");
            }
            result.append("<br>");
        }

        result.append("<br><b>Page 4 Checkboxes:</b><br>");
        result.append(getCheckboxSelections(checkBoxMatrixPage4));
        result.append("</html>");

        // Show result in a new window
        JFrame resultFrame = new JFrame("Submission Result");
        resultFrame.setSize(600, 600);
        JLabel label = new JLabel(result.toString());
        label.setVerticalAlignment(SwingConstants.TOP);
        JScrollPane scrollPane = new JScrollPane(label);
        resultFrame.add(scrollPane);
        resultFrame.setVisible(true);

        frame.setVisible(false); // Hide main window
    }

    // Helper to get checkbox matrix selection
    private String getCheckboxSelections(JCheckBox[][] checkBoxes) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < checkBoxes.length; row++) {
            for (int col = 0; col < checkBoxes[0].length; col++) {
                if (checkBoxes[row][col].isSelected()) {
                    sb.append("Row ").append(row).append(", Col ").append(col).append("<br>");
                }
            }
        }
        return sb.toString();
    }
}
