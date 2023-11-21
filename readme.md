# Documentación del sistema de alertas
## Introducción

Este sistema de alertas permite enviar alertas a usuarios que se han suscrito a un tema. El sistema cumple con los siguientes requisitos:

1. Se pueden registrar usuarios que recibirán alertas.
2. Se pueden registrar temas sobre los cuales se enviarán alertas.
3. Los usuarios pueden optar sobre cuales temas quieren recibir alertas.
4. Se puede enviar una alerta sobre un tema y lo reciben todos los usuarios que han optado recibir alertas de ese tema.
5. Se puede enviar una alerta sobre un tema a un usuario específico, solo lo recibe ese único usuario.
6. Una alerta puede tener una fecha y hora de expiración.
7. Hay dos tipos de alertas: Informativas y Urgentes.
8. Un usuario puede marcar una alerta como leída.
9. Se pueden obtener todas las alertas no expiradas de un usuario que aún no ha leído.
10. Se pueden obtener todas las alertas no expiradas para un tema. Se informa para cada alerta si es para todos los usuarios o para uno específico.

## Implementación

El sistema de alertas implementa el patrón de observador (Observer) y utiliza la interfaz Subscriber para permitir que los usuarios reciban alertas. 
A continuación, se describen las implementaciones relevantes:

### Interfaz Subscriber
La interfaz Subscriber define el método receiveAlert(Alert alert) que las clases deben implementar para recibir y manejar alertas. La implementación básica de esta interfaz se encuentra en la clase User.

### Clase User

La clase User implementa la interfaz Subscriber y representa a un usuario del sistema de alertas. Los usuarios pueden suscribirse a temas y recibir alertas asociadas a esos temas. Algunos métodos clave de la clase son:

**markAlertAsRead(Alert alert):** Marca una alerta como leída y la agrega a la lista de alertas recibidas por el usuario.

**getUnreadAlerts():** Retorna todas las alertas no leídas del usuario.

**getNonExpiredUnreadAlertsForUser():** Retorna todas las alertas no expiradas y no leídas del usuario, ordenadas según criterios específicos.

**receiveAlert(Alert alert):**Implementa el método de la interfaz Subscriber para recibir una alerta y agregarla a la lista de alertas recibidas.

### Patrón Observer en la Clase Topic

La clase Topic actúa como un sujeto observado que notifica a sus suscriptores (usuarios) sobre nuevas alertas. Cuando se envía una alerta mediante el método sendAlert(Alert alert), la clase notifica a todos los suscriptores llamando a sus métodos receiveAlert(Alert alert).

### Clase Topic

La clase Topic contiene métodos para administrar suscriptores y enviar alertas. Algunos métodos importantes son:

**addSubscriber(Subscriber subscriber):** Añade un suscriptor al tema.

**removeSubscriber(Subscriber subscriber):** Remueve un suscriptor del tema.

**notifySubscribers(Alert alert):** Notifica a todos los suscriptores sobre una nueva alerta.

**sendAlert(Alert alert):** Envía una alerta a todos los suscriptores y la agrega a la lista de alertas asociadas al tema.

**getNonExpiredAlerts():** Retorna todas las alertas no expiradas asociadas al tema, ordenadas según criterios específicos.

Esta implementación permite una comunicación flexible y desacoplada entre temas y usuarios, siguiendo el patrón Observer. Los usuarios se suscriben a temas de interés y son notificados automáticamente cuando se envían nuevas alertas.

### Clases principales
Usuario: Representa a un usuario que recibirá alertas.

Tema: Representa un tema sobre el cual se enviarán alertas.

Alerta: Representa una alerta que se enviará a un usuario o a todos los usuarios de un tema.

### Relaciones entre clases

Un usuario puede suscribirse a varios temas.

Un tema puede tener varios usuarios suscritos.

Una alerta puede estar dirigida a un usuario o a todos los usuarios de un tema.

### Uso del sistema

Para utilizar el sistema, se debe realizar los siguientes pasos:

1. Registrar usuarios.
2. Registrar temas.
3. Registrar alertas
4. Suscribir un usuario a un tema.
5. Enviar una alerta(a todos o personalizada).

## Pruebas

El sistema se ha probado utilizando la biblioteca JUnit. Las pruebas se pueden encontrar en el directorio src/test/java.

## Consideraciones

En cuanto el sort de las alertas urgentes(LIFO) y las informativas(FIFO). Cree un tipo de comparacion para reordenar la lista. No se si es la implementacion deseada pero fue la interpretacion que hice. Ademas se podria reutilizar la funcion de comparacion. Con respecto al patron Observer me dedique a estudiarlo ya que no lo conocia. Lo mismo con JUnit para los test unitarios. Para el punto 4 y 5 tal vez se podria haber aplicado otra cosa, yo utilice un targetUser que en caso de que sea null esa alerta va para todos los usuarios subscriptos.