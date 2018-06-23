import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.DefaultEnvironment;

import java.util.Properties;

public class SimpleConfiguration {

    private ConfigurationProvider configurationProvider;

    public SimpleConfiguration(ConfigurationSource configurationSource) {
        this.configurationProvider = new ConfigurationProviderBuilder().withConfigurationSource(configurationSource)
                .withEnvironment(new DefaultEnvironment()).build();
    }

    public Properties getProperties() {

        Properties props = configurationProvider.allConfigurationAsProperties();

        setDefaultProducerConfig(props);
        setDefaultConsumerConfig(props);
        return props;
    }

    private void setDefaultProducerConfig(Properties props) {
        props.putIfAbsent("acks", "all");
        props.putIfAbsent("retries", 0);
        props.putIfAbsent("batch.size", 16384);
        props.putIfAbsent("linger.ms", 1);
        props.putIfAbsent("buffer.memory", 33554432);
        props.putIfAbsent("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.putIfAbsent("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    private void setDefaultConsumerConfig(Properties props) {
        props.putIfAbsent("group.id", "test");
        props.putIfAbsent("enable.auto.commit", "true");
        props.putIfAbsent("auto.commit.interval.ms", "1000");
        props.putIfAbsent("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.putIfAbsent("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }


}
