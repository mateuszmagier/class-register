/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author HP
 */
@XmlRootElement(name="lessons")
@XmlAccessorType(XmlAccessType.FIELD)
public class LessonManager {
    @XmlElement(name="lesson")
    private static List<Lesson> lessonsList;
    private static final int MAX = 15; // maksymalna liczba spotkań
    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    
    public LessonManager() {}
    
    public static List<Lesson> getLessonsList() {
        return lessonsList;
    }
    
    public static boolean setLessonsList(List<Lesson> _lessonsList) {
        if(_lessonsList == null)
            return false;
        if(_lessonsList.size() < MAX) {
            lessonsList = _lessonsList;
            return true;
        }
        return false;
    }
    
    public static boolean addLesson(Lesson l) {
        if(lessonsList.size() < MAX) {
            lessonsList.add(l);
            return true;
        }
        return false;
    }
}
