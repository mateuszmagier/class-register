/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author HP
 */
@XmlRootElement(name="students")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentManager {
    
    @XmlElement(name="student")
    private static List<Student> studentsList;
    
    public StudentManager() {}
    
    public static List<Student> getStudentsList() {
        return studentsList;
    }
    
    public static void setStudentsList(List<Student> _studentsList) {
        studentsList = _studentsList;
    }
    
    public static boolean addStudent(Student s, boolean validationResult) {
        if(validationResult) {
            studentsList.add(s);
            return true;
        }
        return false;
    }
    
    public static boolean validateStudent(Student s) {
        if(s == null)
            throw new IllegalArgumentException("Błąd - do listy nie można dodać wartości NULL.");
        if("".equals(s.getName()) || s.getName() == null || "".equals(s.getSurname()) || s.getSurname() == null || "".equals(s.getStudentNumber()) || s.getStudentNumber() == null)
            throw new IllegalArgumentException("Błąd - brak pełnych informacji o studencie.");
        for(Student st : studentsList) {
            if(s.getStudentNumber().equals(st.getStudentNumber()))
                return false;
        }
        return true;
    }
    
    public static boolean removeStudent(Student s) {
        return studentsList.remove(s); // true, jeśli lista zawierała obiekt przekazany w parametrze
    }
    
    public static boolean updateStudent(int position, Student newStudent, boolean validationResult) {
        if(position >= studentsList.size() || position < 0)
            throw new IllegalArgumentException("Przekroczenie zakresu listy studentów.");
        if(validationResult) {
            studentsList.get(position).update(newStudent);
            return true;
        }
        return false;
    }
    
    public static int calculateAbsencesNumber(int position) {
        int absencesNumber = 0;
//        int index = studentsList.indexOf(s); // indeks studenta na liście obecności
        List<Lesson> lessonsList = LessonManager.getLessonsList();
        for(Lesson l: lessonsList) {
            if(!l.getPresencesList().get(position))
                absencesNumber++;
        }
        return absencesNumber;
    }
    
    public static double calculateGroupAverage() {
        double avg = 0.0;
        List<Double> marks;
        int marksNumber = 0;
        
        if(studentsList.size() > 0) {
            for(Student s: studentsList) {
                marks = s.getMarks();
                for(double mark: marks) {
                    avg += mark;
                }
                marksNumber += marks.size();
            }
            if(marksNumber > 0)
                avg /= marksNumber;
        }
        
        return avg;
    }
}
