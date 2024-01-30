package pl.javastart.task;

import javax.swing.plaf.basic.BasicFileChooserUI;
import java.util.Arrays;

public class UniversityApp {
    private Lecturer[] lecturers = new Lecturer[100];
    private Group[] groups = new Group[100];
    private Student[] students = new Student[100];
    private Grade[] grades = new Grade[100];
    private int lecturersCount = 0;
    private int groupsCount = 0;
    private int studentsCount = 0;
    private int gradesCount = 0;

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */

    public void createLecturer(int id, String degree, String firstName, String lastName) {
        if (findLecturerById(id) != null) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
            return;
        }
        if (lecturersCount >= lecturers.length) {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }
        Lecturer lecturer = new Lecturer();
        lecturer.setId(id);
        lecturer.setDegree(degree);
        lecturer.setFirstName(firstName);
        lecturer.setLastName(lastName);
        lecturers[lecturersCount] = lecturer;
        lecturersCount++;
    }

    private Lecturer findLecturerById(int id) {
        for (int i = 0; i < lecturersCount; i++) {
            if (id == lecturers[i].getId()) {
                return lecturers[i];
            }
        }
        return null;
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */

    public void createGroup(String code, String name, int lecturerId) {
        if (findGroupByGroupCode(code) != null) {
            System.out.println("Grupa " + code + " już istnieje");
            return;
        }
        Group group = new Group();
        group.setGroupCode(code);
        group.setSubjectName(name);
        group.lecturer = new Lecturer();
        group.lecturer.setId(lecturerId);
        groups[groupsCount] = group;
        groupsCount++;
    }

    private Group findGroupByGroupCode(String code) {
        for (int i = 0; i < groupsCount; i++) {
            if (code.equals(groups[i].getGroupCode())) {
                return groups[i];
            }
        }
        return null;
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */

    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        Group groupByGroupCode = findGroupByGroupCode(groupCode);
        if (groupByGroupCode == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        if (groupByGroupCode.findStudent(index) != null) {
            System.out.println("Student o indeksie " + index + " jest już zapisany do grupy " + groupCode);
            return;
        }
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setIndex(index);
        students[studentsCount] = student;
        studentsCount++;
        groupByGroupCode.addStudent(student);
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */

    public void printGroupInfo(String groupCode) {
        if (findGroupByGroupCode(groupCode) == null) {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
        } else {
            for (int i = 0; i < groupsCount; i++) {
                if (groupCode.equals(groups[i].getGroupCode())) {
                    System.out.print("Kod: ");
                    System.out.println(groups[i].getGroupCode());
                    System.out.print("Nazwa: ");
                    System.out.println(groups[i].getSubjectName());
                    System.out.print("Prowadzący: ");
                    System.out.print(lecturers[i].getDegree() + " ");
                    System.out.print(lecturers[i].getFirstName() + " ");
                    System.out.println(lecturers[i].getLastName());
                    System.out.println("Uczestnicy:");
                    for (int j = 0; j < studentsCount; j++) {
                        if (groupCode.equals(groups[j].getGroupCode())) {
                            System.out.print(students[j].getIndex() + " ");
                            System.out.print(students[j].getFirstName() + " ");
                            System.out.println(students[j].getLastName() + " ");
                        }
                    }
                }
            }
        }
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */

    public void addGrade(int studentIndex, String groupCode, double grade) {
        Group groupByGroupCode = findGroupByGroupCode(groupCode);
        if (groupByGroupCode == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        Student student = groupByGroupCode.findStudent(studentIndex);
        if (student == null) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }
        for (int i = 0; i < gradesCount; i++) {
            if (grades[i].getGrade() != 0) {
                System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy " + groupCode);
                return;
            }
            Grade finalGrade = new Grade();
            finalGrade.setGrade(grade);
            finalGrade.student = student;
            finalGrade.group = groupByGroupCode;
            grades[gradesCount] = finalGrade;
            gradesCount++;
        }
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */

    public void printGradesForStudent(int index) {
        for (int i = 0; i < gradesCount; i++) {
            if (index == grades[i].student.getIndex()) {
                System.out.println(grades[i].group.getSubjectName() + ": " + grades[i].getGrade());
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        if (findGroupByGroupCode(groupCode) == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        for (int i = 0; i < gradesCount; i++) {
            if (groupCode.equals(grades[i].group.getGroupCode())) {
                System.out.printf("%d %s %s: %.1f \n", students[i].getIndex(),
                        students[i].getFirstName(), students[i].getLastName(), grades[i].getGrade());
            }
        }
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        for (int i = 0; i < studentsCount; i++) {
            System.out.printf("%d %s %s \n", students[i].getIndex(),
                    students[i].getFirstName(), students[i].getLastName());
        }
    }
}
