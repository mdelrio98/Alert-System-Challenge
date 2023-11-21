package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.enums.AlertType;
import com.example.interfaces.Subscriber;

public class User implements Subscriber {
    private String name;
    private int id;
    private List<Alert> receivedAlerts = new ArrayList<>();


    public User(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public void markAlertAsRead(Alert alert) {
        alert.markAsRead();
        receivedAlerts.add(alert);
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public List<Alert> getUnreadAlerts() {
        return receivedAlerts.stream()
                .filter(alert -> !alert.isRead())
                .collect(Collectors.toList());
    }

    public List<Alert> getNonExpiredUnreadAlertsForUser() {
        System.out.println("----------------------");
        
        String alertM = receivedAlerts.stream()
                .map(Alert::getMessage)
                .collect(Collectors.joining(",, "));
                System.out.println(alertM);
                
        System.out.println("----------------------");

        List<Alert> nonExpiredUnreadAlerts = receivedAlerts.stream()
                .filter(alert -> !alert.isExpired() && !alert.isRead())
                .collect(Collectors.toList());
    
                String alertMessages = nonExpiredUnreadAlerts.stream()
                .map(Alert::getMessage)
                .collect(Collectors.joining(",, "));
            
            System.out.println(alertMessages);

        if (nonExpiredUnreadAlerts.size() > 1) {
        nonExpiredUnreadAlerts.sort((alert1, alert2) -> {
            // Compare by alert type
            int typeComparison = alert1.getType().compareTo(alert2.getType());
    
            // If the types are different, return the type comparison
            if (typeComparison != 0) {
                return typeComparison;
            }
    
            // If the types are the same, check if they are urgent
            if (alert1.getType() == AlertType.URGENTE) {
                // For urgent alerts, compare by index in reverse order (LIFO)
                return Integer.compare(receivedAlerts.indexOf(alert2), receivedAlerts.indexOf(alert1));
            } else {
                // For informative alerts, compare by index in normal order (FIFO)
                return Integer.compare(receivedAlerts.indexOf(alert1), receivedAlerts.indexOf(alert2));
            }
        });}
        return nonExpiredUnreadAlerts;
    }
    

    @Override
    public void receiveAlert(Alert alert) {
        receivedAlerts.add(alert);
        System.out.println("User '" + name + "' received the alert: " + alert.getMessage());
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }


    public List<Alert> getReceivedAlerts() {
        return receivedAlerts;
    }

    
}
