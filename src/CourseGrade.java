public class CourseGrade {
    private String studentId;
    private String courseCode;
    private int[] testGrades = new int[3];
    private int finalExamGrade;

    public CourseGrade(String studentId, String courseCode, int[] testGrades, int finalExamGrade) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.testGrades = testGrades;
        this.finalExamGrade = finalExamGrade;
    }

    public float calculateFinalGrade() {
        float total = 0;
        for (int grade : testGrades) {
            total += grade;
        }
        total = total * 0.2f; // 20% weightage for each test
        total += finalExamGrade * 0.4f; // 40% weightage for final exam
        return total;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int[] getTestGrades() {
        return testGrades;
    }

    public void setTestGrades(int[] testGrades) {
        this.testGrades = testGrades;
    }

    public int getFinalExamGrade() {
        return finalExamGrade;
    }
    public void setFinalExamGrade(int finalExamGrade) {
        this.finalExamGrade = finalExamGrade;
    }
}