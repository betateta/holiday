package com.reksoft.holiday.service;

import com.reksoft.holiday.mechanic.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SseService {
    public static final String START_EVENT_NAME = "START";
    public static final String CONTINUE_EVENT_NAME = "CONTINUE";
    public static final String COMPLETE_EVENT_NAME = "COMPLETE";
    @Autowired
    private ProgressBar progressBar;

    public SseEmitter getHandleSse (Integer percentage){
        SseEmitter emitter = new SseEmitter();

        ExecutorService nonBlockingService = Executors
                .newSingleThreadExecutor();
        nonBlockingService.execute(() -> {
            Integer progress = progressBar.getProgress();
            try {
                switch (progress) {
                    case (0) : {
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(progress)
                                .id(String.valueOf(progress))
                                .name(START_EVENT_NAME);
                        emitter.send(event);
                        break;
                    }
                    case (100):{
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(progress)
                                .id(String.valueOf(progress))
                                .name(COMPLETE_EVENT_NAME);
                        emitter.send(event);
                        progressBar.setProgress(0);
                        break;
                    }
                    default:{
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(progress)
                                .id(String.valueOf(progress))
                                .name(CONTINUE_EVENT_NAME);
                        emitter.send(event);
                        progress = progressBar.getProgress();
                        break;
                    }
                }
                emitter.complete();

            } catch (Exception ex) {
                System.out.println("GENERIC_EXCEPTION");
                emitter.completeWithError(ex);
            }
        });
        nonBlockingService.shutdown();
        return emitter;
    }

    public SseEmitter getStreamSse(Integer percentage){
        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    switch (i) {
                        case (0) : {
                            SseEmitter.SseEventBuilder event = SseEmitter.event()
                                    .data(LocalTime.now().toString())
                                    .id(String.valueOf(i))
                                    .name(START_EVENT_NAME);
                            emitter.send(event);
                            break;
                        }
                        case (100):{
                            SseEmitter.SseEventBuilder event = SseEmitter.event()
                                    .data(LocalTime.now().toString())
                                    .id(String.valueOf(i))
                                    .name(COMPLETE_EVENT_NAME);
                            emitter.send(event);
                            emitter.complete();
                            break;
                        }
                        default:{
                            SseEmitter.SseEventBuilder event = SseEmitter.event()
                                    .data(LocalTime.now().toString())
                                    .id(String.valueOf(i))
                                    .name(CONTINUE_EVENT_NAME);
                            emitter.send(event);
                            break;
                        }
                    }
                    Thread.sleep(10);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }
}
