package com.nsc.kafkademo.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EventSink {
    String INPUT = "eventIn";

    @Input(INPUT)
    SubscribableChannel input();
}
