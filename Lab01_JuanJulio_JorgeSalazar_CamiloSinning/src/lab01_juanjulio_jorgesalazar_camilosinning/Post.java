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

    //Hermano de Post
    Post Link;

    public Post(int userId, String post, String title, int id) {
        this.userId = userId;
        this.post = post;
        this.title = title;
        this.id = id;
        //myComments = new ArrayList<>();
    }

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

    public int CommentsSize() {
        Comment c = CommentPTR;
        int i = 0;
        while (c != null) {
            i = i + 1;
            c = c.Link;
        }
        return i;

    }

    public Comment next(int indexArray) {
        Comment c = CommentPTR;
        int i = 0;
        while (i < indexArray + 1) {
            c = c.Link;
            i=i+1;
        }
        return c;
    }

    public Comment back(int indexArray) {
        Comment c = CommentPTR;
        int i = 0;
        while (i < indexArray - 1) {
            c = c.Link;
            i=i+1;
        }
        return c;
    }

}
