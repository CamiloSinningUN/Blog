/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab01_juanjulio_jorgesalazar_camilosinning;

import java.util.ArrayList;

/**
 *
 * @author camil
 */
public class Post {

    int id;
    int userId;
    String post;
    String title;
    Comment CommentPTR;
    Post Link;

    public Post(int userId, String post, String title, int id) {
        this.userId = userId;
        this.post = post;
        this.title = title;
        this.id = id;
    }

    //inserta commentarios al post
    public void InsertComment(Comment comment) {
        Comment c = CommentPTR;
        if (CommentPTR == null) {
            CommentPTR = comment;
        } else {
            while (c.Link != null) {
                c = c.Link;
            }
            c.Link = comment;
        }
    }

    //te da cuantos comenatrios posee el post
    public int CommentsSize() {
        Comment c = CommentPTR;
        int i = 0;
        while (c != null) {
            i = i + 1;
            c = c.Link;
        }
        return i;

    }

    

}
