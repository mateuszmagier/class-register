/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.text.DateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author HP
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    private final DateFormat dateFormat = LessonManager.df;

    @Override
    public Date unmarshal(String xml) throws Exception {
        return dateFormat.parse(xml);
    }

    @Override
    public String marshal(Date object) throws Exception {
        return dateFormat.format(object);
    }
}
