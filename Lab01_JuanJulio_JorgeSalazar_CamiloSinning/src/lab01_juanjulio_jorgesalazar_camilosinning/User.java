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
    ArrayList<Post> myPosts;
    //Lista de post

    public User(int id, String username, String name, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
        myPosts = new ArrayList<>();
    }

    public int next(int Here, JEditorPane principalEditorPane, JButton backButton, JButton nextButton) {
        for (Post p : myPosts) {
            if (p.id == Here + 1) {
                String title = p.title;
                String post = p.post;
                System.out.println(p.id);
                principalEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);
                Here = Here + 1;
                if (Here > 1) {
                    backButton.setEnabled(true);
                }
                if (Here >= 100) {
                    nextButton.setEnabled(false);
                }
                    
                
                break;
            }
        }
        return Here;
    }

    public int back(int Here, JEditorPane principalEditorPane, JButton backButton, JButton nextButton) {
        for (Post p : myPosts) {
            if (p.id == Here - 1) {
                String title = p.title;
                String post = p.post;
                System.out.println(p.id);
                principalEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);
                Here = Here - 1;
                if (Here == 1) {
                    backButton.setEnabled(false);
                }
                if (Here < 100) {
                    nextButton.setEnabled(true);
                }

                break;
            }
        }
        return Here;
    }

}
