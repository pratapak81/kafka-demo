package com.nsc.kafkademo.model;

import lombok.Data;

@Data
public class Event {
    private int id;
    private String name;
    private String description;
}
