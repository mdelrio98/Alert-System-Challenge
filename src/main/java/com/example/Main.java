package com.example;

import java.util.List;
import java.util.stream.Collectors;

import com.example.enums.AlertType;
import com.example.model.Alert;
import com.example.model.Topic;
import com.example.model.User;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create a topic
        Topic programmingTopic = new Topic("Programming");

        // Create users
        User user1 = new User(1,"User1");
        User user2 = new User(2,"User2");

        // Add users as subscribers to the topic
        programmingTopic.addSubscriber(user1);
        programmingTopic.addSubscriber(user2);



        // prueba sort
        Alert u1 = new Alert(programmingTopic,"u1", AlertType.URGENTE);
        Alert u2 = new Alert(programmingTopic,"u2", AlertType.URGENTE);
        Alert u3 = new Alert(programmingTopic,"u3", AlertType.URGENTE);
        
        Alert i1 = new Alert(programmingTopic,"i1", AlertType.INFORMATIVA);
        Alert i2 = new Alert(programmingTopic,"i2", AlertType.INFORMATIVA);
        Alert i3 = new Alert(programmingTopic,"i3", AlertType.INFORMATIVA);
        Alert i4 = new Alert(programmingTopic,"i4", AlertType.INFORMATIVA);
        
        Alert z1 = new Alert(programmingTopic,"z1", AlertType.INFORMATIVA,user1);

        programmingTopic.sendAlert(i1);
        programmingTopic.sendAlert(i2);
        programmingTopic.sendAlert(u1);
        programmingTopic.sendAlert(i3);
        programmingTopic.sendAlert(u2);
        programmingTopic.sendAlert(i4);
        programmingTopic.sendAlert(u3);

        programmingTopic.sendAlert(z1);

        List<Alert> alertsNonExpired= programmingTopic.getNonExpiredAlerts();
        System.out.println("----------------------");
        System.out.println("Sorted:");
        String alertMessages = alertsNonExpired.stream()
        .map(Alert::getMessage)
        .collect(Collectors.joining(", "));

        System.out.println(alertMessages);
        System.out.println("----------------------");

        

        user1.receiveAlert(i4);

        List<Alert> alertsForUser= user1.getNonExpiredUnreadAlertsForUser();
        System.out.println("----------------------");
        System.out.println("Alerts for user:"+ user1.getName());
String alertMessages2 = alertsForUser.stream()
        .map(Alert::getMessage)
        .collect(Collectors.joining(", "));
        System.out.println(alertMessages2);
        System.out.println("----------------------");

    }
}