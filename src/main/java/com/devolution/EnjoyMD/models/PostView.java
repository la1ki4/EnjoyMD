package com.devolution.EnjoyMD.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_views")
@Setter
@Getter
public class PostView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Integer userId; // Может быть null для анонимных просмотров

    private LocalDateTime viewedAt;

    private String ipAddress;

}
