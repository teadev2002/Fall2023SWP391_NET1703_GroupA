package com.swp391.DogCatLoverPlatform.entity.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserBlogCommentKey implements Serializable {

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_blog")
    private int idBlog;

    @Column(name="id_comment")
    private int idComment;

    public UserBlogCommentKey(int idUser, int idBlog, int idComment) {
        this.idUser = idUser;
        this.idBlog = idBlog;
        this.idComment = idComment;
    }

    public UserBlogCommentKey() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(int idBlog) {
        this.idBlog = idBlog;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }
}
