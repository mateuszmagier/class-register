/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.text.SimpleDateFormat;

/**
 *
 * @author HP
 */
public class CustomDate extends java.sql.Date {
    public CustomDate(long date) {
        super(date);
    }

    public CustomDate(int i, int i0, int i1) {
        super(i-1900, i0, i1);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this);
    }
}
