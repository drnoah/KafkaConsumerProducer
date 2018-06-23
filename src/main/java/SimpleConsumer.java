import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.DefaultConfigFilesProvider;

import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumer {

    public static void main(String[] args) {
        ClasspathConfigurationSource configurationSource = new ClasspathConfigurationSource(new DefaultConfigFilesProvider());
        SimpleConfiguration configuration = new SimpleConfiguration(configurationSource);
        Properties props = configuration.getProperties();
        Consumer consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer());
        consumer.subscribe(Arrays.asList(props.getProperty("topic")));
        try {
            int retry = 5;
            while (waitForMessage(retry)) {
                consumer.poll(1000).forEach(System.out::println);
            }
        } finally {
            consumer.close();
        }
    }

    private static boolean waitForMessage(int retry) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return retry-- > 0;
    }
}
