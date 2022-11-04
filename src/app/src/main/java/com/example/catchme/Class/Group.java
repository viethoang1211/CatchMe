package com.example.catchme.Class;
import com.example.catchme.Class;

public class Group{
    private Integer id;
    private String name;
    private String description;
    //private String pircture;
    private List<User> mems;
    private List<Event> events;
    private Integer chatId;

    //getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getMems() {
        return mems;
    }

    public List<Event> getEvent() {
        return events;
    }

    public Integer getChatId() {
        return chatId;
    }

    //setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMems(List<User> mems) {
        this.mems = mems;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Group(Integer id, String name, String description, List<User> mems, List<Event> events, Integer chatId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mems = mems;
        this.events = events;
        this.chatId = chatId;
    }
}