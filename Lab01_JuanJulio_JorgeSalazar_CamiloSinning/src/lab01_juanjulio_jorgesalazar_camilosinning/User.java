/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab01_juanjulio_jorgesalazar_camilosinning;

/**
 *
 * @author camil
 */
public class User {
    int id;
    String username;
    String name;
    String email;
    String address;
    String phone;
    String website;
    String company;
    Post[] myPosts;  
    //Lista de post

    public User(int id, String username, String name, String email, String address, String phone, String website, String company, int HMPosts) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
        this.myPosts = new Post[HMPosts];
    }
    
    
}
