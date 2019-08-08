package com.nsc.kafkademo.source;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PageViewEventSource {
    String PAGE_VIEW_OUT = "pvout";

    /*String PAGE_COUNT_OUT = "pcout";*/

    @Output(PAGE_VIEW_OUT)
    MessageChannel output();

    /*@Output(PAGE_COUNT_OUT)
    KStream<String, Long> pageCountOut();*/
}
