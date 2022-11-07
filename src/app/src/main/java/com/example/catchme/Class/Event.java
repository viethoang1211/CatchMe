package com.example.catchme.Class;
import com.example.catchme.User;

import java.time.LocalDate;
import java.util.List;

public class Event{
    private Integer id;
    private User creator;
    private String name;
    private String description;
    //private String pircture;
    private List<User> participants;
    private Integer chatId;
    private LocalDate start;
    private LocalDate end;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Event(Integer id, User creator, String name, String description, List<User> participants, Integer chatId, LocalDate start, LocalDate end) {
        this.id = id;
        this.creator = creator;
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.chatId = chatId;
        this.start = start;
        this.end = end;
    }
}