import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.DefaultConfigFilesProvider;

import java.util.Properties;
import java.util.stream.IntStream;

public class SimpleProducer {

    public static void main(String[] args) {
        ClasspathConfigurationSource configurationSource = new ClasspathConfigurationSource(new DefaultConfigFilesProvider());
        SimpleConfiguration configuration = new SimpleConfiguration(configurationSource);
        Producer<String, String> producer = createProducer(configuration);
        publishMessages(producer, configuration.getProperties().getProperty("topic"));
        finalize(producer);
    }

    private static Producer<String, String> createProducer(SimpleConfiguration configuration) {
        Properties props = configuration.getProperties();
        return new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
    }

    private static void publishMessages(Producer<String, String> producer, String topic) {
        IntStream.range(0, 1000).forEach(i -> {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, String.format("Message %d", i));
            producer.send(record);
        });
    }

    private static void finalize(Producer<String, String> producer) {
        producer.flush();
        producer.close();
    }
}
