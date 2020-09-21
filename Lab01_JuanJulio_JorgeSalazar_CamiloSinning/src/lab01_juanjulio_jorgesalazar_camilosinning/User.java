/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab01_juanjulio_jorgesalazar_camilosinning;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JEditorPane;

/**
 *
 * @author camil
 */
public class User {

    int id;
    String username;
    String name;
    String email;
    Address address;
    String phone;
    String website;
    Company company;
    Post PostPTR;
    //Lista de post

    //Link a Hermanos de User
    User Link;

    public User(int id, String username, String name, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
        //myPosts = new ArrayList<>();
    }

    public void InsertPost(Post post) {
        Post p = PostPTR;
        if (PostPTR == null) {
            PostPTR = post;
        } else {

            while (p.Link != null) {
                p = p.Link;
            }
            p.Link = post;
        }
    }

    public int PostsSize() {
        int i = 0;
        Post p =  PostPTR;       
        while (p != null) {
            i = i + 1;
            p = p.Link;
        }
        return i;
    }

    public int next(int Here, JEditorPane principalEditorPane) {     
        Post p = PostPTR;
        while(p!=null){
            if (p.id == Here + 1) {
                String title = p.title;
                String post = p.post;
                System.out.println(p.id);
                principalEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);
                Here = Here + 1;              
                break;
            }
            p=p.Link;
        }
        return Here;
    }

    public int back(int Here, JEditorPane principalEditorPane) { 
        
        Post p = PostPTR;
        while(p != null){        
            if (p.id == Here - 1) {
                String title = p.title;
                String post = p.post;
                System.out.println(p.id);
                principalEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);
                Here = Here - 1;              
                break;
            }
            
            p=p.Link;
        }
        return Here;
    }
}
