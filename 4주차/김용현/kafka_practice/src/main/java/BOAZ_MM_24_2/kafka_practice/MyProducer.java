package BOAZ_MM_24_2.kafka_practice;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class MyProducer {
    private final KafkaProducer<String, String> producer;

    public MyProducer(String url) {
        Properties props = configureProducer(url);
        this.producer = new KafkaProducer<>(props);
    }

    public Properties configureProducer(String url) {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer .class.getName());

        return props;
    }

    public void doProduce(String topic) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello, Kafka");
        producer.send(record);
        producer.close();
    }

    public static void main(String[] args) {
        String url = "localhost:9092";
        String topic = "test_topic";

        MyProducer myProducer = new MyProducer(url);
        myProducer.doProduce(topic);
    }
}
