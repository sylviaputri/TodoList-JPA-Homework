package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Todo")
public class Todo extends AuditModel {
    @Id
    @GeneratedValue(generator = "todo_generator")
    @SequenceGenerator(
            name = "todo_generator",
            sequenceName = "todo_sequence",
            initialValue = 1
    )
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    //gettersetter
    public Long getId() {
        return id;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user= user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
