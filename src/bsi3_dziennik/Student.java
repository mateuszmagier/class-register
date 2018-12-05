/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author HP
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    
    @XmlAttribute(name="name")
    private String name;
    @XmlAttribute(name="surname")
    private String surname;
    @XmlAttribute(name="student_number")
    private String studentNumber;
    @XmlElement(name="mark")
    private List<Double> marks;
    
    public Student() {}
    
    public Student(String _name, String _surname, String _studentNumber) {
        name = _name;
        surname = _surname;
        studentNumber = _studentNumber;
        marks = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public String getStudentNumber() {
        return studentNumber;
    }
    
    public void setName(String _name) {
        name = _name;
    }
    
    public void setSurname(String _surname) {
        surname = _surname;
    }
    
    public void setStudentNumber(String _studentNumber) {
        studentNumber = _studentNumber;
    }
    
    public List<Double> getMarks() {
        return marks;
    }
    
    public void setMarks(List<Double> _marks) {
        marks = _marks;
    }
    
    public void update(Student newStudent) {
        name = newStudent.getName();
        surname = newStudent.getSurname();
        studentNumber = newStudent.getStudentNumber();
    }
    
    public boolean addMark(double mark, boolean validationResult) {
        if(validationResult) {
            marks.add(mark);
            return true;
        }
        return false;
    }
    
    public boolean updateMark(int position, double newMark, boolean validationResult) {
        if(position >= marks.size() || position < 0)
            throw new IllegalArgumentException("Przekroczenie zakresu listy ocen.");
        if(validationResult) {
            marks.set(position, newMark);
            return true;
        }
        return false;
    }
    
    public static boolean validateMark(double mark) {
        List<Double> correctMarks = Arrays.asList(2.0, 3.0, 3.5, 4.0, 4.5, 5.0);
        if(correctMarks.indexOf(mark) != -1)
            return true;
        return false;
    }
    
    public boolean removeMark(double mark) {
        return marks.remove(mark);
    }
    
    public double calculateAverage() {
        double avg = 0.0;
        
        if(marks.size() > 0) {

            for(double mark: marks)
                avg += mark;
            
            avg /= marks.size();
        }
        
        return avg;
    }
}
