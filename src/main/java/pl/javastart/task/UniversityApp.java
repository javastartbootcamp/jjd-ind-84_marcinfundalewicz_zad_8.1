package pl.javastart.task;

import javax.swing.plaf.basic.BasicFileChooserUI;

public class UniversityApp {
    Lecturer[] lecturers = new Lecturer[100];
    Group[] groups = new Group[100];
    Group[] studentsInGroup = new Group[100];
    int lecturersCount = 0;
    int groupCount = 0;
    int studentInGroupCount = 0;
    Grade[] grades = new Grade[100];
    int gradesCount = 0;

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
        lecturers[lecturersCount] = new Lecturer();
        lecturers[lecturersCount].setId(id);
        lecturers[lecturersCount].setDegree(degree);
        lecturers[lecturersCount].setFirstName(firstName);
        lecturers[lecturersCount].setLastName(lastName);
        int counter2 = 0;
        for (int i = 0; i < lecturersCount + 1; i++) {
            if (id == lecturers[i].getId()) {
                counter2++;
            }
        }
        if (counter2 > 1) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
        } else {
            lecturers[lecturersCount].setId(id);
            lecturersCount++;
        }
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
        groups[groupCount] = new Group();
        groups[groupCount].setGroupCode(code);
        groups[groupCount].setSubjectName(name);
        groups[groupCount].lecturer = new Lecturer();
        groups[groupCount].lecturer.setId(lecturerId);
        int counter2 = 0;
        for (int i = 0; i < groupCount + 1; i++) {
            if (code.equals(groups[i].getGroupCode())) {
                counter2++;
            }
        }
        int counter3 = 0;
        for (int i = 0; i < lecturersCount; i++) {
            if (lecturerId == lecturers[i].getId()) {
                counter3++;
            }
        }
        if (lecturerId == 0 || (counter3 <= 0)) {
            System.out.println("Prowadzacy o id " + lecturerId + " nie istnieje");
        } else if (counter2 > 1) {
            System.out.println("Grupa " + code + " już istnieje");
        } else {
            groups[groupCount].setGroupCode(code);
            groupCount++;
        }
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
        int counter = 0;
        for (int i = 0; i < groupCount; i++) {
            if (groupCode.equals(groups[i].getGroupCode())) {
                counter++;
            }
        }
        if (counter >= 1) {
            studentsInGroup[studentInGroupCount] = new Group();
            studentsInGroup[studentInGroupCount].student = new Student();
            studentsInGroup[studentInGroupCount].student.setIndex(index);
            studentsInGroup[studentInGroupCount].setGroupCode(groupCode);
            studentsInGroup[studentInGroupCount].student.setFirstName(firstName);
            studentsInGroup[studentInGroupCount].student.setLastName(lastName);
            studentInGroupCount++;
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
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
        for (int i = 0; i < groupCount; i++) {
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
                for (int j = 0; j < studentInGroupCount; j++) {
                    if (groupCode.equals(studentsInGroup[j].getGroupCode())) {
                        System.out.print(studentsInGroup[j].student.getIndex() + " ");
                        System.out.print(studentsInGroup[j].student.getFirstName() + " ");
                        System.out.println(studentsInGroup[j].student.getLastName() + " ");
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
        grades[gradesCount] = new Grade();
        grades[gradesCount].group = new Group();
        grades[gradesCount].student = new Student();
        grades[gradesCount].setGrade(grade);
        grades[gradesCount].group.setGroupCode(groupCode);
        grades[gradesCount].student.setIndex(studentIndex);
        gradesCount++;
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
                System.out.println(groups[i].getSubjectName() + grades[i].getGrade());
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
        for (int i = 0; i < gradesCount; i++) {
            if (groupCode.equals(grades[i].group.getGroupCode())) {
                System.out.printf("%d %s %s: %.1f \n", studentsInGroup[i].student.getIndex(), studentsInGroup[i].student.getFirstName(), studentsInGroup[i].student.getLastName(), grades[i].getGrade());
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
        for (int i = 0; i < studentInGroupCount; i++) {
            System.out.printf("%d %s %s \n", studentsInGroup[i].student.getIndex(), studentsInGroup[i].student.getFirstName(), studentsInGroup[i].student.getLastName());
        }
    }
}
