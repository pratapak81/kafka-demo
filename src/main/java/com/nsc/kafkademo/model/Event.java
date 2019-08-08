package com.nsc.kafkademo.model;

import lombok.Data;

@Data
public class Event {
    private String id;
    private String name;
    private String description;
}
