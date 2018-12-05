/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author HP
 */
public class LessonManagerTest {
    
    Lesson l;
    
    public LessonManagerTest() {
    }

    @Before
    public void beforeTest() {
        l = new Lesson(new Date(2017,11,26));
        LessonManager.setLessonsList(new ArrayList<>());
    }
    
    /*
        testy getterów i setterów
    */
    
    @Test
    public void testGetLessonsList_ObjectHasList_LessonsListReturned() {
        List<Lesson> result;
        result = LessonManager.getLessonsList();
        assertNotNull(result);
    }
    
    @Test
    public void testSetLessonsList_NewListSizeWasLessThanLimit_TrueReturned() {
        boolean result;
        List<Lesson> list = new ArrayList<>();
        list.add(l);
        result = LessonManager.setLessonsList(list);
        assertTrue(result);
    }
    
    /*
        testy addLesson(Lesson)
    */
    
    @Test
    public void testAddLesson_LessonsListSizeIsLessThanMax_TrueReturned() {
        LessonManager.getLessonsList().add(l);
        Lesson newLesson = new Lesson(new Date(2017,11,11));
        
        boolean result = LessonManager.addLesson(newLesson);
        
        assertTrue(result);
    }
    
    @Test
    public void testAddLesson_LessonsListSizeEqualsToMax_FalseReturned() {
        for(int i = 1; i <= 15; i++)
            LessonManager.getLessonsList().add(new Lesson(new Date(2017,11,i)));
        
        boolean result = LessonManager.addLesson(l);
        
        assertFalse(result);
    }
    
    @Test
    public void testAddLesson_LessonObjectWasCorrect_ListSizeIncreasedBy1() {
        int size_before = LessonManager.getLessonsList().size();
        LessonManager.addLesson(l);
        int size_after = LessonManager.getLessonsList().size();
        assertEquals(1, size_after - size_before);
    }
    
    @Test
    public void testAddLesson_LessonObjectWasCorrect_AddedLessonAndLessonFromListEquals() {
        LessonManager.addLesson(l);
        int size = LessonManager.getLessonsList().size();
        Lesson lesson = LessonManager.getLessonsList().get(size-1);
        Date lessonDate = lesson.getDate();
        
        int result = lessonDate.compareTo(l.getDate());
        assertEquals(0, result);
    }
}
