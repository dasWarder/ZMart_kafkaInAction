package com.example.zmart.streamsnode.service;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

public interface KafkaStreamsService {

    KStream<String,String> processingStream(StreamsBuilder builder);
}
