package com.nsc.kafkademo.consumer;

import com.nsc.kafkademo.model.Event;
import com.nsc.kafkademo.model.PageViewEvent;
import com.nsc.kafkademo.sink.EventSink;
import com.nsc.kafkademo.sink.PageViewEventSink;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

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
    public void process(KStream<String, PageViewEvent> eventKStream) {
        eventKStream.foreach((key, value) -> {
            System.out.println("inside foreach");
            System.out.println(key + "  " + value);
        });
    }
}
