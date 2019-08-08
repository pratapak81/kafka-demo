package com.nsc.kafkademo.sink;

import com.nsc.kafkademo.model.PageViewEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;

public interface PageViewEventSink {
    String PAGE_VIEW_INPUT = "pvin";

    /*String PAGE_COUNT_IN = "pcin";*/

    @Input(PAGE_VIEW_INPUT)
    KStream<String, PageViewEvent> input();

    /*@Input(PAGE_COUNT_IN)
    KTable<String, Long> pageCountIn();*/
}
