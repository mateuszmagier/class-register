/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author HP
 */
public class XMLHelper {
    
    public XMLHelper() {  }
    
    public boolean studentsToXml(File xmlFile) throws JAXBException, FileNotFoundException {
        if(xmlFile == null) 
            throw new IllegalArgumentException();
        if(StudentManager.getStudentsList().size() > 0) {
            JAXBContext jaxbContext = JAXBContext.newInstance(StudentManager.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(new StudentManager(), xmlFile);
            return true;
        }
        return false;
    }
    
    public boolean lessonsToXml(File xmlFile) throws JAXBException, FileNotFoundException {
        if(xmlFile == null) 
            throw new IllegalArgumentException();
        if(LessonManager.getLessonsList().size() > 0) {
            JAXBContext jaxbContext = JAXBContext.newInstance(LessonManager.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(new LessonManager(), xmlFile);
            return true;
        }
        return false;
    }
    
    public void xmlToStudents(File xmlFile) throws FileNotFoundException, JAXBException {
        if(xmlFile == null) 
            throw new IllegalArgumentException();
        if(!xmlFile.exists())
            throw new FileNotFoundException();
        
        JAXBContext jaxbContext = JAXBContext.newInstance(StudentManager.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        StudentManager sm = (StudentManager) jaxbUnmarshaller.unmarshal(xmlFile);
    }
    
    public void xmlToLessons(File xmlFile) throws FileNotFoundException, JAXBException {
        if(xmlFile == null) 
            throw new IllegalArgumentException();
        if(!xmlFile.exists())
            throw new FileNotFoundException();
        
        JAXBContext jaxbContext = JAXBContext.newInstance(LessonManager.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        LessonManager lm = (LessonManager) jaxbUnmarshaller.unmarshal(xmlFile);
    }
}
