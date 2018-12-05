/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author HP
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Lesson {
    
    @XmlAttribute(name="date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date;
    @XmlElement(name="presence")
    private List<Boolean> presencesList; // lista obecności - TRUE: obecny, FALSE: nieobecny
    
    public Lesson() {}
    
    public Lesson(Date _date) {
        date = _date;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date _date) {
        date = _date;
    }
    
    public List<Boolean> getPresencesList() {
        return presencesList;
    }
    
    public void setPresencesList(List<Boolean> _presencesList) {
        presencesList = _presencesList;
    }
    
    public boolean addPresence(boolean presence) {
        if(presencesList.size() < StudentManager.getStudentsList().size()) { // liczba obecności nie może być większa od liczby studentów
            presencesList.add(presence);
            return true;
        }
        return false;
    }
    
    public void updatePresence(int position, boolean newPresence) {
        if(position >= presencesList.size() || position < 0)
            throw new IllegalArgumentException("Przekroczenie zakresu listy obecności.");
        presencesList.set(position, newPresence);
    }
}
