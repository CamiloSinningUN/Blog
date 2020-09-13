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
public class Blog {
    String name;
    ArrayList<User> myUsers;

    public Blog() {      
        myUsers = new ArrayList<>();
        
    }
    
    public int next(int Here, JEditorPane principalEditorPane){
         for (User user : myUsers) {
             Here = user.next(Here, principalEditorPane);           
        }
        return Here; 
    }
    public int back(int Here, JEditorPane principalEditorPane){
        for (User user : myUsers) {
            Here = user.back(Here, principalEditorPane);           
        }
        return Here;
    }
    public User SearchUser(int id){
        User u = null;
        for (User user : myUsers) {
            if(user.id==id){
                u=user;
            }
        }
        return u;
    }
}
