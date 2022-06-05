package hr.fer.iot.rest.controller;

import hr.fer.iot.rest.entities.TopicInfo;
import hr.fer.iot.rest.service.TopicInfoService;
import hr.fer.iot.rest.service.command.TopicInfoCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topic-info")
public class TopicInfoController {

    private final TopicInfoService topicInfoService;

    public TopicInfoController(TopicInfoService topicInfoService) {
        this.topicInfoService = topicInfoService;
    }

    @GetMapping
    public List<TopicInfo> getTopicInfo() {
        return topicInfoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@RequestBody TopicInfoCommand topicInfoCommand) {
        final Integer room = topicInfoCommand.getRoom();
        final Double threshold = topicInfoCommand.getThreshold();

        if (room == null || threshold == null) {
            return ResponseEntity.badRequest().build();
        }

        topicInfoService.save(String.format("room/%d/act", room), threshold);
        return ResponseEntity.ok().build();
    }

}
