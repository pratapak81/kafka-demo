package com.nsc.kafkademo.controller;

import com.nsc.kafkademo.model.Event;
import com.nsc.kafkademo.producer.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @RequestMapping
    public void sendMessage() {
        messageSender.send();
    }

    @RequestMapping("/{message}")
    public String sendMessage(@PathVariable("message") String message) {
        messageSender.send(message);
        return message;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Event> sendMessage(@RequestBody Event event) {
        messageSender.send(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> sendMessageKStream() {
        messageSender.send();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
