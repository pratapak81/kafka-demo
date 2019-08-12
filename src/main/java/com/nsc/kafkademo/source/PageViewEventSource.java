package com.nsc.kafkademo.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PageViewEventSource {
    String PAGE_VIEW_OUT = "pvout";

    @Output(PAGE_VIEW_OUT)
    MessageChannel output();
}
