import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Publisher {
    public static void main(String args[]) {
        String content;
        String topic = "devices/sensor/temperature";
        int qos = 1;
        String broker = "tcp://mqtt.eclipse.org:1883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+ broker + "\n");

            while (true) {
                sampleClient.connect(connOpts);

                content = Integer.toString(getRandomNumber(15, 45));

                System.out.println("Publishing temp: " + content + "º");

                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);

                sampleClient.publish(topic, message);

                sampleClient.disconnect();

                Thread.sleep(1000L);
            }
        } catch(MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getRandomNumber(int a, int b) {
        return a + (int)(Math.random() * ((b - a) + 1));
    }
}