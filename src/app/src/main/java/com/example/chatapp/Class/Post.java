package com.example.chatapp.Class;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    public String user_name;
//    public String postID;
    public String userID;
    public String id;
    public Date dateObject;
    public String posting_time;
    public String description;
    public Long duration;
    public Double pace;
    public Double distance;
    public int visibility;
    public String bitmap;
    public String avatar;
    // 0 is hidden, 1 is visible


}

