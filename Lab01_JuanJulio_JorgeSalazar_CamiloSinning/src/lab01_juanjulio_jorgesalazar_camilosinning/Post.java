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
public class Post {
    
    int id;
    int userId;
    String post;
    String title;   
    Comment[] myComments;

    public Post(int userId, String post, String title, int id, int HMComments) {
        this.userId = userId;
        this.post = post;
        this.title = title;
        this.id = id;
        this.myComments = new Comment[HMComments];
    }
    
}
