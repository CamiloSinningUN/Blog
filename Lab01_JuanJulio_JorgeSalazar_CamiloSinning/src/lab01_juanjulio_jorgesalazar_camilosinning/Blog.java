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
    User UserPTR;

    public void InsertUser(User user) {
        User u = UserPTR;
        if (UserPTR == null) {
            UserPTR = user;
        } else {

            while (u.Link != null) {
                u = u.Link;
            }
            u.Link = user;
        }
    }

    public int next(int Here, JEditorPane principalEditorPane) {

        User u = UserPTR;
        while (u != null) {

            Here = u.next(Here, principalEditorPane);
            u = u.Link;
        }
        return Here;
    }

    public int back(int Here, JEditorPane principalEditorPane){       
        User u = UserPTR;
         while (u != null) {
            Here = u.back(Here, principalEditorPane);           
            u = u.Link;
        }
        return Here;
    }

    public User SearchUser(int id) {
        User u = UserPTR;
        while ( (u != null )&& (u.id != id) ) {
            u = u.Link;
        }
        return u;
    }
    
    public User SearchName(String name){
        User u = UserPTR;
        while( (u != null )&& !(u.name.toLowerCase().contains(name.toLowerCase())) ){
            u=u.Link;
        }
        return u;
    }
    
   
}
