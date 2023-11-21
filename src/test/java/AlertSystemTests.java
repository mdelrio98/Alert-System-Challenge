import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.enums.AlertType;
import com.example.model.Alert;
import com.example.model.Topic;
import com.example.model.User;

public class AlertSystemTests {

    private User user;
    private Topic topic;

    @BeforeEach
    public void setUp() {
        user = new User(1, "User1");
        topic = new Topic("Programming");
    }

    @Test
    public void testUserRegistration() {
        assertTrue(user != null);  // Asegura que la creación del usuario sea exitosa
    }

    @Test
    public void testTopicRegistration() {
        assertTrue(topic != null);  // Asegura que la creación del tema sea exitosa
    }

    @Test
    public void testUserSubscriptionToTopic() {
        topic.addSubscriber(user);
        assertTrue(topic.getSubscribers().contains(user));
    }

    @Test
    public void testSendAlertToTopic() {
        topic.addSubscriber(user);

        Alert alert = new Alert(topic, "New version available", AlertType.INFORMATIVA);
        topic.sendAlert(alert);

        assertTrue(user.getReceivedAlerts().contains(alert));
    }

    @Test
    public void testSendAlertToSpecificUser() {
        Alert alert = new Alert( topic,"asd", AlertType.URGENTE ,user);

        user.receiveAlert(alert);

        assertTrue(user.getReceivedAlerts().contains(alert));
    }

    @Test
    public void testMarkAlertAsRead() {
        //Un usuario puede marcar una alerta como leída.
        Alert alert = new Alert(topic, "New alert", AlertType.INFORMATIVA);
        user.receiveAlert(alert);

        assertFalse(alert.isRead());
        alert.markAsRead();
        assertTrue(alert.isRead());
    }


    @Test
    void testGetNonExpiredUnreadAlertsForUser() {
        //Se pueden obtener todas las alertas no expiradas de un usuario que aún no ha leído.
        // Crear un usuario
        User user = new User(1, "TestUser");

        // Crear alertas
        Alert alert1 = new Alert(new Topic("TestTopic"), "Mensaje 1", AlertType.INFORMATIVA);
        Alert alert2 = new Alert(new Topic("TestTopic"), "Mensaje 2", AlertType.URGENTE);
        Alert alert3 = new Alert(new Topic("TestTopic"), "Mensaje 3", AlertType.INFORMATIVA, user);

        // Agregar alertas al usuario
        user.receiveAlert(alert1);
        user.receiveAlert(alert2);
        user.receiveAlert(alert3);

        // Marcar una alerta como leída
        user.markAlertAsRead(alert1);

        // Obtener alertas no expiradas y no leídas
        List<Alert> nonExpiredUnreadAlerts = user.getNonExpiredUnreadAlertsForUser();

        // Verificar que la cantidad de alertas obtenidas sea la esperada
        assertEquals(2, nonExpiredUnreadAlerts.size());

        // Verificar que la alerta obtenida sea la correcta
        assertEquals(alert2, nonExpiredUnreadAlerts.get(0));
    }

    @Test
    void testGetNonExpiredAlertsForTopic() {
        Topic programmingTopic = new Topic("Programming");
        User user1 = new User(1, "User1");
        User user2 = new User(2, "User2");
    
        programmingTopic.addSubscriber(user1);
        programmingTopic.addSubscriber(user2);
    
        Alert urgentAlertForAll = new Alert(programmingTopic, "Urgent alert for all", AlertType.URGENTE);
        Alert informativeAlertForUser1 = new Alert(programmingTopic, "Informative alert for User1", AlertType.INFORMATIVA, user1);
        Alert informativeAlertForUser2 = new Alert(programmingTopic, "Informative alert for User2", AlertType.INFORMATIVA, user2);
    
        programmingTopic.sendAlert(urgentAlertForAll);
        programmingTopic.sendAlert(informativeAlertForUser1);
        programmingTopic.sendAlert(informativeAlertForUser2);
    
        List<Alert> nonExpiredAlertsForTopic = programmingTopic.getNonExpiredAlerts();
    
        System.out.println("----------------------");
        System.out.println("Non-expired alerts for topic '" + programmingTopic.getName() + "':");
    
        for (Alert alert : nonExpiredAlertsForTopic) {
            if (alert.getTargetUser() == null) {
                System.out.println("Alert for all users: " + alert.getMessage());
            } else {
                System.out.println("Alert for specific user " + (alert.getTargetUser() != null ? alert.getTargetUser().getName() : "Unknown") + ": " + alert.getMessage());
            }
        }
    
        System.out.println("----------------------");
    }
}
