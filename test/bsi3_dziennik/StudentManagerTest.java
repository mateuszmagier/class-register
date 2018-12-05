/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 *
 * @author HP
 */
@RunWith(JUnitParamsRunner.class)
public class StudentManagerTest {
    Student s, s1, s2, s3;
    
    public StudentManagerTest() {
    }

    @Before
    public void beforeTest() {
        s = new Student("Jan", "Kowalski", "D342325");
        StudentManager.setStudentsList(new ArrayList<>());
        LessonManager.setLessonsList(new ArrayList<>());
        s1 = new Student("Jan", "Kowalski", "D433467");
        s2 = new Student("Marcin", "Nowak", "D433566");
        s3 = new Student("Janusz", "Rodzynek", "D436666");
    }
    
    /*
        testy getterów i setterów
    */
    
    @Test
    public void testGetStudentsList_ObjectHasList_StudentsListReturned() {
        List<Student> result;
        result = StudentManager.getStudentsList();
        assertNotNull(result);
    }
    
    @Test
    public void testGetStudentsList_ObjectHasNoList_NullReturned() {
        List<Student> result;
        StudentManager.setStudentsList(null);
        result = StudentManager.getStudentsList();
        assertNull(result);
    }
    
    @Test
    public void testSetStudentsList_NewStudentsList() {
        List<Student> result;
        result = StudentManager.getStudentsList();
        assertNotNull(result);
    }
    
    /*
        testy addStudent(Student)
    */
    
    @Test
    public void testAddStudent_ValidationWasSuccessful_ListSizeIncreasedBy1() {
        int size_before = StudentManager.getStudentsList().size();
        StudentManager.addStudent(s, true);
        int size_after = StudentManager.getStudentsList().size();
        assertEquals(1, size_after - size_before);
    }
    
    @Test
    public void testAddStudent_ValidationWasSuccessful_AddedStudentAndStudentFromListEquals() {
        StudentManager.addStudent(s, true);
        int size = StudentManager.getStudentsList().size();
        Student result = StudentManager.getStudentsList().get(size-1);
        assertEquals("Jan", result.getName());
        assertEquals("Kowalski", result.getSurname());
        assertEquals("D342325", result.getStudentNumber());
    }
    
    @Test
    public void testAddStudent_ValidationWasUnsuccessful_FalseReturned() {
        boolean result;
        
        result = StudentManager.addStudent(s, false);
        
        assertFalse(result);
    }
    
    @Test
    public void testAddStudent_ValidationWasSuccessful_TrueReturned() {
        boolean result;
        
        result = StudentManager.addStudent(s, true);
        
        assertTrue(result);
    }
    
    /*
        testy validateStudent(Student)
    */
    
    @Test
    public void testValidateStudent_ParameterIsNullObject_Exception() {
        String msg = "";
        String expectedMsg = "Błąd - do listy nie można dodać wartości NULL.";
        try {
            StudentManager.validateStudent(null);
        }
        catch(Exception ex) {
            msg = ex.getMessage();
        }
        assertEquals(expectedMsg, msg);
    }
    
    @Test
    public void testValidateStudent_ParameterIsIncomplete_Exception() {
        String msg = "";
        String expectedMsg = "Błąd - brak pełnych informacji o studencie.";
        Student st = new Student("Marek", "Nowak", "");
        try {
            StudentManager.validateStudent(st);
        }
        catch(Exception ex) {
            msg = ex.getMessage();
        }
        assertEquals(expectedMsg, msg);
    }
    
    @Test
    public void testValidateStudent_ParameterIsComplete_TrueReturned() {
        boolean result;
        result = StudentManager.validateStudent(s);
        assertTrue(result);
    }
    
    @Test
    public void testValidateStudent_ObjectIsAlreadyInList_FalseReturned() {
        StudentManager.addStudent(s, true);
        Student st = new Student("Jan", "Kowalski", "D342325");
        boolean result = StudentManager.validateStudent(st);
        assertFalse(result);
    }
    
    /*
        testy removeStudent(Student)
    */
    
    @Test
    public void testRemoveStudent_ListContainsObject_ReturnedTrue() {
        boolean result;
        StudentManager.getStudentsList().add(s);
        
        result = StudentManager.removeStudent(s);
        
        assertTrue(result);
    }
    
    @Test
    public void testRemoveStudent_ListDoesNotContainObject_ReturnedFalse() {
        boolean result;
        
        result = StudentManager.removeStudent(s);
        
        assertFalse(result);
    }
    
    @Test
    public void testRemoveStudent_ListContainsObject_ObjectRemovedFromList() {
        boolean result = false;
        StudentManager.getStudentsList().add(s);
        
        StudentManager.removeStudent(s);
        
        for(Student st : StudentManager.getStudentsList()) {
            if(s.getName().equals(st.getName()) && s.getSurname().equals(st.getSurname()) && s.getStudentNumber().equals(st.getStudentNumber()))
                result = true;
        }
        
        assertFalse(result);
    }
    
    @Test
    public void testRemoveStudent_ListContainsObject_ListSizeDecreasedBy1() {
        StudentManager.getStudentsList().add(s);
        int size_before = StudentManager.getStudentsList().size();
        
        StudentManager.removeStudent(s);
        int size_after = StudentManager.getStudentsList().size();
        
        assertEquals(1, size_before - size_after);
    }
    
    @Test
    public void testRemoveStudent_NullObject_ReturnedFalse() {
        boolean result;
        
        result = StudentManager.removeStudent(null);
        
        assertFalse(result);
    }
    
    /*
        testy updateStudent(int, Student)
    */
    
    @Test
    public void testUpdateStudent_ValidationWasSuccessful_TrueReturned() {
        boolean validationResult = true;
        StudentManager.getStudentsList().add(s);
        Student newStudent = new Student("Marcin", "Nowak", "D345363");
        
        boolean result = StudentManager.updateStudent(0, newStudent, validationResult);
        
        assertTrue(result);
    }
    
    @Test
    public void testUpdateStudent_ValidationWasUnsuccessful_FalseReturned() {
        boolean validationResult = false;
        StudentManager.getStudentsList().add(s);
        Student newStudent = new Student("Marcin", "Nowak", "D345363");
        
        boolean result = StudentManager.updateStudent(0, newStudent, validationResult);
        
        assertFalse(result);
    }
    
    @Test
    public void testUpdateStudent_ValidationWasSuccessful_UpdatedObjectEqualsToNewStudentObject() {
        boolean validationResult = true;
        StudentManager.getStudentsList().add(s);
        int position = 0;
        Student newStudent = new Student("Marcin", "Nowak", "D345363");
        
        StudentManager.updateStudent(position, newStudent, validationResult);
        
        Student updatedStudent = StudentManager.getStudentsList().get(position);
        assertEquals("Marcin", updatedStudent.getName());
        assertEquals("Nowak", updatedStudent.getSurname());
        assertEquals("D345363", updatedStudent.getStudentNumber());
    }
    
    @Test
    (expected=IllegalArgumentException.class)
    @Parameters({"-1", "4"})
    public void testUpdateStudent_PositionWasOutOfBounds_Exception(int position) {
        boolean validationResult = true;
        Student newStudent = new Student("Marcin", "Nowak", "D345363");
        
        StudentManager.updateStudent(position, newStudent, validationResult);
    }
    
    /*
        testy calculateAbsencesNumber(Student)
    */
    
    @Test
    @Parameters({"0, 0", "1, 3", "2, 1"})
    public void testCalculateAbsencesNumber_AbsencesNumberCorrectlyCalculated(int position, int expected) {
        StudentManager.setStudentsList(Arrays.asList(s1, s2, s3));
        List<List<Boolean>> listOfPresencesList = Arrays.asList(Arrays.asList(true, true, false),
                                                       Arrays.asList(true, false, true),
                                                       Arrays.asList(true, false, true),
                                                       Arrays.asList(true, false, true));
        for(int i = 1; i <= 4; i++) {
            LessonManager.getLessonsList().add(new Lesson(new Date(2017,11,i)));
            LessonManager.getLessonsList().get(i-1).setPresencesList(listOfPresencesList.get(i-1));
        }
        
        int studentAbsencesNumber = StudentManager.calculateAbsencesNumber(position);
        
        assertEquals(expected, studentAbsencesNumber);
    }
    
    /*
        testy calculateGroupAverage()
    */
    
    @Test
    public void testCalculateGroupAverage_StudentsListIsEmpty_ZeroReturned() {
        double result;
        
        result = StudentManager.calculateGroupAverage();
        
        assertEquals(0.0, result, 0.1);
    }
    
    @Test
    @Parameters({"2.0, 3.0, 4.0, 5.0, 3.5", 
                 "4.0, 3.5, 2.0, 5.0, 3.625"})
    public void testCalculateGroupAverage_StudentsListAndMarksListAreNotEmpty_AverageCorrectlyCalculated(double m1, double m2, double m3, double m4, double expected) {
        double result;
        StudentManager.setStudentsList(Arrays.asList(s1, s2));
        s1.addMark(m1, true);
        s1.addMark(m2, true);
        s2.addMark(m3, true);
        s2.addMark(m4, true);
        
        result = StudentManager.calculateGroupAverage();
        
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    public void testCalculateGroupAverage_StudentsListIsNotEmptyAndMarksListIsEmpty_ZeroReturned() {
        double result;
        StudentManager.setStudentsList(Arrays.asList(s1, s2));
        
        result = StudentManager.calculateGroupAverage();
        
        assertEquals(0.0, result, 0.1);
    }
    
}