package com.swp391.DogCatLoverPlatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name="create_date")
    private Date createDate;

    @Column(name = "rating")
    private int rating;

    @OneToOne(mappedBy = "commentEntity_UserBlogCommentEntity")
    UserBlogCommentEntity userBlogCommentEntities_CommentEntity;

    @ManyToOne
    @JoinColumn(name = "id_user")   //Checked
    private UserEntity userEntity_CommentEntity;

}

