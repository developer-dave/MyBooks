package io.bascom.controller;

import io.bascom.util.JSONUtil;
import io.bascom.util.TimeUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.bascom.util.Constants.JSON_UTF8;

@RestController
public class PingController {

    @GetMapping(value = "/ping", produces = JSON_UTF8)
    public ResponseEntity<String> ping() {
        final String responseMessage = "Pong! The time is " + TimeUtil.getHumanReadableTime();
        final String response = JSONUtil.generateResponse(responseMessage);
        return ResponseEntity.ok().body(response);
    }
}
