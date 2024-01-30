package pl.javastart.task;

import javax.swing.plaf.basic.BasicFileChooserUI;

public class UniversityApp {
    public Lecturer[] lecturers = new Lecturer[100];
    public Group[] groups = new Group[100];
    public Student[] students = new Student[100];
    public int lecturersCount = 0;
    public int groupCount = 0;
    public int studentCount = 0;
    public Grade[] grades = new Grade[100];
    public int gradesCount = 0;

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
        Lecturer lecturer = new Lecturer();
        lecturer.setId(id);
        lecturer.setDegree(degree);
        lecturer.setFirstName(firstName);
        lecturer.setLastName(lastName);
        if (findLecturerById(id) != null) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
            return;
        }
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
        Group group = new Group();
        group.setGroupCode(code);
        group.setSubjectName(name);
        group.lecturer = new Lecturer();
        group.lecturer.setId(lecturerId);
        if (findGroupByGroupCode(code) != null) {
            System.out.println("Grupa " + code + " już istnieje");
            return;
        }
        groups[groupCount] = group;
        groupCount++;
    }

    private Group findGroupByGroupCode(String code) {
        for (int i = 0; i < groupCount; i++) {
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
        //prosilbym jeszcze raz o wytlumaczenie tworzenia metody findStudent i AddStudent, czy te metody zamiast tworzenia
        // w klasie Group mozna stworzyc w UniversityApp
        //czy jezeli stworze licznik count w klasie Group to czy musze definiowac jego wartosc od razu na 0 np. int count = 0 czy wystarczy int count;
        //czy jezeli mam count zdefiniowany w klasie Group to jak sie to ma do wywolywania count w innych metodach
        // void metoda1() {
        //count++ }
        // void metoda2() {
        //count++ }
        //metoda1()
        //metoda2()
        //czy po wywolaniu metody 1 oraz 2 licznik bedzie 2 czy licznik jest jakby osobno przypisany do kazdej metody
        Group groupByGroupCode = findGroupByGroupCode(groupCode);
        if (groupByGroupCode == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        for (int i = 0; i < groupCount; i++) {
            if (groups[i].getGroupCode().equals(groupCode) && groups[i].findStudent(index) == null) {
                Student student = new Student();
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setIndex(index);
                students[studentCount] = student;
                studentCount++;
                groups[i].addStudent(student);
            } else if (groups[i].getGroupCode().equals(groupCode) && groups[i].findStudent(index) != null) {
                System.out.println("Student o indeksie " + index + " jest już zapisany do grupy " + groupCode);
            }
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
        if (findGroupByGroupCode(groupCode) == null) {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
        } else {
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
                    for (int j = 0; j < studentCount; j++) {
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
        //prosilbym o sprawdzenie metody findGrade i AddGrade, czy te metody zamiast tworzenia w klasie Group mozna stworzyc w UniversityApp
        //czy jeszcze potrzebne sa jakies warunki lub czego brakuje w metodzie addGrade lub klasie Group do pelnej funkcjonalnosci
        Group groupByGroupCode = findGroupByGroupCode(groupCode);
        if (groupByGroupCode == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        for (int i = 0; i < groupCount; i++) {
            if (groups[i].getGroupCode().equals(groupCode) && groups[i].findStudent(studentIndex) != null  && groups[i].findGrade(grade) == null) {
                Grade finalGrade = new Grade();
                finalGrade.setGrade(grade);
                finalGrade.student = new Student();
                finalGrade.student.setIndex(studentIndex);
                finalGrade.group = new Group();
                finalGrade.group.setGroupCode(groupCode);
                grades[gradesCount] = finalGrade;
                gradesCount++;
                groups[i].addGrade(finalGrade);
            } else if (groups[i].getGroupCode().equals(groupCode) && groups[i].findStudent(studentIndex) != null) {
                System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            } else if (groups[i].getGroupCode().equals(groupCode) && groups[i].findGrade(grade) != null) {
                System.out.println("Student o indeksie " + studentIndex + " ma już ocenę w grupie " + groupCode);
            }
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
                System.out.println(groups[i].getSubjectName() + ": " + grades[i].getGrade());
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
        for (int i = 0; i < studentCount; i++) {
            System.out.printf("%d %s %s \n", students[i].getIndex(),
                    students[i].getFirstName(), students[i].getLastName());
        }
    }
}
