package com.example.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.enums.AlertType;
import com.example.interfaces.Subscriber;


public class Topic {

    private String name;
    private List<Subscriber> subscribers = new ArrayList<>();
    private List<Alert> alerts = new ArrayList<>();

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    //Los usuarios pueden optar sobre cuales temas quieren recibir alertas.
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(Alert alert) {
        for (Subscriber subscriber : subscribers) {
            subscriber.receiveAlert(alert);
        }
    }  
    
    public void sendAlert(Alert alert) {
        System.out.println("Sending alert on the topic '" + name + "'.");
        alerts.add(alert);
        notifySubscribers(alert);
    }

    public List<Alert> getNonExpiredAlerts() {
        List<Alert> nonExpiredAlerts = alerts.stream()
            .filter(alert -> !alert.isExpired() && alert.getTopic().getName().equals(this.getName()))
            .collect(Collectors.toList());     
            

        System.out.println("----------------------");
        System.out.println("Unsorted:");
        String alertMessages = nonExpiredAlerts.stream()
            .map(Alert::getMessage)
            .collect(Collectors.joining(", "));
        
        System.out.println(alertMessages);


        nonExpiredAlerts.sort((alert1, alert2) -> {
            // Compare by alert type
            int typeComparison = alert1.getType().compareTo(alert2.getType());
        
            // Tipos diferentes, return el tipo de comparacion
            if (typeComparison != 0) {
                return typeComparison;
            }
        
            // Tipos iguales, chequea si es urgente o informativa para hacer el orden respectivo
            if (alert1.getType() == AlertType.URGENTE) {
                // For urgent alerts, compare by index in reverse order (LIFO)
                return Integer.compare(alerts.indexOf(alert2), alerts.indexOf(alert1));
            } else {
                // For informative alerts, compare by index in normal order (FIFO)
                return Integer.compare(alerts.indexOf(alert1), alerts.indexOf(alert2));
            }
        }); 

        for (Alert alert : nonExpiredAlerts) {
            if (alert.getTargetUser()== null) {
                System.out.println("Alert for all users: " + alert.getMessage());
            } else {
                System.out.println("Alert for specific user " + (alert.getTargetUser() != null ? alert.getTargetUser().getName() : "Unknown") + ": " + alert.getMessage());
            }
        }   

    return nonExpiredAlerts;
}
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Topic other = (Topic) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }    
}

