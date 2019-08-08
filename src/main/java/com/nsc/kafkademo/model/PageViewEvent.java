package com.nsc.kafkademo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageViewEvent {
    private String userId;
    private String page;
    private long duration;
}
