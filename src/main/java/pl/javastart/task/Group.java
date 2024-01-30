package pl.javastart.task;

public class Group {
    private String groupCode;
    private String subjectName;
    Lecturer lecturer;
    public Student[] students = new Student[30];
    public Grade[] grades = new Grade[30];
    public int count;

    void addStudent(Student student) {
        students[count] = student;
        count++;
    }

    Student findStudent(int index) {
        for (int i = 0; i < count; i++) {
            if (students[i].getIndex() != 0) {
                return students[i];
            }
        }
        return null;
    }

    void addGrade(Grade grade) {
        grades[count] = grade;
        count++;
    }

    Grade findGrade(double grade) {
        for (int i = 0; i < count; i++) {
            if (grades[i].getGrade() != 0) {
                return grades[i];
            }
        }
        return null;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
}
