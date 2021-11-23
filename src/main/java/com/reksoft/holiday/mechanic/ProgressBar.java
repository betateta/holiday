package com.reksoft.holiday.mechanic;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProgressBar {
    private Integer progress = 0;
    private static final Logger log = Logger.getLogger(ProgressBar.class);

    public void setProgress(Integer progress) {
        this.progress = progress;
        log.info("progressSet: "+progress);
        System.out.println("progressSet: "+progress);
    }

    public Integer getProgress() {
        log.info("progressGet: "+progress);
        System.out.println("progressGet: "+progress);
        return progress;
    }
}
