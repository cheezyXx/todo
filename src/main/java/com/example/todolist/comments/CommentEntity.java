package com.example.todolist.comments;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="comments")
public class CommentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "comment_id")
    private String id;

    private String userId;

    private String todoId;

    private String createdAt;
}
