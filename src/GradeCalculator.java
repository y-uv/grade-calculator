import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;


public class GradeCalculator {
    private Map<String, Student> students = new HashMap<>();
    private List<CourseGrade> courseGrades = new ArrayList<>();
    private List<CourseResult> courseResults = new ArrayList<>();

public void readStudents(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(", ");
        if (parts.length < 2) {
            //for debugging
            System.out.println("Skipping line with fewer than 2 parts: " + line);
            continue;
        }
        students.put(parts[0], new Student(parts[0], parts[1]));
    }
    reader.close();
}

public boolean readCourseGrades(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] parts = line.split(", ");
                if (parts.length != 6) { // check if the number of elements is not 6
                    JOptionPane.showMessageDialog(null, "Invalid file format.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                if (!isNumeric(parts[2]) || !isNumeric(parts[3]) || !isNumeric(parts[4]) || !isNumeric(parts[5])) {
                    throw new IOException("Invalid file format");
                }
                int[] testGrades = {Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])};
                courseGrades.add(new CourseGrade(parts[0], parts[1], testGrades, Integer.parseInt(parts[5])));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IOException("Invalid file format", e);
            }
        }
        return true; // return true if the operation was successful
    } catch (IOException e) {
        return false; // return false if an exception was thrown
    }
}

private boolean isNumeric(String str) {
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

    public boolean calculateFinalGrades() {
        try {
            for (CourseGrade courseGrade : courseGrades) {
                Student student = students.get(courseGrade.getStudentId());
                if (student == null) {
                    throw new NullPointerException("Student object is null");
                }
                float finalGrade = courseGrade.calculateFinalGrade();
                courseResults.add(new CourseResult(student.getStudentId(), student.getStudentName(), courseGrade.getCourseCode(), finalGrade));
            }
            return true;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Invalid file format.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    public void writeCourseResults(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (CourseResult courseResult : courseResults) {
            double finalGrade = courseResult.getFinalGrade();
            String formattedFinalGrade = String.format("%.2f", finalGrade);
            writer.write(courseResult.getStudentId() + ", " + courseResult.getStudentName() + ", " + courseResult.getCourseCode() + ", " + formattedFinalGrade);
            writer.newLine();
        }
        writer.close();
    }
}