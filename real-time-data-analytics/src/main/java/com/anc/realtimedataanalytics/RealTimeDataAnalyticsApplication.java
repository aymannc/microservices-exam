package com.anc.realtimedataanalytics;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class RealTimeDataAnalyticsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RealTimeDataAnalyticsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "operations-stream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        final Serde<String> stringSerde = Serdes.String();
        KStream<String, Long> operationsMainStream =
                streamsBuilder
                        .stream("DEPOSIT", Consumed.with(stringSerde, stringSerde))
                        .groupByKey()
                        .count()
                        .toStream();
        operationsMainStream.foreach((k, v) -> {
            System.out.println(k + " -> " + v);
        });
        Topology topology = streamsBuilder.build();
        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
        kafkaStreams.start();

    }
}
