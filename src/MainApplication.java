import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainApplication {
    private JFrame frame;
    private String studentFilePath;
    private String courseGradeFilePath;
    private JButton studentFileButton;
    private JButton courseGradeFileButton;
    private JButton generateButton;
    private JLabel studentFileLabel;
    private JLabel courseGradeFileLabel;

    public MainApplication() {
        frame = new JFrame("Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2));

        studentFileButton = new JButton("Browse Student File");
        studentFileButton.setFocusPainted(false);
        studentFileLabel = new JLabel();
        studentFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    studentFilePath = chooser.getSelectedFile().getAbsolutePath();
                    studentFileLabel.setText(chooser.getSelectedFile().getName());
                }
            }
        });

        courseGradeFileButton = new JButton("Browse Course Grade File");
        courseGradeFileButton.setFocusPainted(false);
        courseGradeFileLabel = new JLabel();
        courseGradeFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    courseGradeFilePath = chooser.getSelectedFile().getAbsolutePath();
                    courseGradeFileLabel.setText(chooser.getSelectedFile().getName());
                }
            }
        });

        generateButton = new JButton("Generate");
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!studentFilePath.toLowerCase().endsWith(".txt") || !courseGradeFilePath.toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(frame, "The file type is not valid. Please select .txt files.");
                    return;
                }
                GradeCalculator calculator = new GradeCalculator();
                try {
                    calculator.readStudents(studentFilePath);
                    calculator.readCourseGrades(courseGradeFilePath);
                    boolean calculationSuccessful = calculator.calculateFinalGrades();
                    if (!calculationSuccessful) {
                        return;
                    }
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                    chooser.setFileFilter(filter);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        String savePath = chooser.getSelectedFile().getAbsolutePath();
                        if (!savePath.toLowerCase().endsWith(".txt")) {
                            savePath += ".txt";
                        }
                        calculator.writeCourseResults(savePath);
                        JOptionPane.showMessageDialog(frame, "Grades calculated and written to " + savePath);
                    }
                } catch (IOException ex) {
                    // Handle the exception
                }
            }
        });

        frame.add(studentFileButton);
        frame.add(studentFileLabel);
        frame.add(courseGradeFileButton);
        frame.add(courseGradeFileLabel);
        frame.add(generateButton);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainApplication();
    }
}