package com.devolution.EnjoyMD.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int userId;
    @Column(name = "u_name")
    private String username;
    @Column(name = "u_email")
    private String email;
    @Column(name = "u_password")
    private String password;
    @Column(name = "u_role")
    private String role = "USER";
}
