package com.nsc.kafkademo.consumer;

import com.nsc.kafkademo.model.Event;
import com.nsc.kafkademo.sink.EventSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding({Sink.class, EventSink.class})
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
}
