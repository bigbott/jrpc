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
public class Order {
    public String id;
    public double totalPrice;
    public String date;
    public Customer customer;
    public Product[] products;
    
    
    class Customer{
        public String id;
        public String name;
        public Address address;
    }
    
    class Address {
        public String country;
        public String city;
        public String zipCode;
        public String street;
        public int buildingNumber;
    }
    
    class Product {
        public String id;
        public double price;
        public String title;
        public String sku;
    }
    
}
