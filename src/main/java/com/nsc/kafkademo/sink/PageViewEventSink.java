package com.nsc.kafkademo.sink;

import com.nsc.kafkademo.model.PageViewEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface PageViewEventSink {
    String PAGE_VIEW_INPUT = "pvin";

    @Input(PAGE_VIEW_INPUT)
    KStream<String, PageViewEvent> input();
}
