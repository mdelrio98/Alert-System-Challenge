package com.example.model;
import java.time.LocalDateTime;

import com.example.enums.AlertType;

public class Alert {

    private int id;
    private String message;
    private Topic topic;
    private AlertType type;
    private boolean read;
    //Una alerta puede tener una fecha y hora de expiración. 
    private LocalDateTime expirationDate;
    //Se puede enviar una alerta sobre un tema a un usuario específico, 
    //solo lo recibe ese único usuario.
    private User targetUser; 

    public Alert(Topic topic,String message, AlertType type) {
        this.topic = topic;
        this.message = message;
        this.type = type;
        this.read = false;
        this.targetUser = null;
        this.expirationDate = LocalDateTime.now().plusDays(1);
    }

    public Alert(Topic topic,String message, AlertType type, User targetUser) {
        this.topic = topic;
        this.message = message;
        this.type = type;
        this.read = false;
        this.targetUser = targetUser;
        this.expirationDate = LocalDateTime.now().plusDays(1);
    }    

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public User getTargetUser() {
        return targetUser;
    }
    
    public String getMessage() {
        return message;
    }

    public AlertType getType() {
        return type;
    }

    public void markAsRead() {
        this.read = true;
    }

    
    public Topic getTopic() {
        return topic;
    }

    
    public boolean isRead() {
        return read;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

}
