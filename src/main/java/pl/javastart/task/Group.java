package pl.javastart.task;

public class Group {
    private String groupCode;
    private String subjectName;
    Lecturer lecturer;
    private Student[] students = new Student[100];
    private int count;

    void addStudent(Student student) {
        students[count] = student;
        count++;
    }

    Student findStudent(int index) {
        for (int i = 0; i < count; i++) {
            if (index == students[i].getIndex()) {
                return students[i];
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
