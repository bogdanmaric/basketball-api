package com.fiba.basketballapi;

import com.fiba.basketballapi.service.BasketballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    private BasketballPlayerService basketballPlayerService;
    @Autowired
    public MyApplicationRunner(BasketballPlayerService basketballPlayerService) {
        this.basketballPlayerService = basketballPlayerService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        basketballPlayerService.saveAllPlayerStatsFromCsvFile();
    }
}
