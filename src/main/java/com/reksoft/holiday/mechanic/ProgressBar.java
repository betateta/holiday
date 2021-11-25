package com.reksoft.holiday.mechanic;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProgressBar {
    private volatile Integer progress = 0;
    private static final Logger log = Logger.getLogger(ProgressBar.class);
    private final boolean debug = false;

    public void setProgress(Integer progress) {
        this.progress = progress;
        log.info("progressSet: "+progress);
        if(debug){
            System.out.println("progressSet: "+progress);}

    }

    public Integer getProgress() {
        log.info("progressGet: "+progress);
        if(debug){
            System.out.println("progressGet: "+progress);
        }
        return progress;
    }
}
