package com.reksoft.holiday.controller;

import com.reksoft.holiday.service.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
public class SseEmitterController {
    @Autowired
    private SseService sseService;

    @GetMapping("/sse")
    public SseEmitter handleSse()  {
        return sseService.getHandleSse(0);
    }

    @GetMapping("/sse-stream")
    public SseEmitter streamSseMvc() {
        return sseService.getStreamSse(0);
    }
}