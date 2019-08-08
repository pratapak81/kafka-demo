package com.nsc.kafkademo.producer;

import com.nsc.kafkademo.model.Event;
import com.nsc.kafkademo.model.PageViewEvent;
import com.nsc.kafkademo.source.EventSource;
import com.nsc.kafkademo.source.PageViewEventSource;
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

@EnableBinding({Source.class, EventSource.class, PageViewEventSource.class})
public class MessageSender {

    private Source source;
    private EventSource eventSource;
    private PageViewEventSource pageViewEventSource;

    @Autowired
    public MessageSender(Source source, EventSource eventSource, PageViewEventSource pageViewEventSource) {
        this.source = source;
        this.eventSource = eventSource;
        this.pageViewEventSource = pageViewEventSource;
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

    public void send() {
        List<String> names = Arrays.asList("pratap", "ak", "pk", "bk", "mk");
        List<String> pages = Arrays.asList("about", "contact", "clients", "products", "misc");
        Runnable runnable = () -> {
            PageViewEvent pageViewEvent = PageViewEvent.builder()
                    .userId(names.get(new Random().nextInt(names.size())))
                    .page(pages.get(new Random().nextInt(pages.size())))
                    .duration(Math.random() > 0.5 ? 10 : 1000)
                    .build();

            Message<PageViewEvent> message = MessageBuilder
                    .withPayload(pageViewEvent)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, pageViewEvent.getUserId().getBytes())
                    .build();

            try {
                pageViewEventSource.output().send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }
}
