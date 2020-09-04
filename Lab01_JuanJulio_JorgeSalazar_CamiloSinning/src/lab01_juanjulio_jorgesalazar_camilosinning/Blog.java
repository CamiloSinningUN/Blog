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
public class Blog {
    String name;
    User[] myUsers;

    public Blog(int HMUsers) {      
        this.myUsers = new User[HMUsers];
    }
    
}
