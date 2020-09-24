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
public class Comment {
    String comment;
    int postId;
    int id;
    String name;
    String email;
    Comment Link;
    

    public Comment(String comment, int postId,int id, String name, String email) {
        this.comment = comment;
        this.postId = postId;
        this.id=id;
        this.name = name;
        this.email = email;       
    }
    
}
