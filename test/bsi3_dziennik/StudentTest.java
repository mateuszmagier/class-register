/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 *
 * @author HP
 */
@RunWith(JUnitParamsRunner.class)
public class StudentTest {
    
    public Student s;
    
    public StudentTest() {
    }
    
    @Before
    public void beforeTest() {
        s = new Student("Jan", "Nowak", "D654781");
    }

    /*
        testy konstruktora
    */
    
    @Test
    public void testStudent_CorrectParameters_NewObjectCreated () {
        assertNotNull(s);
    }
    
    /*
        testy getterów
    */
    
    @Test
    public void testGetImie_ObjectWasCreated_GetNameReturnedName() {
        String expectedResult = "Jan";
        
        String result = s.getName();
        
        assertEquals(expectedResult, result);
    }
    
    @Test
    public void testGetNazwisko_ObjectWasCreated_GetSurnameReturnedSurname() {
        String expectedResult = "Nowak";
        
        String result = s.getSurname();
        
        assertEquals(expectedResult, result);
    }
    
    @Test
    public void testGetStudentNumber_ObjectWasCreated_GetStudentNumberReturnedStudentNumber() {
        String expectedResult = "D654781";
        
        String result = s.getStudentNumber();
        
        assertEquals(expectedResult, result);
    }
    
    @Test
    public void testGetMarks_ObjectWasCreated_GetMarksReturnedNotNull() {
        List<Double> result;
        
        result = s.getMarks();
        
        assertNotNull(result);
    }
    
    /*
        testy setterów
    */
    
    @Test
    public void testSetName_NameWasChanged_ObjectsNameEqualsToNewName() {
        String newName = "Marcin";
        
        s.setName(newName);
        String result = s.getName();
        
        assertEquals(newName, result);
    }
    
    @Test
    public void testSetSurname_SurnameWasChanged_ObjectsSurnameEqualsToNewSurname() {
        String newSurname = "Kowalski";
        
        s.setSurname(newSurname);
        String result = s.getSurname();
        
        assertEquals(newSurname, result);
    }
    
    @Test
    public void testSetStudentNumber_StudentNumberWasChanged_ObjectsStudentNumberEqualsToNewStudentNumber() {
        String newStudentNumber = "E121780";
        
        s.setStudentNumber(newStudentNumber);
        String result = s.getStudentNumber();
        
        assertEquals(newStudentNumber, result);
    }
    
    @Test
    public void testSetMarks_MarksListIsNotEmpty_MarksWasSuccessfullySet() {
        List<Double> marks = Arrays.asList(4.5, 2.0, 4.0);
        
        s.setMarks(marks);
        
        List<Double> studentMarks = s.getMarks();
        assertThat(studentMarks, is(marks));
    }
    
    /*
        testy update(Student)
    */
    
    @Test
    public void testUpdate_NewStudentObject_UpdatedObjectEqualsToNewStudentObject() {
        Student newStudent = new Student("Marcin", "Nowak", "D345363");
        
        s.update(newStudent);
        
        assertEquals("Marcin", s.getName());
        assertEquals("Nowak", s.getSurname());
        assertEquals("D345363", s.getStudentNumber());
    }
    
    /*
        testy addMark(double)
    */
    
    @Test
    @Parameters({"2.0", "3.5", "4.0", "5.0"})
    public void testAddMark_ValidationWasSuccessful_ReturnedTrue(double mark) {
        boolean result;
        boolean validationResult = true;
        
        result = s.addMark(mark, validationResult);
        
        assertTrue(result);
    }
    
    @Test
    public void testAddMark_ValidationWasUnsuccessful_ReturnedFalse() {
        boolean result;
        boolean validationResult = false;
        
        result = s.addMark(1.0, validationResult);
        
        assertFalse(result);
    }
    
    @Test
    @Parameters({"2.0, 2.0", "3.0, 3.0", "4.5, 4.5"})
    public void testAddMark_ValidationWasSuccessful_AddedMarkEqualsToMarkFromList(double mark, double expected) {
        double result;
        boolean validationResult = true;
        
        s.addMark(mark, validationResult);
        
        result = s.getMarks().get(0);
        assertEquals(expected, result, 0.1);
    }
    
    /*
        testy validateMark(double)
    */
    
    @Test
    @Parameters({"2.0", "3.5", "4.0", "5.0"})
    public void testValidateMark_MarkWasCorrect_ReturnedTrue(double mark) {
        boolean result;
        
        result = Student.validateMark(mark);
        
        assertTrue(result);
    }
    
    @Test
    @Parameters({"-2.0", "1.9", "5.1", "5.5"})
    public void testValidateMark_MarkWasIncorrect_ReturnedFalse(double mark) {
        boolean result;
        
        result = Student.validateMark(mark);
        
        assertFalse(result);
    }
    
    /*
        testy updateMark(int, double)
    */
    
    @Test
    public void testUpdateMark_ValidationWasSuccessful_ReturnedTrue() {
        boolean result;
        boolean validationResult = true;
        s.getMarks().add(2.0);
        
        result = s.updateMark(0, 3.0, validationResult);
        
        assertTrue(result);
    }
    
    @Test
    public void testUpdateMark_ValidationWasUnsuccessful_ReturnedFalse() {
        boolean result;
        boolean validationResult = false;
        s.getMarks().add(2.0);
        
        result = s.updateMark(0, 3.1, validationResult);
        
        assertFalse(result);
    }
    
    @Test
    @Parameters({"0, 3.5", "2, 4.0", "3, 5.0"})
    public void testUpdateMark_ValidationWasSuccessful_SpecifiedMarkWasSuccessfullyUpdated(int position, double newMark) {
        List<Double> marks = Arrays.asList(3.0, 4.0, 3.0, 4.5, 5.0);
        s.setMarks(marks);
        
        s.updateMark(position, newMark, true);
        
        double result = s.getMarks().get(position);
        assertEquals(newMark, result, 0.1);
    }
    
    @Test
    (expected=IllegalArgumentException.class)
    @Parameters({"-1", "3"})
    public void testUpdateMark_PositionWasOutOfBounds_Exception(int position) {
        s.updateMark(position, 4.5, true);
    }
    
    /*
        testy removeMark(int)
    */
    
    @Test
    @Parameters({"3.0", "4.5"})
    public void testRemoveMark_MarksListContainsMark_TrueReturned(double mark) {
        List<Double> marks = new ArrayList<>();
        marks.add(3.0);
        marks.add(4.5);
        s.setMarks(marks);
        boolean result;
        
        result = s.removeMark(mark);
        
        assertTrue(result);
    }
    
    @Test
    @Parameters({"2.0", "5.0"})
    public void testRemoveMark_MarksListDoesNotContainMark_FalseReturned(double mark) {
        List<Double> marks = new ArrayList<>();
        marks.add(3.0);
        marks.add(4.5);
        s.setMarks(marks);
        boolean result;
        
        result = s.removeMark(mark);
        
        assertFalse(result);
    }
    
    /*
        testy calculateAverage()
    */
    
    @Test
    public void testCalculateAverage_MarksListIsEmpty_ZeroReturned() {
        List<Double> marks = new ArrayList<>();
        s.setMarks(marks);
        double result;
        
        result = s.calculateAverage();
        
        assertEquals(0.0, result, 0.1);
    }
    
    @Test
    @Parameters({"2.0, 4.5, 3.5, 3.333", "5.0, 5.0, 5.0, 5.0", "2.0, 2.0, 5.0, 3.0"})
    public void testCalculateAverage_MarksListIsNotEmpty_AverageCorrectlyCalculated(double m1, double m2, double m3, double expected) {
        List<Double> marks = new ArrayList<>();
        marks.add(m1);
        marks.add(m2);
        marks.add(m3);
        s.setMarks(marks);
        double precision = 0.001;
        double result;
        
        result = s.calculateAverage();
        
        assertEquals(expected, result, precision);
    }
}