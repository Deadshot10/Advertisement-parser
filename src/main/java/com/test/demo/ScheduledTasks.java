package com.test.demo;

import com.test.demo.services.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.test.demo.Util.log;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private ParserService parserService;

    @Scheduled(fixedDelay = 1000*3600*2)
    public void parsing() throws Exception {
        if (parserService.connect()) {
            parserService.startParse();
        }
    }
}