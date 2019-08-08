package com.nsc.kafkademo.producer;

import com.nsc.kafkademo.model.Event;
import com.nsc.kafkademo.source.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        List<String> eventNames = Arrays.asList("Leave", "Holiday", "Meeting", "Travel", "Business");
        Runnable runnable = () -> {
            event.setName(eventNames.get(new Random().nextInt(eventNames.size())));
            event.setId(new Random().nextInt(5));
            Message<Event> message = MessageBuilder
                    .withPayload(event)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, event.getName().getBytes())
                    .build();
            eventSource.output().send(message);
        };
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }
}
