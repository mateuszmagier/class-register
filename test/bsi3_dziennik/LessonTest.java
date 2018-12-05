/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.text.ParseException;
import java.util.ArrayList;
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
public class LessonTest {
    
    Lesson l;
    
    public LessonTest() {
    }

    @Before
    public void beforeTest() throws ParseException {
        Date date = LessonManager.df.parse("2017-11-26");
        l = new Lesson(date);
        l.setPresencesList(new ArrayList<>());
    }
    
    /*
        testy konstruktora
    */
    
    @Test
    public void testLesson_CorrectDate_LessonObjectCreated() {
        assertNotNull(l);
    }
    
    /*
        testy getterów i setterów
    */
    
    @Test
    public void testGetDate_ObjectWasCreated_GetDateReturnedDate() throws ParseException {
        Date expectedDate = LessonManager.df.parse("2017-11-26");
        
        Date resultDate = l.getDate();
        
        int result = resultDate.compareTo(expectedDate);
        assertEquals(0, result);
    }
    
    @Test
    public void testSetDate_DateWasChanged_ObjectDateEqualsToNewDate() throws ParseException {
        Date newDate = LessonManager.df.parse("2017-11-26");
        
        l.setDate(newDate);
        
        int result = l.getDate().compareTo(newDate);
        assertEquals(0, result);
    }
    
    @Test
    public void testGetPresencesList_ObjectHasList_PresencesListReturned() {
        List<Boolean> result;
        result = l.getPresencesList();
        assertNotNull(result);
    }
    
    @Test
    public void testGetPresencesList_ObjectHasNoList_NullReturned() {
        List<Boolean> result;
        l.setPresencesList(null);
        result = l.getPresencesList();
        assertNull(result);
    }
    
    @Test
    public void testSetPresencesList_NewPresencesList() {
        List<Boolean> result;
        result = l.getPresencesList();
        assertNotNull(result);
    }
    
    /*
        testy addPresence(boolean)
    */
    
    @Test
    public void testAddPresence_PresencesListSizeIsLessThanStudentsListSize_TrueReturned() {
        boolean result;
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student("Jan", "Kowalski", "D344636"));
        StudentManager.setStudentsList(studentsList);
        
        result = l.addPresence(true);
        
        assertTrue(result);
    }
    
    @Test
    public void testAddPresence_PresencesListSizeEqualsStudentsListSize_FalseReturned() {
        boolean result;
        StudentManager.setStudentsList(new ArrayList<>());
        
        result = l.addPresence(true);
        
        assertFalse(result);
    }
    
    @Test
    @Parameters({"true", "false"})
    public void testAddPresence_CorrectPresence_PresenceAddedToList(boolean presence) {
        boolean result;
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student("Jan", "Kowalski", "D344636"));
        StudentManager.setStudentsList(studentsList);
        
        l.addPresence(presence);
        result = l.getPresencesList().get(0);
        
        assertEquals(presence, result);
    }
    
    /*
        testy updatePresence(int, boolean)
    */
    
    @Test
    (expected=IllegalArgumentException.class)
    @Parameters({"-1", "3"})
    public void testAddPresence_PositionWasOutOfBounds_Exception(int position) {
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student("Jan", "Kowalski", "D344636"));
        StudentManager.setStudentsList(studentsList);
        l.getPresencesList().add(true);
        
        l.updatePresence(position, false);
    }
    
    @Test
    @Parameters({"true, false", "false, true"})
    public void testAddPresence_PositionWasCorrect_PresenceWasUpdatedSuccessfully(boolean prevPresence, boolean newPresence) {
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student("Jan", "Kowalski", "D344636"));
        StudentManager.setStudentsList(studentsList);
        l.getPresencesList().add(prevPresence);
        
        l.updatePresence(0, newPresence);
        
        assertEquals(newPresence, l.getPresencesList().get(0));
    }
}
