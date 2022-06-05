package hr.fer.iot.rest.controller;

import hr.fer.iot.rest.entities.MqttMessage;
import hr.fer.iot.rest.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<MqttMessage> getMessages(@RequestParam(name = "minutes", defaultValue = "10") int minutes) {
        return messageService.findLastNMinutes(minutes);
    }

}
