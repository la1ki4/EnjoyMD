package com.devolution.EnjoyMD.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int userId;
    @Column(name = "u_name")
    private String userName;
    @Column(name = "u_email")
    private String userEmail;
    @Column(name = "u_password")
    private String userPassword;
//    @Column(name = "u_posts")
//    private List<Post> userPosts;
//    @Column(name = "u_comments")
//    private List<Comment> userComments;
//    @Column(name = "u_likes")
//    private List<Like> userLikes;
}
