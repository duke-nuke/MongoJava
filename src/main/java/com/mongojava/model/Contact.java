/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongojava.model;

/**
 *
 * @author Khurram.Shahzad
 */
public class Contact {
    
    private String email;
    private String cell;
    
    public Contact(){
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "Contact{" + "email=" + email + ", cell=" + cell + '}';
    }
    
    
}
