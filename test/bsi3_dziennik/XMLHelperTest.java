/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author HP
 */
public class XMLHelperTest {
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    public File tempFile;
    Student s1, s2, s3;
    Lesson l1, l2, l3;
    
    XMLHelper xml;
    
    public XMLHelperTest() {
    }
    
    @Before
    public void beforeTest() throws IOException, ParseException {
        tempFile = tempFolder.newFile("file.xml");
        xml = new XMLHelper();
        s1 = new Student("Jan", "Kowalski", "D433467");
        s2 = new Student("Marcin", "Nowak", "D433566");
        s3 = new Student("Janusz", "Rodzynek", "D436666");
        s1.addMark(4.5, true);
        s1.addMark(3.5, true);
        l1 = new Lesson(LessonManager.df.parse("2017-11-25"));
        l2 = new Lesson(LessonManager.df.parse("2017-11-26"));
        l3 = new Lesson(LessonManager.df.parse("2017-11-27"));
        
        l1.setPresencesList(Arrays.asList(true, true, false));
        l2.setPresencesList(Arrays.asList(false, true, true));
        l3.setPresencesList(Arrays.asList(true, false, false));
    }
    
    /*
        testy konstruktora
    */
    
    @Test
    public void testXMLHelper_NewObjectCreated() {
        assertNotNull(xml);
    }
    
    /*
        testy studentsToXml(File)
    */
    
    @Test
    public void testStudentsToXml_StudentsListIsNotEmpty_ReturnedTrue() throws JAXBException, FileNotFoundException {
        boolean result;
        StudentManager.setStudentsList(Arrays.asList(s1, s2, s3));
        
        result = xml.studentsToXml(tempFile);
        
        assertTrue(result);
    }
    
    @Test
    public void testStudentsToXml_StudentsListIsEmpty_ReturnedFalse() throws JAXBException, FileNotFoundException {
        boolean result;
        StudentManager.setStudentsList(new ArrayList<>());
        
        result = xml.studentsToXml(tempFile);
        
        assertFalse(result);
    }
    
    @Test
    (expected=IllegalArgumentException.class)
    public void testStudentsToXml_FileIsNull_Exception() throws JAXBException, FileNotFoundException {
        StudentManager.setStudentsList(Arrays.asList(s1, s2, s3));
        
        xml.studentsToXml(null);
    }
    
    @Test
    public void testStudentsToXml_StudentsListIsNotEmptyFileExists_XmlIsCorrect() throws JAXBException, FileNotFoundException, IOException {
        StudentManager.setStudentsList(Arrays.asList(s1, s2, s3));
        String result;
        String expected =   "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                            "<students>\n" +
                            "    <student name=\"Jan\" surname=\"Kowalski\" student_number=\"D433467\">\n" +
                            "        <mark>4.5</mark>\n" +
                            "        <mark>3.5</mark>\n" +
                            "    </student>\n" +
                            "    <student name=\"Marcin\" surname=\"Nowak\" student_number=\"D433566\"/>\n" +
                            "    <student name=\"Janusz\" surname=\"Rodzynek\" student_number=\"D436666\"/>\n" +
                            "</students>\n";
        
        xml.studentsToXml(tempFile);
        result = FileUtils.readFileToString(tempFile, "utf-8");
        
        assertEquals(expected, result);
    }
    
    /*
        testy lessonsToXml(File)
    */
    
    @Test
    public void testLessonsToXml_LessonsListIsNotEmpty_ReturnedTrue() throws JAXBException, Exception {
        boolean result;
        LessonManager.setLessonsList(Arrays.asList(l1, l2, l3));
        
        result = xml.lessonsToXml(tempFile);
        
        assertTrue(result);
    }
    
    @Test
    public void testLessonsToXml_LessonsListIsEmpty_ReturnedFalse() throws JAXBException, Exception {
        boolean result;
        LessonManager.setLessonsList(new ArrayList<>());
        
        result = xml.lessonsToXml(tempFile);
        
        assertFalse(result);
    }
    
    @Test
    (expected=IllegalArgumentException.class)
    public void testLessonsToXml_FileIsNull_Exception() throws JAXBException, Exception {
        LessonManager.setLessonsList(Arrays.asList(l1, l2, l3));
        
        xml.lessonsToXml(null);
    }
    
    @Test
    public void testLessonsToXml_LessonsListIsNotEmptyFileExists_XmlIsCorrect() throws JAXBException, FileNotFoundException, IOException {
        LessonManager.setLessonsList(Arrays.asList(l1, l2, l3));
        String result;
        String expected =   "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                            "<lessons>\n" +
                            "    <lesson date=\"2017-11-25\">\n" +
                            "        <presence>true</presence>\n" +
                            "        <presence>true</presence>\n" +
                            "        <presence>false</presence>\n" +
                            "    </lesson>\n" +
                            "    <lesson date=\"2017-11-26\">\n" +
                            "        <presence>false</presence>\n" +
                            "        <presence>true</presence>\n" +
                            "        <presence>true</presence>\n" +
                            "    </lesson>\n" +
                            "    <lesson date=\"2017-11-27\">\n" +
                            "        <presence>true</presence>\n" +
                            "        <presence>false</presence>\n" +
                            "        <presence>false</presence>\n" +
                            "    </lesson>\n" +
                            "</lessons>\n";
        
        xml.lessonsToXml(tempFile);
        result = FileUtils.readFileToString(tempFile, "utf-8");
        
        assertEquals(expected, result);
    }
    
    /*
        testy xmlToStudents(File)
    */
    
    @Test
    (expected=IllegalArgumentException.class)
    public void testXmlToStudents_FileIsNull_Exception() throws FileNotFoundException, JAXBException {
        xml.xmlToStudents(null);
    }
    
    @Test
    (expected=FileNotFoundException.class)
    public void testXmlToStudents_FileDoesNotExist_Exception() throws FileNotFoundException, JAXBException {
        tempFile.delete();
        
        xml.xmlToStudents(tempFile);
    }
    
    @Test
    public void testXmlToStudents_FileExists_StudentsImportedToList() throws FileNotFoundException, JAXBException, IOException {
        List<Student> list = new ArrayList<>();
        StudentManager.setStudentsList(list);
        String xmlContent =   "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                            "<students>\n" +
                            "    <student name=\"Mariusz\" surname=\"Malinowski\" student_number=\"D433467\">\n" +
                            "        <mark>5.0</mark>\n" +
                            "        <mark>3.0</mark>\n" +
                            "    </student>\n" +
                            "</students>\n";
        FileUtils.writeStringToFile(tempFile, xmlContent, "utf-8");
        xml.xmlToStudents(tempFile);
        Student s = StudentManager.getStudentsList().get(0);
        assertEquals("Mariusz", s.getName());
        assertEquals("Malinowski", s.getSurname());
        assertEquals("D433467", s.getStudentNumber());
        assertEquals(5.0, s.getMarks().get(0), 0.1);
        assertEquals(3.0, s.getMarks().get(1), 0.1);
    }
    
    /*
        testy xmlToLessons(File)
    */
    
    @Test
    (expected=IllegalArgumentException.class)
    public void testXmlToLessons_FileIsNull_Exception() throws FileNotFoundException, JAXBException {
        xml.xmlToLessons(null);
    }
    
    @Test
    (expected=FileNotFoundException.class)
    public void testXmlToLessons_FileDoesNotExist_Exception() throws FileNotFoundException, JAXBException {
        tempFile.delete();
        
        xml.xmlToLessons(tempFile);
    }
    
    @Test
    public void testXmlToLessons_FileExists_LessonsImportedToList() throws FileNotFoundException, JAXBException, IOException, ParseException {
        List<Lesson> list = new ArrayList<>();
        LessonManager.setLessonsList(list);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                            "<lessons>\n" +
                            "    <lesson date=\"2017-11-25\">\n" +
                            "        <presence>true</presence>\n" +
                            "        <presence>false</presence>\n" +
                            "        <presence>true</presence>\n" +
                            "    </lesson>\n" +
                            "</lessons>\n";
        FileUtils.writeStringToFile(tempFile, xmlContent, "utf-8");
        xml.xmlToLessons(tempFile);
        Lesson l = LessonManager.getLessonsList().get(0);
        assertEquals(LessonManager.df.parse("2017-11-25"), l.getDate());
        assertTrue(l.getPresencesList().get(0));
        assertFalse(l.getPresencesList().get(1));
        assertTrue(l.getPresencesList().get(2));
    }
}
