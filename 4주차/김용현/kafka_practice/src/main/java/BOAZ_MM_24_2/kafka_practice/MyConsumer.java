package BOAZ_MM_24_2.kafka_practice;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

public class MyConsumer {
    private final KafkaConsumer<String, String> consumer;

    public MyConsumer(String url, String groupId, String topic) {
        Properties props = configureConsumer(url, groupId);

        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
    }

    public Properties configureConsumer(String url, String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer .class.getName());

        return props;
    }

    public void doConsume() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2_000));
                records.forEach(record -> System.out.println(record.value()));
            }
        } catch (WakeupException e) {
            System.err.println("일어나");
        } finally {
            try {
                consumer.commitSync();
            }finally {
                consumer.close();
                System.out.println("Closed consumer");
            }
        }
    }

    public static void main(String[] args) {
        String url = "localhost:9092";
        String groupId = "myGroupId";
        String topic = "test_topic";

        MyConsumer myConsumer = new MyConsumer(url, groupId, topic);
        myConsumer.doConsume();
    }
}
