package com.nsc.kafkademo.producer;

import com.nsc.kafkademo.model.Event;
import com.nsc.kafkademo.source.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding({Source.class, EventSource.class})
public class MessageSender {

    private Source source;
    private EventSource eventSource;

    @Autowired
    public MessageSender(Source source, EventSource eventSource) {
        this.source = source;
        this.eventSource = eventSource;
    }

    public void send(String message) {
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    public void send(Event event) {
        eventSource.output().send(MessageBuilder.withPayload(event).build());
    }
}
