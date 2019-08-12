package com.nsc.kafkademo.consumer;

import com.nsc.kafkademo.model.Event;
import com.nsc.kafkademo.model.PageViewEvent;
import com.nsc.kafkademo.sink.EventSink;
import com.nsc.kafkademo.sink.PageViewEventSink;
import com.nsc.kafkademo.source.PageViewEventSource;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding({Sink.class, EventSink.class, PageViewEventSink.class})
public class MessageReceiver {

    @StreamListener(target = Sink.INPUT)
    public void process(String message) {
        System.out.println("----- Received Message -----");
        System.out.println(message);
    }

    @StreamListener(target = EventSink.INPUT)
    public void process(Event event) {
        System.out.println("----- Event Message -----");
        System.out.println(event);
    }

    @StreamListener(target = PageViewEventSink.PAGE_VIEW_INPUT)
    @SendTo(PageViewEventSource.PAGE_COUNT_OUT)
    public KStream<String, Long> process(KStream<String, PageViewEvent> eventKStream) {
        return eventKStream.filter((key, value) -> value.getDuration() > 10)
                .map((key, value) -> new KeyValue<>(value.getPage(), "0"))
                .groupByKey()
                .count(Materialized.as("PAGE_COUNT_MV"))
                .toStream();
    }

    @StreamListener(target = PageViewEventSink.PAGE_COUNT_IN)
    public void processKTable(KTable<String, Long> counts) {
        counts.toStream().foreach((key, value) -> {
            System.out.println(key + "=" + value);
        });
    }
}
