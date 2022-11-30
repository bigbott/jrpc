/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jrpc.example.data;

/**
 *
 * @author Owner
 */
public class User {
    public String name;
    public int age;
    public boolean isMale;
    
    public User (String name, int age, boolean isMale){
        this.name = name;
        this.age = age;
        this.isMale = isMale;
    }
}
