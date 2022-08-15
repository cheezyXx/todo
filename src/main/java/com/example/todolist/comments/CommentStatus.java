package com.example.todolist.comments;

public enum CommentStatus {
    CREATED("CREATED"),
    EDITED("EDITED"),
    DELETED("DELETED");

    private final String label;

    private CommentStatus(String label) {
        this.label = label;
    }
}
