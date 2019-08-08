package com.nsc.kafkademo.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EventSource {
    String OUTPUT = "eventOut";

    @Output(OUTPUT)
    MessageChannel output();
}
