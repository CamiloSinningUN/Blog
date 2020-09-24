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

    //inserta usuarios a la raiz
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
    
    //metodo que devuelve un entero del siguiente post
    public int next(int Here, JEditorPane principalEditorPane) {

        User u = UserPTR;
        while (u != null) {

            Here = u.next(Here, principalEditorPane);
            u = u.Link;
        }
        return Here;
    }

    //metodo que devuelve un entero del anterior post
    public int back(int Here, JEditorPane principalEditorPane){       
        User u = UserPTR;
         while (u != null) {
            Here = u.back(Here, principalEditorPane);           
            u = u.Link;
        }
        return Here;
    }

    //Busca usuario con determinada id
    public User SearchUser(int id) {
        User u = UserPTR;
        while ( (u != null )&& (u.id != id) ) {
            u = u.Link;
        }
        return u;
    }
    
    //busca usuario con determinado nombre
    public User SearchName(String name){
        User u = UserPTR;
        while( (u != null )&& !(u.name.toLowerCase().contains(name.toLowerCase())) ){
            u=u.Link;
        }
        return u;
    }
    
   
}
