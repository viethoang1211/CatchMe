package com.example.catchme.Class;
import com.example.catchme.User;

import java.time.LocalDate;
import java.util.List;

public class Post{
    private Integer id;
    private String location;
    //private String pircture;
    private List<User> likes;
    private List<User> comments;
    private List<User> shares;
    private LocalDate time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<User> getComments() {
        return comments;
    }

    public void setComments(List<User> comments) {
        this.comments = comments;
    }

    public List<User> getShares() {
        return shares;
    }

    public void setShares(List<User> shares) {
        this.shares = shares;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Post(Integer id, String location, List<User> likes, List<User> comments, List<User> shares, LocalDate time) {
        this.id = id;
        this.location = location;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.time = time;
    }
}
